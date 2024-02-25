package com.codelabs.core.data.source.remote

import com.codelabs.core.data.source.remote.network.PlantDiseaseDetectionService
import com.codelabs.core.data.source.remote.response.Response
import com.codelabs.core.data.source.remote.response.diseasedetection.DiseaseDetectionResponse
import com.codelabs.core.data.source.remote.response.diseasedetection.ResponseDiseaseDetectionAPI
import okhttp3.MultipartBody
import okhttp3.RequestBody
import javax.inject.Inject

class DiseaseDetectionRemoteDataSource @Inject constructor(private val plantDiseaseDetectionService: PlantDiseaseDetectionService) {
    @Deprecated("Use checkDisease(file: MultipartBody.Part) instead")
    suspend fun checkDisease(image: RequestBody): ResponseDiseaseDetectionAPI =
        plantDiseaseDetectionService.diseaseDetection(image)

    suspend fun checkDisease(file: MultipartBody.Part): Response<DiseaseDetectionResponse> =
        plantDiseaseDetectionService.diseaseDetection(file)

    suspend fun getDiseasesPlants(name: String): Response<List<DiseaseDetectionResponse>> =
        plantDiseaseDetectionService.getDiseasesPlants(name)
}