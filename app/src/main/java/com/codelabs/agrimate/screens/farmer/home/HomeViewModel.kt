package com.codelabs.agrimate.screens.farmer.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.codelabs.agrimate.ui.common.state.UiState
import com.codelabs.core.domain.usecase.DataStoreUseCase
import com.codelabs.core.domain.usecase.NewsUseCase
import com.codelabs.core.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val dataStoreUseCase: DataStoreUseCase,
    private val newsUseCase: NewsUseCase
) :
    ViewModel() {
    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            getUsername()
            getCurrentDate()
            getNews()
        }
    }

    private fun getCurrentDate() {
        try {
            val calendar = Calendar.getInstance()
            val dateFormat = SimpleDateFormat("EEEE d, MMMM yyyy", Locale("id", "ID"))
            val currentDate = dateFormat.format(calendar.time)
            _uiState.update {
                it.copy(currentDate = currentDate)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private suspend fun getUsername() {
        val username = dataStoreUseCase.getUsername().firstOrNull()
        _uiState.update {
            it.copy(username = username.orEmpty())
        }
    }

    private suspend fun getNews() {
        newsUseCase.getNews().collect { resource ->
            when (resource) {
                is Resource.Error -> {}
                is Resource.Loading -> {
                    _uiState.update {
                        it.copy(news = UiState.Loading)
                    }
                }

                is Resource.Success -> {
                    _uiState.update {
                        it.copy(news = UiState.Success(resource.data.orEmpty()))
                    }
                }
            }
        }
    }
}