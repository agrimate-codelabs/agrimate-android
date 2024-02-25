package com.codelabs.agrimate.screens.farmer.home

import com.codelabs.agrimate.ui.common.state.UiState
import com.codelabs.core.domain.model.NewsModel

data class HomeUiState(
    val username: String = "",
    val currentDate: String = "",

    val news: UiState<List<NewsModel>> = UiState.Loading
)