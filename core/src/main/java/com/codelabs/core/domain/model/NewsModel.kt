package com.codelabs.core.domain.model;

data class NewsModel(
    val url: String,
    val title: String,
    val date: String,
    val source: String,
    val image: String
)