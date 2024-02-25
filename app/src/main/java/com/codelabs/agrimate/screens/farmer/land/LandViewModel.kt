package com.codelabs.agrimate.screens.farmer.land

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.codelabs.core.domain.model.FarmerLandListItemModel
import com.codelabs.core.domain.usecase.FarmerLandUseCase
import com.codelabs.core.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LandViewModel @Inject constructor(private val farmerLandUseCase: FarmerLandUseCase) :
    ViewModel() {
    private val _uiState = MutableStateFlow(LandUiState())
    val uiState = _uiState.asStateFlow()

    private val _farmerLand =
        MutableStateFlow<Resource<List<FarmerLandListItemModel>>>(Resource.Loading())

    val farmerLand: StateFlow<Resource<List<FarmerLandListItemModel>>> = _farmerLand

    fun getFarmerLand() {
        viewModelScope.launch {
            farmerLandUseCase.getFarmerLand().collect {
                when (it) {
                    is Resource.Error -> {
                        _farmerLand.value = it
                    }

                    is Resource.Loading -> {}
                    is Resource.Success -> {
                        _farmerLand.value = it
                    }
                }
            }
        }
    }

    fun deleteLand(id: String) {
        viewModelScope.launch {
            farmerLandUseCase.deleteLand(id).collect {
                Log.d("Test Delete", "$it")
                when (it) {
                    is Resource.Error -> {
                        updateIsDeleting(false)
                    }

                    is Resource.Loading -> {
                        updateIsDeleting(true)
                    }

                    is Resource.Success -> {
                        updateIsDeleting(false)
                        getFarmerLand()
                    }
                }
            }
        }
    }

    private fun updateIsDeleting(state: Boolean) {
        _uiState.update {
            it.copy(isDeleting = state)
        }
    }
}