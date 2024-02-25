package com.codelabs.agrimate.screens.shared.signin

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.codelabs.agrimate.common.FormHandler
import com.codelabs.core.data.source.remote.body.SignInBody
import com.codelabs.core.data.source.remote.response.ResponseMessage
import com.codelabs.core.domain.usecase.AuthUseCase
import com.codelabs.core.domain.usecase.DataStoreUseCase
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
class SignInViewModel @Inject constructor(
    private val authUseCase: AuthUseCase,
    private val dataStoreUseCase: DataStoreUseCase
) :
    ViewModel() {
    private val _uiState = MutableStateFlow(SignInUiState())
    val uiState: StateFlow<SignInUiState> = _uiState.asStateFlow()

    private val _toastMessage = MutableSharedFlow<String>()
    val toastMessage = _toastMessage.asSharedFlow()

    private fun sendMessage(message: String) {
        viewModelScope.launch {
            _toastMessage.emit(message)
        }
    }

    fun signIn() {
        viewModelScope.launch {
            authUseCase.signIn(
                SignInBody(
                    email = _uiState.value.loginId,
                    password = _uiState.value.password
                )
            ).collect { resource ->
                resetFormValidationState()
                when (resource) {
                    is Resource.Success -> {
                        dataStoreUseCase.saveAccessToken(resource.data?.accessToken.toString())
                        dataStoreUseCase.saveRefreshToken(resource.data?.refreshToken.toString())
                        authMe()
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

    private suspend fun authMe() {
        authUseCase.authMe().collect { resource ->
            when (resource) {
                is Resource.Error -> {
                    sendMessage(resource.message.toString())
                }

                is Resource.Loading -> {}

                is Resource.Success -> {
                    resource.data?.let {
                        dataStoreUseCase.saveUserId(it.id.orEmpty())
                        dataStoreUseCase.saveUsername(it.name.orEmpty())
                    }
                }
            }
        }
    }

    private fun updateMultipleError(error: ResponseMessage.ArrayMessage) {
        error.message.map {
            if (it.path == "email") updateIsEmailValid(
                false,
                it.message
            )
            if (it.path == "password") updateIsPasswordValid(
                false,
                it.message
            )
        }
    }

    fun updateLoginId(newLoginId: String) {
        _uiState.update {
            it.copy(loginId = newLoginId)
        }
    }

    fun updatePassword(newPassword: String) {
        _uiState.update {
            it.copy(password = newPassword)
        }
    }

    private fun resetFormValidationState() {
        updateIsEmailValid(true, "")
        updateIsPasswordValid(true, "")
    }

    private fun updateIsEmailValid(isValid: Boolean, message: String) {
        _uiState.update {
            it.copy(inputEmail = FormHandler(isValid, message))
        }
    }

    private fun updateIsPasswordValid(isValid: Boolean, message: String) {
        _uiState.update {
            it.copy(inputPassword = FormHandler(isValid, message))
        }
    }

    private fun updateIsLoading(isLoading: Boolean) {
        _uiState.update {
            it.copy(isLoading = isLoading)
        }
    }

    private fun updateIsSuccess(isSuccess: Boolean) {
        _uiState.update {
            it.copy(isSuccess = isSuccess)
        }
    }
}