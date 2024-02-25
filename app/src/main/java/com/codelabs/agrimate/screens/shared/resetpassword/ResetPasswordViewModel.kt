package com.codelabs.agrimate.screens.shared.resetpassword

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.codelabs.agrimate.ui.navigation.DestinationsArg
import com.codelabs.core.data.source.remote.body.ResetPasswordBody
import com.codelabs.core.domain.usecase.AuthUseCase
import com.codelabs.core.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ResetPasswordViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val authUseCase: AuthUseCase
) :
    ViewModel() {
    private val _uiState = MutableStateFlow(ResetPasswordUiState())
    val uiState = _uiState.asStateFlow()

    private val userId = savedStateHandle.get<String>(DestinationsArg.USER_ID_ARG).orEmpty()

    private val _toastMessage = MutableSharedFlow<String>()
    val toastMessage = _toastMessage.asSharedFlow()

    private fun sendMessage(message: String) {
        viewModelScope.launch {
            _toastMessage.emit(message)
        }
    }

    fun savePassword() {
        Log.d("TEST", userId)
        viewModelScope.launch {
            authUseCase.resetPassword(
                ResetPasswordBody(
                    userId = userId,
                    password = _uiState.value.password,
                    confirmPassword = _uiState.value.confirmPassword
                )
            ).collect { resource ->
                when (resource) {
                    is Resource.Error -> {
                        updateIsLoading(false)
                        updateIsSuccess(false)
                        sendMessage(resource.message ?: "Terjadi kesalahan")
                    }

                    is Resource.Loading -> {
                        updateIsLoading(true)
                    }

                    is Resource.Success -> {
                        updateIsLoading(false)
                        updateIsSuccess(true)
                        sendMessage(resource.data?.message.orEmpty())
                    }
                }
            }
        }
    }

    fun updatePassword(value: String) {
        _uiState.update {
            it.copy(password = value)
        }
    }

    fun updateConfirmPassword(value: String) {
        _uiState.update {
            it.copy(confirmPassword = value)
        }
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
}