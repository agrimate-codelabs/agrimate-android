package com.codelabs.core.domain.repository

import com.codelabs.core.domain.model.PlantDiseaseDetectionModel
import com.codelabs.core.domain.model.PlantDiseasePredictionModel
import com.codelabs.core.utils.Resource
import kotlinx.coroutines.flow.Flow
import okhttp3.MultipartBody
import okhttp3.RequestBody

interface PlantDiseaseRepository {
    fun predictPlantDisease(image: RequestBody): Flow<Resource<List<PlantDiseasePredictionModel>>>

    fun predictPlantDisease(file: MultipartBody.Part): Flow<Resource<PlantDiseaseDetectionModel>>

    fun getDiseasePlant(name: String): Flow<Resource<PlantDiseaseDetectionModel>>
}