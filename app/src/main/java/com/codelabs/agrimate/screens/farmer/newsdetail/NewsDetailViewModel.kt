package com.codelabs.agrimate.screens.farmer.newsdetail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.codelabs.agrimate.ui.navigation.DestinationsArg
import dagger.hilt.android.lifecycle.HiltViewModel
import java.net.URLDecoder
import java.nio.charset.StandardCharsets
import javax.inject.Inject

@HiltViewModel
class NewsDetailViewModel @Inject constructor(savedStateHandle: SavedStateHandle) : ViewModel() {
    val url = savedStateHandle.get<String>(DestinationsArg.NEWS_URL_ARG).orEmpty()
    val title: String = URLDecoder.decode(
        savedStateHandle.get<String>(DestinationsArg.NEWS_TITLE_ARG).orEmpty(),
        StandardCharsets.UTF_8.toString()
    )
}