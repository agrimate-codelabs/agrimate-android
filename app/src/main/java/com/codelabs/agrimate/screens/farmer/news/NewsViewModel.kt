package com.codelabs.agrimate.screens.farmer.news

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.codelabs.core.domain.usecase.NewsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class NewsViewModel @Inject constructor(newsUseCase: NewsUseCase) : ViewModel() {
    val news = newsUseCase.getNews().asLiveData()
}