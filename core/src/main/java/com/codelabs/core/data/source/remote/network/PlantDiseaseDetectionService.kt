package com.codelabs.core.data.source.remote.network

import com.codelabs.core.data.source.remote.response.Response
import com.codelabs.core.data.source.remote.response.diseasedetection.DiseaseDetectionResponse
import com.codelabs.core.data.source.remote.response.diseasedetection.ResponseDiseaseDetectionAPI
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Query


interface PlantDiseaseDetectionService {
    @POST("/customvision/v3.0/Prediction/f6507929-06d1-4799-bbe7-0a980a82fa44/classify/iterations/Iteration3/image/")
    suspend fun diseaseDetection(
        @Body image: RequestBody
    ): ResponseDiseaseDetectionAPI

    @Multipart
    @POST("/check-plant-health")
    suspend fun diseaseDetection(@Part file: MultipartBody.Part): Response<DiseaseDetectionResponse>

    @GET("/diseases-plants")
    suspend fun getDiseasesPlants(@Query("name") name: String): Response<List<DiseaseDetectionResponse>>
}
