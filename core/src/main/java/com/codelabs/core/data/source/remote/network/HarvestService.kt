package com.codelabs.core.data.source.remote.network

import com.codelabs.core.data.source.remote.body.CreateHarvestBody
import com.codelabs.core.data.source.remote.response.Response
import com.codelabs.core.data.source.remote.response.harvest.HarvestResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface HarvestService {
    @POST("harvesting")
    suspend fun addSuccess(@Body body: CreateHarvestBody): Response<HarvestResponse>

    @POST("harvesting/crop-failure")
    suspend fun addFailure(@Body body: CreateHarvestBody): Response<HarvestResponse>
}