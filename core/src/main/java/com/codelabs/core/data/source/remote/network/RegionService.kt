package com.codelabs.core.data.source.remote.network

import com.codelabs.core.data.source.remote.response.Response
import com.codelabs.core.data.source.remote.response.region.RegionResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface RegionService {
    @GET("/region")
    suspend fun getListRegion(
        @Query("code") code: String? = null,
        @Query("type") type: String
    ): Response<List<RegionResponse>>

    @GET("/region")
    suspend fun getRegionName(@Query("code") code: String?): Response<RegionResponse>
}