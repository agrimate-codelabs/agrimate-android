package com.codelabs.core.data.source.remote

import com.codelabs.core.data.source.remote.body.LandActivityBody
import com.codelabs.core.data.source.remote.network.LandActivityService
import javax.inject.Inject

class LandActivityRemoteDataSource @Inject constructor(private val landActivityService: LandActivityService) {
    suspend fun get() = landActivityService.get()
    suspend fun getById(id: String) = landActivityService.getById(id)

    suspend fun create(body: LandActivityBody) = landActivityService.create(body)
}