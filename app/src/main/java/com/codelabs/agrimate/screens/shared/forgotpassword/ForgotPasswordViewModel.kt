package com.codelabs.agrimate.screens.shared.forgotpassword

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.codelabs.agrimate.common.FormHandler
import com.codelabs.core.data.source.remote.body.ForgotPasswordBody
import com.codelabs.core.data.source.remote.response.ResponseMessage
import com.codelabs.core.domain.usecase.AuthUseCase
import com.codelabs.core.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ForgotPasswordViewModel @Inject constructor(
    private val authUseCase: AuthUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow(ForgotPasswordUiState())
    val uiState: StateFlow<ForgotPasswordUiState> = _uiState.asStateFlow()

    private val _toastMessage = MutableSharedFlow<String>()
    val toastMessage = _toastMessage.asSharedFlow()

    private fun sendMessage(message: String) {
        viewModelScope.launch {
            _toastMessage.emit(message)
        }
    }

    fun forgotPassword() {
        viewModelScope.launch {
            resetInputValidation()
            authUseCase.forgotPassword(ForgotPasswordBody(email = _uiState.value.email))
                .collect { resource ->
                    when (resource) {
                        is Resource.Success -> {
                            setIsLoading(false)
                            setIsCodeSent(true)
                        }

                        is Resource.Loading -> {
                            setIsLoading(true)
                        }

                        is Resource.Error -> {
                            setIsLoading(false)
                            if (resource.data != null) {
                                when (val error = resource.data?.message) {
                                    is ResponseMessage.ArrayMessage -> {
                                        updateMultipleError(error)
                                    }

                                    is ResponseMessage.StringMessage -> {
                                        sendMessage(error.message)
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

    private fun updateMultipleError(error: ResponseMessage.ArrayMessage) {
        error.message.map {
            if (it.path == "email") setInputEmail(
                false,
                it.message
            )
        }
    }

    private fun resetInputValidation() {
        setInputEmail(true, "")
    }

    fun setEmail(input: String) {
        _uiState.update {
            it.copy(email = input)
        }
    }

    private fun setIsCodeSent(state: Boolean) {
        _uiState.update {
            it.copy(isCodeSent = state)
        }
    }

    private fun setInputEmail(state: Boolean, message: String) {
        _uiState.update {
            it.copy(inputEmail = FormHandler(state, message))
        }
    }

    private fun setIsLoading(state: Boolean) {
        _uiState.update {
            it.copy(isLoading = state)
        }
    }
}