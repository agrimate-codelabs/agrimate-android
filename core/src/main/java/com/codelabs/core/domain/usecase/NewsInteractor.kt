package com.codelabs.core.domain.usecase

import com.codelabs.core.domain.model.NewsModel
import com.codelabs.core.domain.repository.NewsRepository
import com.codelabs.core.utils.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class NewsInteractor @Inject constructor(private val newsRepository: NewsRepository) : NewsUseCase {
    override fun getNews(): Flow<Resource<List<NewsModel>>> = newsRepository.getNews()
}