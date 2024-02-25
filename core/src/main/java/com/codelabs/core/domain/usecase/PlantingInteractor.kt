package com.codelabs.core.domain.usecase

import com.codelabs.core.data.source.remote.body.CreatePlantingBody
import com.codelabs.core.domain.model.PlantingListItemModel
import com.codelabs.core.domain.model.PlantingModel
import com.codelabs.core.domain.repository.PlantingRepository
import com.codelabs.core.utils.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PlantingInteractor @Inject constructor(private val plantingRepository: PlantingRepository) :
    PlantingUseCase {
    override fun create(body: CreatePlantingBody): Flow<Resource<PlantingModel>> =
        plantingRepository.create(body)

    override fun getAll(farmlandId: String): Flow<Resource<List<PlantingListItemModel>>> =
        plantingRepository.getAll(farmlandId)

    override fun get(plantingId: String): Flow<Resource<PlantingModel>> =
        plantingRepository.get(plantingId)

}