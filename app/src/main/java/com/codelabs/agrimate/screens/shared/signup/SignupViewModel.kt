package com.codelabs.agrimate.screens.shared.signup

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.codelabs.agrimate.common.FormHandler
import com.codelabs.agrimate.ui.common.impl.ProfessionSelectInputImpl
import com.codelabs.agrimate.ui.common.impl.RegionSelectInputImpl
import com.codelabs.core.data.source.remote.body.SignUpBody
import com.codelabs.core.data.source.remote.response.ResponseMessage
import com.codelabs.core.domain.model.RegionModel
import com.codelabs.core.domain.usecase.AuthUseCase
import com.codelabs.core.domain.usecase.RegionUseCase
import com.codelabs.core.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignupViewModel @Inject constructor(
    private val authUseCase: AuthUseCase,
    private val regionUseCase: RegionUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow(SignupUiState())
    val uiState: StateFlow<SignupUiState> = _uiState

    private val _toastMessage = MutableSharedFlow<String>()
    val toastMessage = _toastMessage.asSharedFlow()

    private val _selectedProvinceCode = MutableStateFlow("")
    private val _selectedCityCode = MutableStateFlow("")
    private val _selectedDistrictCode = MutableStateFlow("")

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

    private fun sendMessage(message: String) {
        viewModelScope.launch {
            _toastMessage.emit(message)
        }
    }

    fun signUp() {
        viewModelScope.launch {
            authUseCase.signUp(
                SignUpBody(
                    confirmPassword = _uiState.value.confirmPassword,
                    password = _uiState.value.password,
                    role = _uiState.value.role.value,
                    name = _uiState.value.name,
                    email = _uiState.value.email,
                    phone = _uiState.value.phoneNumber,
                    provinceId = _uiState.value.province.code,
                    cityId = _uiState.value.city.code,
                    districtId = _uiState.value.district.code,
                    villageId = _uiState.value.villages.code,
                    address = _uiState.value.fullAddress,
                )
            ).collect { resource ->
                resetFormValidationState()
                when (resource) {
                    is Resource.Success -> {
                        updateIsLoading(false)
                        updateIsSuccess(true)
                    }

                    is Resource.Loading -> {
                        updateIsLoading(true)
                    }

                    is Resource.Error -> {
                        updateIsLoading(false)
                        updateIsSuccess(false)
                        if (resource.data != null) {
                            when (val error = resource.data?.message) {
                                is ResponseMessage.ArrayMessage -> {
                                    updateMultipleError(error)
                                }

                                is ResponseMessage.StringMessage -> {
                                    sendMessage("Daftar akun tidak berhasil!")
                                }

                                null -> {
                                    sendMessage(resource.message.toString())
                                }
                            }
                        } else {
                            sendMessage(resource.message.toString())
                        }
                    }
                }
            }
        }
    }

    private fun updateIsSuccess(state: Boolean) {
        _uiState.update {
            it.copy(isSuccess = state)
        }
    }

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

    private fun resetFormValidationState() {
        updateIsRoleValid(true, "")
        updateIsNameValid(true, "")
        updateIsEmailValid(true, "")
        updateIsPhoneNumberValid(true, "")
        updateIsProvinceValid(true, "")
        updateIsCityValid(true, "")
        updateIsDistrictValid(true, "")
        updateIsVillageValid(true, "")
        updateIsFullAddressValid(true, "")
        updateIsPasswordValid(true, "")
        updateIsConfirmPasswordValid(true, "")
    }

    private fun updateMultipleError(error: ResponseMessage.ArrayMessage) {
        error.message.map {
            if (it.path == "role") updateIsRoleValid(false, it.message)
            if (it.path == "name") updateIsNameValid(
                false,
                it.message
            )
            if (it.path == "email") updateIsEmailValid(
                false,
                it.message
            )
            if (it.path == "phone") updateIsPhoneNumberValid(
                false,
                it.message
            )
            if (it.path == "province") updateIsProvinceValid(
                false,
                it.message
            )
            if (it.path == "city") updateIsCityValid(
                false,
                it.message
            )
            if (it.path == "district") updateIsDistrictValid(
                false,
                it.message
            )
            if (it.path == "village") updateIsVillageValid(
                false,
                it.message
            )
            if (it.path == "address") updateIsFullAddressValid(
                false,
                it.message
            )
            if (it.path == "password") updateIsPasswordValid(
                false,
                it.message
            )
            if (it.path == "confirmPassword") updateIsConfirmPasswordValid(
                false,
                it.message
            )
        }
    }

    private fun updateIsRoleValid(state: Boolean, message: String) {
        _uiState.update {
            it.copy(inputRole = FormHandler(state, message))
        }
    }

    private fun updateIsNameValid(state: Boolean, message: String) {
        _uiState.update {
            it.copy(inputName = FormHandler(state, message))
        }
    }

    private fun updateIsEmailValid(state: Boolean, message: String) {
        _uiState.update {
            it.copy(inputEmail = FormHandler(state, message))
        }
    }

    private fun updateIsPhoneNumberValid(state: Boolean, message: String) {
        _uiState.update {
            it.copy(inputPhoneNumber = FormHandler(state, message))
        }
    }

    private fun updateIsProvinceValid(state: Boolean, message: String) {
        _uiState.update {
            it.copy(inputProvince = FormHandler(state, message))
        }
    }

    private fun updateIsCityValid(state: Boolean, message: String) {
        _uiState.update {
            it.copy(inputCity = FormHandler(state, message))
        }
    }

    private fun updateIsDistrictValid(state: Boolean, message: String) {
        _uiState.update {
            it.copy(inputDistrict = FormHandler(state, message))
        }
    }

    private fun updateIsVillageValid(state: Boolean, message: String) {
        _uiState.update {
            it.copy(inputVillage = FormHandler(state, message))
        }
    }

    private fun updateIsFullAddressValid(state: Boolean, message: String) {
        _uiState.update {
            it.copy(inputFullAddress = FormHandler(state, message))
        }
    }

    private fun updateIsPasswordValid(state: Boolean, message: String) {
        _uiState.update {
            it.copy(inputPassword = FormHandler(state, message))
        }
    }

    private fun updateIsConfirmPasswordValid(state: Boolean, message: String) {
        _uiState.update {
            it.copy(inputConfirmPassword = FormHandler(state, message))
        }
    }

    fun updateRole(newRole: ProfessionSelectInputImpl) {
        _uiState.update {
            it.copy(role = newRole)
        }
    }

    fun updateName(newName: String) {
        _uiState.update {
            it.copy(name = newName)
        }
    }

    fun updateEmail(newEmail: String) {
        _uiState.update {
            it.copy(email = newEmail)
        }
    }

    fun updatePhoneNumber(newPhoneNumber: String) {
        _uiState.update {
            it.copy(phoneNumber = newPhoneNumber)
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

    fun updateProvince(newProvince: RegionModel) {
        _uiState.update {
            it.copy(
                province = newProvince,
                city = RegionModel("", ""),
                district = RegionModel("", ""),
                villages = RegionModel("", "")
            )
        }
        updateSelectedRegionCode(provinceCode = newProvince.code, cityCode = "", districtCode = "")
    }

    fun updateCity(newCity: RegionModel) {
        _uiState.update {
            it.copy(city = newCity, district = RegionModel("", ""), villages = RegionModel("", ""))
        }
        updateSelectedRegionCode(
            provinceCode = _selectedProvinceCode.value,
            cityCode = newCity.code,
            districtCode = ""
        )
    }

    fun updateDistrict(newDistrict: RegionModel) {
        _uiState.update {
            it.copy(district = newDistrict, villages = RegionModel("", ""))
        }
        updateSelectedRegionCode(
            provinceCode = _selectedProvinceCode.value,
            cityCode = _selectedCityCode.value,
            districtCode = newDistrict.code
        )
    }

    fun updateVillages(newVillages: RegionModel) {
        _uiState.update {
            it.copy(villages = newVillages)
        }
    }

    fun updateFullAddress(newFullAddress: String) {
        _uiState.update {
            it.copy(fullAddress = newFullAddress)
        }
    }

    fun updatePassword(newPassword: String) {
        _uiState.update {
            it.copy(password = newPassword)
        }
    }

    fun updateConfirmPassword(confirmPassword: String) {
        _uiState.update {
            it.copy(confirmPassword = confirmPassword)
        }
    }

    private fun updateIsLoading(state: Boolean) {
        _uiState.update {
            it.copy(isLoading = state)
        }
    }
}