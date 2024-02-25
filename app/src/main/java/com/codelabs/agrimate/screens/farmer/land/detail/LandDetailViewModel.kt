package com.codelabs.agrimate.screens.farmer.land.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.codelabs.agrimate.ui.navigation.DestinationsArg
import com.codelabs.core.domain.usecase.FarmerLandDetailUseCase
import com.codelabs.core.domain.usecase.PlantingUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LandDetailViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val farmerLandDetailUseCase: FarmerLandDetailUseCase,
    private val plantingUseCase: PlantingUseCase
) :
    ViewModel() {
    private val _uiState = MutableStateFlow(LandDetailUiState())
    val uiState = _uiState.asStateFlow()

    val landId = savedStateHandle.get<String>(DestinationsArg.LAND_ID_ARG).orEmpty()

    val landDetail = farmerLandDetailUseCase.getFarmerLand(landId).asLiveData()

    val plantNutritionData = farmerLandDetailUseCase.getPlantNutrition(landId).asLiveData()

    fun getListOfPlanting() {
        viewModelScope.launch {
            plantingUseCase.getAll(landId).collect { resource ->
                _uiState.update {
                    it.copy(listOfPlatingItem = resource)
                }
            }
        }
    }

    fun getRemainingLandArea() {
        viewModelScope.launch {
            farmerLandDetailUseCase.getRemainingLandArea(landId).collect { resource ->
                _uiState.update {
                    it.copy(remainingLandArea = resource)
                }
            }
        }
    }
}
