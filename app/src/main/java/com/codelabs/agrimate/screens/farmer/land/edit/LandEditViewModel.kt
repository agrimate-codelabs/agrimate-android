package com.codelabs.agrimate.screens.farmer.land.edit

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.codelabs.agrimate.common.FormHandler
import com.codelabs.agrimate.ui.common.impl.RegionSelectInputImpl
import com.codelabs.agrimate.ui.navigation.DestinationsArg
import com.codelabs.core.data.source.remote.body.CreateFarmerLandBody
import com.codelabs.core.data.source.remote.response.ResponseMessage
import com.codelabs.core.domain.model.RegionModel
import com.codelabs.core.domain.usecase.FarmerLandUseCase
import com.codelabs.core.domain.usecase.RegionUseCase
import com.codelabs.core.utils.Resource
import com.google.android.gms.maps.model.LatLng
import com.google.gson.Gson
import com.google.maps.android.SphericalUtil
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.util.Locale
import javax.inject.Inject
import kotlin.math.atan2

@HiltViewModel
class LandEditViewModel @Inject constructor(
    private val farmerLandUseCase: FarmerLandUseCase,
    private val regionUseCase: RegionUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val landId = savedStateHandle.get<String>(DestinationsArg.LAND_ID_ARG).orEmpty()

    private val _uiState = MutableStateFlow(LandEditUiState())
    val uiState: StateFlow<LandEditUiState> = _uiState.asStateFlow()

    private val _toastMessage = MutableSharedFlow<String>()
    val toastMessage = _toastMessage.asSharedFlow()

    private fun sendMessage(message: String) {
        viewModelScope.launch {
            _toastMessage.emit(message)
        }
    }

    private val _selectedProvinceCode = MutableStateFlow("")
    private val _selectedCityCode = MutableStateFlow("")
    private val _selectedDistrictCode = MutableStateFlow("")


    fun getFarmerLandData() {
        viewModelScope.launch {
            updateIsGettingData(true)
            farmerLandUseCase.getFarmerLand(landId).collect { resource ->
                Log.d("TEST", "$resource")
                when (resource) {
                    is Resource.Error -> {
                        sendMessage(resource.message ?: "Terjadi Kesalahan")
                    }

                    is Resource.Loading -> {
                        updateIsGettingData(true)
                    }

                    is Resource.Success -> {
                        resource.data?.apply {
                            updateLandName(name.orEmpty())
                            updateProvince(RegionModel(provinceCode.orEmpty(), province.orEmpty()))
                            updateCity(RegionModel(cityCode.orEmpty(), city.orEmpty()))
                            updateDistrict(RegionModel(districtCode.orEmpty(), district.orEmpty()))
                            updateVillages(RegionModel(villageCode.orEmpty(), village.orEmpty()))
                            val landMarker = polygon.map {
                                LatLng(it?.latitude ?: 0.0, it?.longitude ?: 0.0)
                            }
                            updateLandMarker(landMarker)
                            updateLandArea(landArea?.toString().orEmpty())
                            updateAddress(address.orEmpty())
                            updateImageUrl(photo.orEmpty())
                        }
                        updateIsGettingData(false)
                    }
                }
            }
        }
    }


    val listOfProvince = regionUseCase.getListOfProvince().map {
        mapToRegionSelectInputImpl(it)
    }.asLiveData()

    @OptIn(ExperimentalCoroutinesApi::class)
    val listOfCity = _selectedProvinceCode.flatMapLatest { code ->
        regionUseCase.getListOfCity(provinceCode = code).map {
            mapToRegionSelectInputImpl(it)
        }
    }.asLiveData()


    @OptIn(ExperimentalCoroutinesApi::class)
    val listOfDistrict = _selectedCityCode.flatMapLatest { code ->
        regionUseCase.getListOfDistrict(cityCode = code).map {
            mapToRegionSelectInputImpl(it)
        }
    }.asLiveData()

    @OptIn(ExperimentalCoroutinesApi::class)
    val listOfVillage = _selectedDistrictCode.flatMapLatest { code ->
        regionUseCase.getListOfVillage(districtCode = code).map {
            mapToRegionSelectInputImpl(it)
        }
    }.asLiveData()

    private fun mapToRegionSelectInputImpl(resource: Resource<List<RegionModel>>) =
        when (resource) {
            is Resource.Success -> {
                val selectOptions = resource.data!!.map {
                    RegionSelectInputImpl(it.name, it.code)
                }
                Resource.Success(selectOptions)
            }

            is Resource.Loading -> {
                Resource.Loading()
            }

            is Resource.Error -> {
                Resource.Error(resource.message.toString())
            }
        }

    private fun updateSelectedRegionCode(
        provinceCode: String,
        cityCode: String,
        districtCode: String
    ) {
        _selectedProvinceCode.value = provinceCode
        _selectedCityCode.value = cityCode
        _selectedDistrictCode.value = districtCode
    }

    fun uploadImage(imageFile: File) {
        viewModelScope.launch {
            val imageRequestBody = imageFile.asRequestBody("image/jpeg".toMediaType())
            val image: MultipartBody.Part = MultipartBody.Part.createFormData(
                "image", imageFile.name, imageRequestBody
            )
            farmerLandUseCase.uploadImage(image).collect { resource ->
                when (resource) {
                    is Resource.Error -> {
                        updateIsUploadingImage(false)
                        sendMessage(resource.message.toString())
                    }

                    is Resource.Loading -> {
                        updateIsUploadingImage(true)
                    }

                    is Resource.Success -> {
                        updateIsUploadingImage(false)
                        updateImageUrl(resource.data?.url.toString())
                    }
                }
            }
        }
    }

    fun saveLand() {
        viewModelScope.launch {
            resetValidation()
            updateValidation()
            if (isFormValid()) {
                _uiState.value.apply {
                    farmerLandUseCase.updateFarmerLand(
                        landId,
                        CreateFarmerLandBody(
                            name = landName,
                            province = province.name,
                            city = city.name,
                            district = district.name,
                            village = village.name,
                            address = address,
                            landArea = landArea.toDouble(),
                            polygon = Gson().toJson(landMarker),
                            photo = imageUrl
                        )
                    ).collect { resource ->
                        when (resource) {
                            is Resource.Error -> {
                                updateIsLoading(false)
                                if (resource.data != null) {
                                    when (val error = resource.data?.message) {
                                        is ResponseMessage.ArrayMessage -> {
                                            updateMultipleError(error)
                                        }

                                        is ResponseMessage.StringMessage -> {
                                            sendMessage("Gagal menambahkan lahan!")
                                        }

                                        null -> {
                                            sendMessage(resource.message.toString())
                                        }
                                    }
                                } else {
                                    sendMessage(resource.message.toString())
                                }
                            }

                            is Resource.Loading -> {
                                updateIsLoading(true)
                            }

                            is Resource.Success -> {
                                updateIsLoading(false)
                                _uiState.update {
                                    it.copy(
                                        isSuccess = true
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private fun resetValidation() {
        updateInputProvince(true, "")
        updateInputCity(true, "")
        updateInputDistrict(true, "")
        updateInputVillage(true, "")
        updateInputAddress(true, "")
        updateInputImage(true, "")
        updateInputLandArea(true, "")
        updateInputLandMarker(true, "")
    }

    private fun updateMultipleError(error: ResponseMessage.ArrayMessage) {
        error.message.map {
            if (it.path == "province") updateInputProvince(false, it.message)
            if (it.path == "city") updateInputCity(false, it.message)
            if (it.path == "district") updateInputDistrict(false, it.message)
            if (it.path == "village") updateInputVillage(false, it.message)
            if (it.path == "landArea") updateInputLandArea(false, it.message)
        }
    }

    private fun updateInputProvince(state: Boolean, message: String) {
        _uiState.update {
            it.copy(inputProvince = FormHandler(state, message))
        }
    }

    private fun updateInputCity(state: Boolean, message: String) {
        _uiState.update {
            it.copy(inputCity = FormHandler(state, message))
        }
    }

    private fun updateInputDistrict(state: Boolean, message: String) {
        _uiState.update {
            it.copy(inputDistrict = FormHandler(state, message))
        }
    }

    private fun updateInputVillage(state: Boolean, message: String) {
        _uiState.update {
            it.copy(inputVillage = FormHandler(state, message))
        }
    }

    private fun updateInputLandArea(state: Boolean, message: String) {
        _uiState.update {
            it.copy(inputLandArea = FormHandler(state, message))
        }
    }

    private fun updateInputLandMarker(state: Boolean, message: String) {
        _uiState.update {
            it.copy(inputLandMarker = FormHandler(state, message))
        }
    }

    private fun updateInputAddress(state: Boolean, message: String) {
        _uiState.update {
            it.copy(inputAddress = FormHandler(state, message))
        }
    }

    private fun updateInputImage(state: Boolean, message: String) {
        _uiState.update {
            it.copy(inputImage = FormHandler(state, message))
        }
    }

    private fun updateValidation() {
        _uiState.value.run {
            // landMarker validation
            if (landMarker.isEmpty()) {
                updateInputLandMarker(false, "Area Lahan wajib ditandai")
            }

            // province validation
            if (province.code.isEmpty()) {
                updateInputProvince(false, "Pilih Provinsi terlebih dahulu")
            }

            // city validation validation
            if (city.code.isEmpty()) {
                updateInputCity(false, "Pilih Kota terlebih dahulu")
            }

            // district validation
            if (district.code.isEmpty()) {
                updateInputDistrict(false, "Pilih Kecamatan terlebih dahulu")
            }

            // village validation
            if (village.code.isEmpty()) {
                updateInputVillage(false, "Pilih Kelurahan/Desa terlebih dahulu")
            }

            // addreass validation
            if (address.isEmpty()) {
                updateInputAddress(false, "Isi Alamat terlebih dahulu")
            }

            // imageUrl validation
            if (imageUrl.isEmpty()) {
                updateInputImage(false, "Unggah Gambar Lahan terlebih dahulu")
            }

            // landArea validation
            if (landArea.isEmpty()) {
                updateInputLandArea(false, "Isi Luas Lahan terlebih dahulu")
            }
            try {
                if (landArea.toDouble() <= 0) {
                    updateInputLandArea(false, "Luas Lahan tidak valid")
                }
            } catch (_: Exception) {
                updateInputLandArea(
                    false,
                    "Luas Lahan tidak valid. Gunakan tanda pemisah desimal \".\""
                )
            }
        }
    }

    private fun isFormValid(): Boolean {
        return _uiState.value.run {
            inputProvince.isValid
                    && inputCity.isValid
                    && inputDistrict.isValid
                    && inputVillage.isValid
                    && inputLandMarker.isValid
                    && inputLandArea.isValid
                    && inputAddress.isValid
                    && inputImage.isValid
        }
    }

    private fun updateIsLoading(state: Boolean) {
        _uiState.update {
            it.copy(isLoading = state)
        }
    }

    private fun updateIsGettingData(state: Boolean) {
        _uiState.update {
            it.copy(isGettingData = state)
        }
    }

    private fun updateIsUploadingImage(state: Boolean) {
        _uiState.update {
            it.copy(isUploadingImage = state)
        }
    }

    private fun sortClockwise(landMarker: List<LatLng>): List<LatLng> {
        val center = calculateCenter(landMarker)

        return landMarker.sortedBy { angleFromCenter(it, center) }
    }

    private fun calculateCenter(landMarker: List<LatLng>): LatLng {
        val avgLat = landMarker.map { it.latitude }.average()
        val avgLng = landMarker.map { it.longitude }.average()
        return LatLng(avgLat, avgLng)
    }

    private fun angleFromCenter(point: LatLng, center: LatLng): Double {
        val angleRad = atan2(point.longitude - center.longitude, point.latitude - center.latitude)
        return Math.toDegrees(angleRad).let {
            if (it < 0) it + 360 else it
        }
    }

    fun updateLandName(input: String) {
        _uiState.update {
            it.copy(landName = input)
        }
    }

    fun updateProvince(newProvince: RegionModel) {
        updateInputProvince(true, "")
        _uiState.update {
            it.copy(
                province = newProvince,
                city = RegionModel("", ""),
                district = RegionModel("", ""),
                village = RegionModel("", "")
            )
        }
        updateSelectedRegionCode(provinceCode = newProvince.code, cityCode = "", districtCode = "")
    }

    fun updateCity(newCity: RegionModel) {
        updateInputCity(true, "")
        _uiState.update {
            it.copy(city = newCity, district = RegionModel("", ""), village = RegionModel("", ""))
        }
        updateSelectedRegionCode(
            provinceCode = _selectedProvinceCode.value,
            cityCode = newCity.code,
            districtCode = ""
        )
    }

    fun updateDistrict(newDistrict: RegionModel) {
        updateInputDistrict(true, "")
        _uiState.update {
            it.copy(district = newDistrict, village = RegionModel("", ""))
        }
        updateSelectedRegionCode(
            provinceCode = _selectedProvinceCode.value,
            cityCode = _selectedCityCode.value,
            districtCode = newDistrict.code
        )
    }

    fun updateVillages(newVillages: RegionModel) {
        updateInputVillage(true, "")
        _uiState.update {
            it.copy(village = newVillages)
        }
    }

    fun updateAddress(input: String) {
        updateInputAddress(true, "")
        _uiState.update {
            it.copy(address = input)
        }
    }

    fun updateLandArea(input: String) {
        updateInputLandArea(true, "")
        _uiState.update {
            it.copy(landArea = input)
        }
        try {
            if (input.toDouble() <= 0) {
                updateInputLandArea(false, "Jumlah Tanam tidak valid")
            } else {
                updateInputLandArea(true, "")
            }
        } catch (e: Exception) {
            Log.e("Error", "updateLandArea: ${e.message}")
        }
    }

    fun updateLandMarker(landMarker: List<LatLng>) {
        updateInputLandMarker(true, "")
        val sortedLandMarker = sortClockwise(landMarker)
        val hectareUnit = 10000
        val landArea = SphericalUtil.computeArea(sortedLandMarker) / hectareUnit
        val decimalFormat = DecimalFormat("#.##", DecimalFormatSymbols(Locale.ENGLISH))
        val landAreaStr = decimalFormat.format(landArea)
        updateLandArea(landAreaStr)
        _uiState.update {
            it.copy(landMarker = sortedLandMarker)
        }
    }

    private fun updateImageUrl(input: String) {
        updateInputImage(true, "")
        _uiState.update {
            it.copy(imageUrl = input)
        }
    }
}