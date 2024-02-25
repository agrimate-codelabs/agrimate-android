package com.codelabs.core.data.source.remote.response.news

data class NewsItemResponse(
    val url: String,
    val title: String,
    val date: String,
    val source: String,
    val image: String
)
