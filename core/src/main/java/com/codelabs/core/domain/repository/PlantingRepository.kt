package com.codelabs.core.domain.repository

import com.codelabs.core.data.source.remote.body.CreatePlantingBody
import com.codelabs.core.domain.model.PlantingListItemModel
import com.codelabs.core.domain.model.PlantingModel
import com.codelabs.core.utils.Resource
import kotlinx.coroutines.flow.Flow

interface PlantingRepository {
    fun create(body: CreatePlantingBody): Flow<Resource<PlantingModel>>
    fun getAll(farmlandId: String): Flow<Resource<List<PlantingListItemModel>>>
    fun get(plantingId: String): Flow<Resource<PlantingModel>>
}