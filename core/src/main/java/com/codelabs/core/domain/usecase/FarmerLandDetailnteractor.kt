package com.codelabs.core.domain.usecase

import com.codelabs.core.domain.model.FarmerLandModel
import com.codelabs.core.domain.model.PlantNutritionModel
import com.codelabs.core.domain.model.RemainingLandAreaModel
import com.codelabs.core.domain.repository.FarmerLandRepository
import com.codelabs.core.utils.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FarmerLandDetailInteractor @Inject constructor(private val farmerLandRepository: FarmerLandRepository) :
    FarmerLandDetailUseCase {
    override fun getPlantNutrition(id: String): Flow<PlantNutritionModel?> =
        farmerLandRepository.getPlantNutrition(id)

    override fun getRemainingLandArea(id: String): Flow<Resource<RemainingLandAreaModel>> =
        farmerLandRepository.getRemainingLandArea(id)

    override fun getFarmerLand(id: String): Flow<Resource<FarmerLandModel>> =
        farmerLandRepository.getFarmerLand(id)

}