package com.codelabs.core.data.source.remote.network

import com.codelabs.core.data.source.remote.body.CreateFarmerLandBody
import com.codelabs.core.data.source.remote.response.Response
import com.codelabs.core.data.source.remote.response.farmerland.FarmerLandListItemResponse
import com.codelabs.core.data.source.remote.response.farmerland.FarmerLandResponse
import com.codelabs.core.data.source.remote.response.farmerland.RemainingLandAreaResponse
import okhttp3.MultipartBody
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Part
import retrofit2.http.Path

interface FarmerLandService {
    @POST("farmer-land")
    suspend fun create(@Body body: CreateFarmerLandBody): Response<Nothing>

    @PUT("farmer-land/{id}")
    suspend fun update(@Path("id") id: String, @Body body: CreateFarmerLandBody): Response<Nothing>

    @GET("farmer-land/my-farmland")
    suspend fun get(): Response<List<FarmerLandListItemResponse>>

    @GET("farmer-land/{id}")
    suspend fun get(@Path("id") id: String): Response<FarmerLandResponse>

    @Multipart
    @POST("farmer-land/upload")
    suspend fun uploadImage(@Part image: MultipartBody.Part): Response<String>

    @DELETE("farmer-land/{id}")
    suspend fun deleteLand(@Path("id") id: String): Response<FarmerLandListItemResponse>

    @GET("farmer-land/remaining-land-area/{id}")
    suspend fun getRemainingLandArea(@Path("id") id: String): Response<RemainingLandAreaResponse>
}