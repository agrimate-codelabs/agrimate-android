package com.codelabs.core.domain.usecase

import com.codelabs.core.domain.model.NewsModel
import com.codelabs.core.utils.Resource
import kotlinx.coroutines.flow.Flow

interface NewsUseCase {
    fun getNews(): Flow<Resource<List<NewsModel>>>
}