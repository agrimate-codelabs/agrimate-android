package com.codelabs.core.domain.usecase

import com.codelabs.core.data.source.remote.body.CreateFarmerLandBody
import com.codelabs.core.domain.model.CreateFarmerLandModel
import com.codelabs.core.domain.model.FarmerLandListItemModel
import com.codelabs.core.domain.model.FarmerLandModel
import com.codelabs.core.domain.model.UploadImageModel
import com.codelabs.core.domain.repository.FarmerLandRepository
import com.codelabs.core.utils.Resource
import kotlinx.coroutines.flow.Flow
import okhttp3.MultipartBody
import javax.inject.Inject

class FarmerLandInteractor @Inject constructor(private val farmerLandRepository: FarmerLandRepository) :
    FarmerLandUseCase {
    override fun createFarmerLand(body: CreateFarmerLandBody): Flow<Resource<CreateFarmerLandModel>> =
        farmerLandRepository.createFarmerLand(body)

    override fun updateFarmerLand(
        id: String,
        body: CreateFarmerLandBody
    ): Flow<Resource<CreateFarmerLandModel>> =
        farmerLandRepository.updateFarmerLand(id, body)

    override fun getFarmerLand(): Flow<Resource<List<FarmerLandListItemModel>>> =
        farmerLandRepository.getFarmerLand()

    override fun getFarmerLand(landId: String): Flow<Resource<FarmerLandModel>> =
        farmerLandRepository.getFarmerLand(landId)

    override fun uploadImage(image: MultipartBody.Part): Flow<Resource<UploadImageModel>> =
        farmerLandRepository.uploadImage(image)

    override fun deleteLand(id: String): Flow<Resource<String>> =
        farmerLandRepository.deleteLand(id)

}