package com.codelabs.core.data.source.remote.network

import com.codelabs.core.data.source.remote.body.CreatePlantingBody
import com.codelabs.core.data.source.remote.response.Response
import com.codelabs.core.data.source.remote.response.planting.PlantingDataResponse
import com.codelabs.core.data.source.remote.response.planting.PlantingListDataResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface PlantingService {
    @POST("/planting")
    suspend fun create(@Body createPlantingBody: CreatePlantingBody): Response<PlantingDataResponse>

    @GET("/planting/by-farmland/{farmlandId}")
    suspend fun getAll(@Path("farmlandId") id: String): Response<PlantingListDataResponse>

    @GET("planting/{plantingId}")
    suspend fun get(@Path("plantingId") plantingId: String): Response<PlantingDataResponse>
}