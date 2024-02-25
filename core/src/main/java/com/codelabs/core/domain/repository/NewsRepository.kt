package com.codelabs.core.domain.repository

import com.codelabs.core.domain.model.NewsModel
import com.codelabs.core.utils.Resource
import kotlinx.coroutines.flow.Flow

interface NewsRepository {
    fun getNews(): Flow<Resource<List<NewsModel>>>
}