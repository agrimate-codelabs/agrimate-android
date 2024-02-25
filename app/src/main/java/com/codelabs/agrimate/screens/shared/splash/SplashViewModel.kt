package com.codelabs.agrimate.screens.shared.splash

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.codelabs.core.domain.usecase.AuthUseCase
import com.codelabs.core.domain.usecase.DataStoreUseCase
import com.codelabs.core.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val dataStoreUseCase: DataStoreUseCase,
    private val authUseCase: AuthUseCase
) :
    ViewModel() {
    private val _isAuthorized: MutableStateFlow<Resource<Boolean>> =
        MutableStateFlow(Resource.Loading())
    val isAuthorized: StateFlow<Resource<Boolean>> = _isAuthorized.asStateFlow()

    fun authMe() {
        viewModelScope.launch {
            _isAuthorized.value = Resource.Loading()
            authUseCase.authMe().collect { resource ->
                when (resource) {
                    is Resource.Error -> {
                        _isAuthorized.value =
                            Resource.Error(message = resource.message.toString(), data = false)
                    }

                    is Resource.Loading -> {
                    }

                    is Resource.Success -> {
                        resource.data?.let {
                            dataStoreUseCase.saveUserId(it.id.orEmpty())
                            dataStoreUseCase.saveUsername(it.name.orEmpty())
                        }
                        _isAuthorized.value = Resource.Success(true)
                    }
                }
            }
        }
    }
}