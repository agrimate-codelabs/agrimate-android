package com.codelabs.agrimate.screens.farmer.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.codelabs.core.domain.usecase.AuthUseCase
import com.codelabs.core.domain.usecase.DataStoreUseCase
import com.codelabs.core.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val dataStoreUseCase: DataStoreUseCase,
    private val authUseCase: AuthUseCase
) : ViewModel() {
    private val _isLogout: MutableStateFlow<Resource<Boolean>> =
        MutableStateFlow(Resource.Success(false))
    val isLogout: StateFlow<Resource<Boolean>> = _isLogout.asStateFlow()

    private val _toastMessage = MutableSharedFlow<String>()
    val toastMessage = _toastMessage.asSharedFlow()

    private val _uiState = MutableStateFlow(ProfileUiState())

    val uiState = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            getUsername()
        }
    }

    private suspend fun getUsername() {
        val username = dataStoreUseCase.getUsername().firstOrNull()
        _uiState.update {
            it.copy(username = username.orEmpty())
        }
    }


    private fun sendMessage(message: String) {
        viewModelScope.launch {
            _toastMessage.emit(message)
        }
    }

    fun logout() {
        viewModelScope.launch {
            authUseCase.logout().collect { resource ->
                when (resource) {
                    is Resource.Error -> {
                        sendMessage(resource.message.toString())
                        _isLogout.value = Resource.Success(true)
                        dataStoreUseCase.clearTokens()
                    }

                    is Resource.Loading -> {
                        _isLogout.value = Resource.Loading()
                    }

                    is Resource.Success -> {
                        _isLogout.value = Resource.Success(true)
                        dataStoreUseCase.clearTokens()
                    }
                }
            }
        }
    }
}