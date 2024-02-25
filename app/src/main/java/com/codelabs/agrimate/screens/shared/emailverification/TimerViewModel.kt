package com.codelabs.agrimate.screens.shared.emailverification

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TimerViewModel @Inject constructor() : ViewModel() {
    private var job: Job? = null
    private val _timerValue = MutableStateFlow(900)
    val timerValue = _timerValue.asStateFlow()

    private val _play = MutableStateFlow(true)
    val play = _play.asStateFlow()

    fun start(times: Int = 900) {
        _timerValue.value = times
        job?.cancel()
        job = viewModelScope.launch(Dispatchers.IO) {
            delay(timeMillis = 1000)
            while (isActive) {
                if (_timerValue.value <= 0) {
                    job?.cancel()
                    _play.value = false
                    return@launch
                }
                delay(timeMillis = 1000)
                _timerValue.value -= 1
                _play.value = true
            }
        }
    }
}