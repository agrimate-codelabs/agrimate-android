package com.codelabs.agrimate.screens.farmer.land.detail.activity.detail

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.codelabs.agrimate.common.BaseViewModel
import com.codelabs.agrimate.common.FormHandler
import com.codelabs.agrimate.ui.common.state.UiState
import com.codelabs.agrimate.ui.navigation.DestinationsArg
import com.codelabs.core.data.source.remote.body.CreateHarvestBody
import com.codelabs.core.domain.model.PlantingModel
import com.codelabs.core.domain.usecase.FarmerLandUseCase
import com.codelabs.core.domain.usecase.HarvestUseCase
import com.codelabs.core.domain.usecase.PlantingUseCase
import com.codelabs.core.utils.Resource
import com.google.android.gms.maps.model.LatLng
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LandDetailActivityViewModel @Inject constructor(
    private val plantingUseCase: PlantingUseCase,
    private val farmerLandUseCase: FarmerLandUseCase,
    private val harvestUseCase: HarvestUseCase,
    savedStateHandle: SavedStateHandle
) :
    BaseViewModel() {
    private val plantingId =
        savedStateHandle.get<String>(DestinationsArg.LAND_ACTIVITY_ID_ARG).orEmpty()

    private val landId = savedStateHandle.get<String>(DestinationsArg.LAND_ID_ARG).orEmpty()

    private val _uiState = MutableStateFlow(LandDetailActivityUiState())
    val uiState = _uiState.asStateFlow()

    private val _plantingDetailUiState = MutableStateFlow<UiState<PlantingModel>>(UiState.Loading)
    val plantingDetailUiState = _plantingDetailUiState.asStateFlow()

    private val _mapPolygon = MutableStateFlow<List<LatLng>>(emptyList())
    val mapPolygon = _mapPolygon.asStateFlow()

    init {
        getPlantingLandActivityDetail()
        getLandAreaPolygon()
    }

    fun saveFailed() {
        viewModelScope.launch {
            harvestUseCase.saveFailed(
                body = CreateHarvestBody(
                    plantingId = plantingId,
                )
            ).collect { resource ->
                when (resource) {
                    is Resource.Error -> {
                        sendMessage(resource.message.orEmpty())
                        updateIsLoading(false)
                    }

                    is Resource.Loading -> {
                        updateIsLoading(true)
                    }

                    is Resource.Success -> {
                        updateIsLoading(false)
                        updateIsSaveFailed(true)
                    }
                }
            }
        }
    }

    fun saveSuccess() {
        viewModelScope.launch {
            resetValidation()
            updateValidation()
            if (isFormValid()) {
                harvestUseCase.saveSuccess(
                    body = CreateHarvestBody(
                        plantingId = plantingId,
                        date = _uiState.value.dateHarvest,
                        amount = _uiState.value.amountHarvest.toDouble()
                    )
                ).collect { resource ->
                    when (resource) {
                        is Resource.Error -> {
                            sendMessage(resource.message.orEmpty())
                            updateIsLoading(false)
                        }

                        is Resource.Loading -> {
                            updateIsLoading(true)
                        }

                        is Resource.Success -> {
                            updateIsLoading(false)
                            updateIsSaveSuccess(true)
                        }
                    }
                }
            }
        }
    }

    fun getPlantingLandActivityDetail() {
        viewModelScope.launch {
            delay(250L)
            plantingUseCase.get(plantingId).collect { resource ->
                when (resource) {
                    is Resource.Error -> {
                        _plantingDetailUiState.value =
                            UiState.Error(resource.message ?: "Terjadi Kesalahan")
                        sendMessage(resource.message ?: "Terjadi Kesalahn")
                    }

                    is Resource.Loading -> {
                        _plantingDetailUiState.value = UiState.Loading
                    }

                    is Resource.Success -> {
                        Log.d("TEST", "plantingData ${resource.data}")
                        _plantingDetailUiState.value =
                            UiState.Success(data = resource.data ?: PlantingModel())
                    }
                }
            }
        }
    }

    private fun getLandAreaPolygon() {
        viewModelScope.launch {
            farmerLandUseCase.getFarmerLand(landId).collect { resource ->
                when (resource) {
                    is Resource.Error -> {
                        sendMessage(resource.message ?: "Terjadi Kesalahan")
                    }

                    is Resource.Loading -> {}
                    is Resource.Success -> {
                        val polygon = resource.data?.polygon?.map {
                            LatLng(it?.latitude ?: 0.0, it?.longitude ?: 0.0)
                        }.orEmpty()
                        _mapPolygon.value = polygon
                    }
                }
            }
        }
    }

    fun updateDateHarvest(input: String) {
        _uiState.update {
            it.copy(
                dateHarvest = input
            )
        }
    }

    fun updateAmountHarvest(input: String) {
        _uiState.update {
            it.copy(
                amountHarvest = input
            )
        }
    }

    private fun updateIsLoading(state: Boolean) {
        _uiState.update {
            it.copy(isLoading = state)
        }
    }

    private fun updateIsSaveFailed(state: Boolean) {
        _uiState.update {
            it.copy(isSaveFailed = state)
        }
    }

    private fun updateIsSaveSuccess(state: Boolean) {
        _uiState.update {
            it.copy(isSaveSuccess = state)
        }
    }

    private fun updateInputDateHarvest(state: Boolean, message: String = "") {
        _uiState.update {
            it.copy(inputDateHarvest = FormHandler(state, message))
        }
    }

    private fun updateInputAmountHarvest(state: Boolean, message: String = "") {
        _uiState.update {
            it.copy(inputAmountHarvest = FormHandler(state, message))
        }
    }

    fun resetValidation() {
        updateInputDateHarvest(true, "")
        updateInputAmountHarvest(true, "")
    }

    fun updateValidation() {
        if (_uiState.value.dateHarvest.isEmpty()) {
            updateInputDateHarvest(false, "Tanggal Panen wajib diisi")
        }
        if (_uiState.value.amountHarvest.isEmpty()) {
            updateInputAmountHarvest(false, "Jumlah Panen wajib diisi")
        } else {
            try {
                if (_uiState.value.amountHarvest.toDouble() <= 0) {
                    updateInputAmountHarvest(false, "Jumlah Panen tidak valid")
                }
            } catch (e: Exception) {
                updateInputAmountHarvest(false, "Jumlah Panen tidak valid")
            }
        }
    }

    fun isFormValid(): Boolean {
        return _uiState.value.run {
            _uiState.value.inputDateHarvest.isValid && _uiState.value.inputAmountHarvest.isValid
        }
    }
}