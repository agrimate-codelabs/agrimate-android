package com.codelabs.core.domain.repository

import com.codelabs.core.data.source.remote.body.CreateFarmerLandBody
import com.codelabs.core.domain.model.CreateFarmerLandModel
import com.codelabs.core.domain.model.FarmerLandListItemModel
import com.codelabs.core.domain.model.FarmerLandModel
import com.codelabs.core.domain.model.PlantNutritionModel
import com.codelabs.core.domain.model.RemainingLandAreaModel
import com.codelabs.core.domain.model.UploadImageModel
import com.codelabs.core.utils.Resource
import kotlinx.coroutines.flow.Flow
import okhttp3.MultipartBody

interface FarmerLandRepository {
    fun createFarmerLand(body: CreateFarmerLandBody): Flow<Resource<CreateFarmerLandModel>>
    fun updateFarmerLand(
        id: String,
        body: CreateFarmerLandBody
    ): Flow<Resource<CreateFarmerLandModel>>

    fun getFarmerLand(): Flow<Resource<List<FarmerLandListItemModel>>>
    fun getFarmerLand(id: String): Flow<Resource<FarmerLandModel>>
    fun uploadImage(image: MultipartBody.Part): Flow<Resource<UploadImageModel>>
    fun deleteLand(id: String): Flow<Resource<String>>
    fun getPlantNutrition(id: String): Flow<PlantNutritionModel?>
    fun getRemainingLandArea(id: String): Flow<Resource<RemainingLandAreaModel>>
}