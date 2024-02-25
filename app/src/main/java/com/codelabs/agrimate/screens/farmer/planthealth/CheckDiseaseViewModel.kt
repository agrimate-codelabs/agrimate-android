package com.codelabs.agrimate.screens.farmer.planthealth

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.codelabs.agrimate.ui.common.state.UiState
import com.codelabs.core.domain.model.PlantDiseaseDetectionModel
import com.codelabs.core.domain.usecase.PlantDiseaseUseCase
import com.codelabs.core.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import javax.inject.Inject

@HiltViewModel
class CheckDiseaseViewModel @Inject constructor(private val plantDiseaseUseCase: PlantDiseaseUseCase) :
    ViewModel() {
    private val _uiState = MutableStateFlow(CheckDiseaseUiState())
    val uiState = _uiState.asStateFlow()

    private val _predictRes: MutableStateFlow<UiState<PlantDiseaseDetectionModel>> =
        MutableStateFlow(UiState.Loading)
    val predictRes: StateFlow<UiState<PlantDiseaseDetectionModel>> = _predictRes.asStateFlow()

    private val _toastMessage = MutableSharedFlow<String>()
    val toastMessage = _toastMessage.asSharedFlow()

    private fun sendMessage(message: String) {
        viewModelScope.launch {
            _toastMessage.emit(message)
        }
    }

    suspend fun predict(image: File) {
        _predictRes.value = UiState.Loading
        val imageRequestBody = image.asRequestBody("image/jpeg".toMediaType())
        val file: MultipartBody.Part =
            MultipartBody.Part.createFormData("file", image.name, imageRequestBody)
        plantDiseaseUseCase.predictDisease(file).collect { resource ->
            when (resource) {
                is Resource.Error -> {
                    image.delete()
                    _predictRes.value = UiState.Error(resource.message.toString())
                    Log.d("Test", resource.message.toString())
                    sendMessage(resource.message.toString())
                }

                is Resource.Loading -> {
                    _predictRes.value = UiState.Loading
                }

                is Resource.Success -> {
                    image.delete()

                    resource.data?.let { model ->
                        _predictRes.value = UiState.Success(model)
                    }
                }
            }
        }
    }

    fun resetPrediction() {
        _predictRes.value = UiState.Loading
    }

    fun updateSelectedImage(string: String) {
        _uiState.update {
            it.copy(selectedImage = string)
        }
    }
}