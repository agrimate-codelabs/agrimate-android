package com.codelabs.core.domain.usecase

import com.codelabs.core.domain.model.FarmerLandModel
import com.codelabs.core.domain.model.PlantNutritionModel
import com.codelabs.core.domain.model.RemainingLandAreaModel
import com.codelabs.core.utils.Resource
import kotlinx.coroutines.flow.Flow

interface FarmerLandDetailUseCase {
    fun getPlantNutrition(id: String): Flow<PlantNutritionModel?>
    fun getRemainingLandArea(id: String): Flow<Resource<RemainingLandAreaModel>>
    fun getFarmerLand(id: String): Flow<Resource<FarmerLandModel>>
}