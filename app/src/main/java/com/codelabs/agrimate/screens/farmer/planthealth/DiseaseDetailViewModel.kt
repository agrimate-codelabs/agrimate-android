package com.codelabs.agrimate.screens.farmer.planthealth

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.codelabs.agrimate.ui.common.state.UiState
import com.codelabs.agrimate.ui.navigation.DestinationsArg
import com.codelabs.core.domain.model.PlantDiseaseDetectionModel
import com.codelabs.core.domain.usecase.PlantDiseaseUseCase
import com.codelabs.core.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DiseaseDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val plantDiseaseUseCase: PlantDiseaseUseCase
) : ViewModel() {
    private val diseaseName =
        savedStateHandle.get<String>(DestinationsArg.PLANT_DISEASE_ID_ARG).orEmpty()

    private val _uiState = MutableStateFlow<UiState<PlantDiseaseDetectionModel>>(UiState.Loading)
    val uiState: StateFlow<UiState<PlantDiseaseDetectionModel>> get() = _uiState.asStateFlow()

    init {
        getDiseasePlant()
    }

    private fun getDiseasePlant() {
        viewModelScope.launch {
            plantDiseaseUseCase.getDiseasePlant(diseaseName).collect { resource ->
                when (resource) {
                    is Resource.Error -> {
                        _uiState.value = UiState.Error(resource.message.toString())
                    }

                    is Resource.Loading -> {
                        _uiState.value = UiState.Loading
                    }

                    is Resource.Success -> {
                        resource.data?.let {
                            _uiState.value = UiState.Success(it)
                        }
                    }
                }
            }
        }
    }
}