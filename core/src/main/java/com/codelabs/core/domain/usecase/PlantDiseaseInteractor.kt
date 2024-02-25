package com.codelabs.core.domain.usecase

import com.codelabs.core.domain.model.PlantDiseaseDetectionModel
import com.codelabs.core.domain.repository.PlantDiseaseRepository
import com.codelabs.core.utils.Resource
import kotlinx.coroutines.flow.Flow
import okhttp3.MultipartBody
import okhttp3.RequestBody
import javax.inject.Inject

class PlantDiseaseInteractor @Inject constructor(private val plantDiseaseRepository: PlantDiseaseRepository) :
    PlantDiseaseUseCase {
    @Deprecated("use predictDisease(file: Multipart.Body) instead")
    override fun predictDisease(image: RequestBody) =
        plantDiseaseRepository.predictPlantDisease(image)

    override fun predictDisease(file: MultipartBody.Part): Flow<Resource<PlantDiseaseDetectionModel>> =
        plantDiseaseRepository.predictPlantDisease(file)

    override fun getDiseasePlant(name: String): Flow<Resource<PlantDiseaseDetectionModel>> =
        plantDiseaseRepository.getDiseasePlant(name)

}