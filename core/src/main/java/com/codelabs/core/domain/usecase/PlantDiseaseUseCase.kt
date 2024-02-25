package com.codelabs.core.domain.usecase

import com.codelabs.core.domain.model.PlantDiseaseDetectionModel
import com.codelabs.core.domain.model.PlantDiseasePredictionModel
import com.codelabs.core.utils.Resource
import kotlinx.coroutines.flow.Flow
import okhttp3.MultipartBody
import okhttp3.RequestBody

interface PlantDiseaseUseCase {
    @Deprecated("use predictDisease(file: MultipartBody.Part) instead, because the azure service not used anymore")
    fun predictDisease(image: RequestBody): Flow<Resource<List<PlantDiseasePredictionModel>>>

    fun predictDisease(file: MultipartBody.Part): Flow<Resource<PlantDiseaseDetectionModel>>

    fun getDiseasePlant(name: String): Flow<Resource<PlantDiseaseDetectionModel>>
}