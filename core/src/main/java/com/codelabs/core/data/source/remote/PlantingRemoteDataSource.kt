package com.codelabs.core.data.source.remote

import com.codelabs.core.data.source.remote.body.CreatePlantingBody
import com.codelabs.core.data.source.remote.network.PlantingService
import javax.inject.Inject

class PlantingRemoteDataSource @Inject constructor(private val plantingService: PlantingService) {
    suspend fun create(body: CreatePlantingBody) = plantingService.create(body)
    suspend fun getAll(farmlandId: String) = plantingService.getAll(farmlandId)
    suspend fun get(plantingId: String) = plantingService.get(plantingId)
}