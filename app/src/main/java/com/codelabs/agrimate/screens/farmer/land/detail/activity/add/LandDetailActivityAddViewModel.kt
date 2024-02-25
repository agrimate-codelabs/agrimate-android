package com.codelabs.agrimate.screens.farmer.land.detail.activity.add

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.codelabs.agrimate.common.FormHandler
import com.codelabs.agrimate.ui.common.impl.PlantCommoditySelectInputImpl
import com.codelabs.agrimate.ui.common.impl.UnitSelectInputImpl
import com.codelabs.agrimate.ui.common.impl.UnitState
import com.codelabs.agrimate.ui.navigation.DestinationsArg
import com.codelabs.agrimate.utils.DateUtils
import com.codelabs.core.data.source.remote.body.CreatePlantingBody
import com.codelabs.core.data.source.remote.response.ResponseMessage
import com.codelabs.core.domain.model.CommodityModel
import com.codelabs.core.domain.usecase.CommodityUseCase
import com.codelabs.core.domain.usecase.FarmerLandDetailUseCase
import com.codelabs.core.domain.usecase.PlantingUseCase
import com.codelabs.core.utils.Resource
import com.google.android.gms.maps.model.LatLng
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LandDetailActivityAddViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val farmerLandDetailUseCase: FarmerLandDetailUseCase,
    commodityUseCase: CommodityUseCase,
    private val plantingUseCase: PlantingUseCase,
) : ViewModel() {
    private val farmLandId = savedStateHandle.get<String>(DestinationsArg.LAND_ID_ARG).orEmpty()

    private val _uiState = MutableStateFlow(LandDetailActivityAddUiState())
    val uiState = _uiState.asStateFlow()

    private var _farmPolygon = MutableStateFlow<List<LatLng>>(emptyList())
    val farmPolygon = _farmPolygon.asStateFlow()

    private var _marker = MutableStateFlow<LatLng?>(null)
    val marker = _marker.asStateFlow()

    private val _toastMessage = MutableSharedFlow<String>()
    val toastMessage = _toastMessage.asSharedFlow()

    private fun sendMessage(message: String) {
        viewModelScope.launch {
            _toastMessage.emit(message)
        }
    }

    val listOfCommodity = commodityUseCase.getAll().map { resource ->
        when (resource) {
            is Resource.Success -> {
                val selectOptions = resource.data!!.map {
                    PlantCommoditySelectInputImpl(it.name, it.id)
                }
                Resource.Success(selectOptions)
            }

            is Resource.Loading -> {
                Resource.Loading()
            }

            is Resource.Error -> {
                Resource.Error(resource.message.orEmpty())
            }
        }
    }.asLiveData()

    init {
        viewModelScope.launch {
            delay(1000)
            getRemainingLandArea()
            getFarmPolygon()
            updateIsGettingData(false)
        }
    }

    fun save() {
        viewModelScope.launch {
            resetValidation()
            updateValidation()
            if (isFormValid()) {
                _uiState.value.run {
                    val body = CreatePlantingBody(
                        farmLandId,
                        commodity.id,
                        typeOfPlating.value,
                        typeOfPlating.unit,
                        landArea.toDouble(),
                        plantAmount.toDouble(),
                        productionCost.toLong(),
                        _marker.value?.latitude?.toString().orEmpty(),
                        _marker.value?.longitude?.toString().orEmpty(),
                        DateUtils.convertDateFormat(datePlant),
                    )
                    Log.d("TEST", "$body")
                    plantingUseCase.create(body).collect { resource ->
                        when (resource) {
                            is Resource.Error -> {
                                updateIsLoading(false)
                                updateIsSuccess(false)
                                if (resource.data != null) {
                                    when (val error = resource.data?.message) {
                                        is ResponseMessage.ArrayMessage -> {
                                            updateMultipleError(error)
                                        }

                                        is ResponseMessage.StringMessage -> {
                                            sendMessage(error.message)
                                        }

                                        null -> {}
                                    }
                                }
                            }

                            is Resource.Loading -> {
                                updateIsLoading(true)
                            }

                            is Resource.Success -> {
                                updateIsLoading(false)
                                updateIsSuccess(true)
                            }
                        }
                    }
                }
            } else {
                sendMessage("Harap isi semua masukan")
            }
        }
    }

    private fun isFormValid(): Boolean {
        return _uiState.value.run {
            inputCommodity.isValid
                    && inputLandArea.isValid
                    && inputDatePlant.isValid
                    && inputTypeOfPlanting.isValid
                    && inputPlantAmount.isValid
                    && inputProductionCost.isValid
                    && inputLandMarker.isValid
        }
    }

    private fun resetValidation() {
        updateInputCommodity(true, "")
        updateInputLandArea(true, "")
        updateInputDatePlant(true, "")
        updateInputTypeOfPlanting(true, "")
        updateInputPlantAmount(true, "")
        updateInputProductionCost(true, "")
        updateInputLandMarker(true, "")
    }

    private fun updateValidation() {
        _uiState.value.run {
            // marker validation
            if (_marker.value == null) {
                updateInputLandMarker(false, "Tandai Lahan Tanam terlebih dahulu")
            }

            // commodity validation
            if (commodity.id.isEmpty()) {
                updateInputCommodity(false, "Pilih Komoditas terlebih dahulu")
            }

            // landArea validation
            if (landArea.isEmpty()) {
                updateInputLandArea(false, "Luas Lahan Tanam wajib diisi")
            }
            try {
                if (landArea.toDouble() <= 0) {
                    updateInputLandArea(false, "Luas Lahan Tanam tidak valid")
                } else if (landArea.toDouble() > _uiState.value.remainingLandArea) {
                    updateInputLandArea(
                        false,
                        "Tidak boleh lebih dari sisa lahan ${_uiState.value.remainingLandArea} hektare"
                    )
                }
            } catch (e: Exception) {
                updateInputLandArea(
                    false,
                    "Luas Lahan Tanam tidak valid. Gunakan tanda pemisah desimal \".\""
                )
            }

            // datePlant validation
            if (datePlant.isEmpty()) {
                updateInputDatePlant(false, "Tanggal Tanam wajib diisi")
            }

            // TypeOfPlating validation
            if (typeOfPlating.value.isEmpty()) {
                updateInputTypeOfPlanting(false, "Pilih Jenis Tanam terlebih dahulu")
            }

            // plantAmount validation
            if (plantAmount.isEmpty()) {
                updateInputPlantAmount(false, "Jumlah Tanam wajib diisi")
            }
            try {
                if (plantAmount.toDouble() <= 0) {
                    updateInputPlantAmount(false, "Jumlah Tanam tidak valid")
                }
            } catch (e: Exception) {
                updateInputPlantAmount(
                    false,
                    "Jumlah tidak valid. Gunakan tanda pemisah desimal \".\""
                )
            }

            // ProductionCost validation
            if (productionCost.isEmpty()) {
                updateInputProductionCost(false, "Biaya Produksi wajib diisi")
            }
        }
    }

    private suspend fun getRemainingLandArea() {
        viewModelScope.launch {
            farmerLandDetailUseCase.getRemainingLandArea(farmLandId).collect { resource ->
                when (resource) {
                    is Resource.Error -> {
                        sendMessage(resource.message ?: "Terjadi Kesalahan")
                    }

                    is Resource.Loading -> {
                        updateIsGettingData(true)
                    }

                    is Resource.Success -> {
                        val totalArea = resource.data?.totalArea ?: 0.0
                        val totalUsed = resource.data?.usedArea ?: 0.0
                        updateRemainingLandArea(totalArea - totalUsed)
                    }
                }
            }
        }
    }

    private suspend fun getFarmPolygon() {
        farmerLandDetailUseCase.getFarmerLand(farmLandId).collect { resource ->
            when (resource) {
                is Resource.Error -> {
                    sendMessage(resource.message ?: "Terjadi Kesalahan")
                }

                is Resource.Loading -> {
                    updateIsGettingData(true)
                }

                is Resource.Success -> {
                    val listLatLng = resource.data?.polygon?.map { polygon ->
                        LatLng(polygon?.latitude ?: 0.0, polygon?.longitude ?: 0.0)
                    }
                    _farmPolygon.value = listLatLng ?: emptyList()
                }
            }
        }
    }

    private fun updateRemainingLandArea(input: Double) {
        _uiState.update {
            it.copy(
                remainingLandArea = input

            )
        }
    }

    fun setMarker(latLng: LatLng?) {
        if (latLng != null) {
            if (isInsidePolygon(latLng)) {
                _marker.value = latLng
            }
        } else {
            _marker.value = null
        }
    }

    private fun isInsidePolygon(point: LatLng): Boolean {
        var inside = false
        val x = point.latitude
        val y = point.longitude

        val n = _farmPolygon.value.size
        var j = n - 1

        for (i in 0 until n) {
            val xi = _farmPolygon.value[i].latitude
            val yi = _farmPolygon.value[i].longitude
            val xj = _farmPolygon.value[j].latitude
            val yj = _farmPolygon.value[j].longitude

            val intersect = ((yi > y) != (yj > y)) && (x < (xj - xi) * (y - yi) / (yj - yi) + xi)

            if (intersect) {
                inside = !inside
            }

            j = i
        }
        return inside
    }

    private fun updateIsLoading(state: Boolean) {
        _uiState.update {
            it.copy(isLoading = state)
        }
    }

    private fun updateIsSuccess(state: Boolean) {
        _uiState.update {
            it.copy(isSuccess = state)
        }
    }

    private fun updateIsGettingData(state: Boolean) {
        _uiState.update {
            it.copy(isGettingData = state)
        }
    }

    private fun updateInputLandArea(state: Boolean, message: String) {
        _uiState.update {
            it.copy(
                inputLandArea = FormHandler(state, message)
            )
        }
    }

    private fun updateInputCommodity(state: Boolean, message: String) {
        _uiState.update {
            it.copy(
                inputCommodity = FormHandler(state, message)
            )
        }
    }

    private fun updateInputDatePlant(state: Boolean, message: String) {
        _uiState.update {
            it.copy(
                inputDatePlant = FormHandler(state, message)
            )
        }
    }

    private fun updateInputTypeOfPlanting(state: Boolean, message: String) {
        _uiState.update {
            it.copy(
                inputTypeOfPlanting = FormHandler(state, message)
            )
        }
    }

    private fun updateInputPlantAmount(state: Boolean, message: String) {
        _uiState.update {
            it.copy(
                inputPlantAmount = FormHandler(state, message),
            )
        }
    }

    private fun updateInputProductionCost(state: Boolean, message: String) {
        _uiState.update {
            it.copy(
                inputProductionCost = FormHandler(state, message)
            )
        }
    }

    private fun updateInputLandMarker(state: Boolean, message: String) {
        _uiState.update {
            it.copy(
                inputLandMarker = FormHandler(state, message)
            )
        }
    }

    fun updateCommodity(input: CommodityModel) {
        updateInputCommodity(true, "")
        _uiState.update {
            it.copy(commodity = input)
        }
    }

    fun updateLandArea(input: String) {
        updateInputLandArea(true, "")
        _uiState.update {
            it.copy(landArea = input)
        }
        try {
            if (input.toDouble() <= 0) {
                updateInputLandArea(false, "Luas Lahan Tanam tidak valid")
            } else if (input.toDouble() > _uiState.value.remainingLandArea) {
                updateInputLandArea(
                    false,
                    "Tidak boleh lebih dari sisa lahan ${_uiState.value.remainingLandArea} ${_uiState.value.unit.label}"
                )
            } else {
                updateInputLandArea(
                    true, ""
                )
            }
        } catch (e: Exception) {
            updateInputLandArea(
                false,
                "Luas Lahan Tanam tidak valid. Gunakan tanda pemisah desimal \".\""
            )
        }
    }

    fun updateDatePlant(input: String) {
        updateInputDatePlant(true, "")
        _uiState.update {
            it.copy(datePlant = input)
        }
    }

    fun updatePlantAmount(input: String) {
        updateInputPlantAmount(true, "")
        _uiState.update {
            it.copy(plantAmount = input)
        }
        try {
            if (input.toDouble() <= 0) {
                updateInputPlantAmount(false, "Jumlah Tanam tidak valid")
            }
        } catch (e: Exception) {
            updateInputLandArea(
                false,
                "Jumlah Tanam tidak valid. Gunakan tanda pemisah desimal \".\""
            )
        }
    }

    fun updateSelectedUnit(input: UnitSelectInputImpl) {
        if (input.value != _uiState.value.unit.value) {
            _uiState.update {
                it.copy(unit = input)
            }
            val remainingLand = when (input.state) {
                UnitState.HA -> _uiState.value.remainingLandArea / 10000
                UnitState.M2 -> _uiState.value.remainingLandArea * 10000
            }
            updateRemainingLandArea(remainingLand)
        }
    }

    fun updateTypeOfPlanting(input: TypeOfPlantingModel) {
        updateInputTypeOfPlanting(true, "")
        _uiState.update {
            it.copy(typeOfPlating = input)
        }
    }

    fun updateProductionCost(input: String) {
        updateInputProductionCost(true, "")
        _uiState.update {
            it.copy(productionCost = input)
        }
    }

    private fun updateMultipleError(error: ResponseMessage.ArrayMessage) {
        error.message.map {
            if (it.path == "commodityId") updateInputCommodity(false, it.message)
            if (it.path == "planting_size") updateInputLandArea(false, it.message)
            if (it.path == "planting_type") updateInputTypeOfPlanting(false, it.message)
            if (it.path == "production_cost") updateInputProductionCost(false, it.message)
            if (it.path == "longitude") updateInputLandMarker(false, it.message)
            if (it.path == "latitude") updateInputLandMarker(false, it.message)
            if (it.path == "date") updateInputDatePlant(false, it.message)
        }
    }
}