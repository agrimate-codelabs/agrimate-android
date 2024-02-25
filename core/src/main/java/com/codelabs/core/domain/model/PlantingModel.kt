package com.codelabs.core.domain.model

import com.codelabs.core.data.source.remote.response.ResponseMessage

data class PlantingModel(
    val id: String = "",
    val farmLandId: String = "",
    val commodityId: String = "",
    val commodity: String = "",
    val unit: String = "",
    val plantingSize: Double = 0.0,
    val plantingQuantity: Double = 0.0,
    val productionCost: Long = 0,
    val plantingType: String = "",
    val longitude: String = "",
    val latitude: String = "",
    val date: String = "",
    val status: String = "",
    val createdAt: String = "",
    val updatedAt: String = "",
    val harvesting: HarvestModel? = null,
    val code: Int = 0,
    val message: ResponseMessage? = null,
)
