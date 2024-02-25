package com.codelabs.agrimate.screens.shared.emailverification

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.codelabs.agrimate.ui.navigation.DestinationsArg
import com.codelabs.core.data.source.remote.body.CheckOTPBody
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
import java.net.URLDecoder
import java.nio.charset.StandardCharsets
import javax.inject.Inject

@HiltViewModel
class EmailVerificationViewModel @Inject constructor(
    private val authUseCase: AuthUseCase,
    savedStateHandle: SavedStateHandle
) :
    ViewModel() {
    private val _uiState = MutableStateFlow(EmailVerificationUiState())
    val uiState: StateFlow<EmailVerificationUiState> = _uiState.asStateFlow()

    private val _toastMessage = MutableSharedFlow<String>()
    val toastMessage = _toastMessage.asSharedFlow()

    init {
        val email = URLDecoder.decode(
            savedStateHandle.get<String>(DestinationsArg.EMAIL_ARG).orEmpty(),
            StandardCharsets.UTF_8.toString()
        )
        _uiState.update {
            it.copy(email = email)
        }
    }

    private fun sendMessage(message: String) {
        viewModelScope.launch {
            _toastMessage.emit(message)
        }
    }

    fun verify() {
        viewModelScope.launch {
            authUseCase.checkOTP(CheckOTPBody(_uiState.value.code)).collect { resource ->
                when (resource) {
                    is Resource.Error -> {
                        setIsLoading(false)
                        sendMessage("Kode tidak valid")
                    }

                    is Resource.Loading -> {
                        setIsLoading(true)
                    }

                    is Resource.Success -> {
                        setIsLoading(false)
                        updateUseId(resource.data?.userId.orEmpty())
                        setIsSuccess(true)
                    }
                }
            }
        }
    }

    private fun updateUseId(userId: String) {
        _uiState.update {
            it.copy(userId = userId)
        }
    }

    fun setCode(value: String) {
        _uiState.update {
            it.copy(code = value)
        }
    }

    private fun setIsLoading(state: Boolean) {
        _uiState.update {
            it.copy(isLoading = state)
        }
    }

    private fun setIsSuccess(state: Boolean) {
        _uiState.update {
            it.copy(isSuccess = state)
        }
    }
}