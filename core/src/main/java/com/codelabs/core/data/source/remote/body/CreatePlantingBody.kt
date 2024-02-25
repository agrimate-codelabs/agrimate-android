package com.codelabs.core.data.source.remote.body

data class CreatePlantingBody(
    val farmlandId: String,
    val commodityId: String,
    val planting_type: String,
    val unit: String,
    val planting_size: Double,
    val planting_quantity: Double,
    val production_cost: Long,
    val latitude: String,
    val longitude: String,
    val date: String
)