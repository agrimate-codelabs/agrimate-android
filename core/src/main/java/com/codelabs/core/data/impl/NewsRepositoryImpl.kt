package com.codelabs.core.data.impl

import android.util.Log
import com.codelabs.core.data.source.remote.NewsRemoteDataSource
import com.codelabs.core.domain.model.NewsModel
import com.codelabs.core.domain.repository.NewsRepository
import com.codelabs.core.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class NewsRepositoryImpl @Inject constructor(private val newsRemoteDataSource: NewsRemoteDataSource) :
    NewsRepository {
    override fun getNews(): Flow<Resource<List<NewsModel>>> = flow {
        emit(Resource.Loading())
        try {
            val response = newsRemoteDataSource.getNews()
            val model = response.data?.map {
                NewsModel(it.url, it.title, it.date, it.source, it.image)
            }.orEmpty()
            Log.d("TEST", "$model")
            emit(Resource.Success(model))
        } catch (e: Exception) {
            Log.d("TEST", e.message.toString())
            emit(Resource.Loading())
        }
    }
}