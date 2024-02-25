package com.codelabs.core.data.source.remote

import com.codelabs.core.data.source.remote.body.CreateFarmerLandBody
import com.codelabs.core.data.source.remote.network.FarmerLandService
import okhttp3.MultipartBody
import javax.inject.Inject

class FarmerLandRemoteDataSource @Inject constructor(private val farmerLandService: FarmerLandService) {
    suspend fun createFarmerLand(body: CreateFarmerLandBody) = farmerLandService.create(body)
    suspend fun update(id: String, body: CreateFarmerLandBody) = farmerLandService.update(id, body)
    suspend fun getFarmerLand() = farmerLandService.get()
    suspend fun getFarmerLand(id: String) = farmerLandService.get(id)
    suspend fun uploadImage(image: MultipartBody.Part) = farmerLandService.uploadImage(image)
    suspend fun deleteLand(id: String) = farmerLandService.deleteLand(id)
    suspend fun getRemainingLandArea(id: String) = farmerLandService.getRemainingLandArea(id)
}