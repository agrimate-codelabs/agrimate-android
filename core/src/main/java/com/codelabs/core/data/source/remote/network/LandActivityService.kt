package com.codelabs.core.data.source.remote.network

import com.codelabs.core.data.source.remote.body.LandActivityBody
import com.codelabs.core.data.source.remote.response.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface LandActivityService {
    @GET("land-activity")
    suspend fun get(): Response<Nothing>

    @GET("land-activity/detail/{id}")
    suspend fun getById(@Path("id") id: String): Response<Nothing>

    @POST("land-activity")
    suspend fun create(@Body body: LandActivityBody): Response<Nothing>
}