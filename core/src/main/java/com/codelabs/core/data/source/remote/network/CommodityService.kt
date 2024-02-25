package com.codelabs.core.data.source.remote.network

import com.codelabs.core.data.source.remote.response.Response
import com.codelabs.core.data.source.remote.response.commodity.CommoditiesItem
import com.codelabs.core.data.source.remote.response.commodity.CommodityDataResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface CommodityService {
    @GET("/commodity")
    suspend fun getAll(): Response<CommodityDataResponse>

    @GET("/commodity/{id}")
    suspend fun get(@Path("id") commodityId: String): Response<CommoditiesItem>
}