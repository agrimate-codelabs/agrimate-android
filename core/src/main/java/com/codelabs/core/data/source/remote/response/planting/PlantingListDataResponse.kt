package com.codelabs.core.data.source.remote.response.planting

data class PlantingListDataResponse(
    val pagination: Pagination? = null,
    val plantings: List<PlantingsItem?>? = null
)

data class Pagination(
    val total: Int? = null,
    val totalPage: Int? = null,
    val limit: Int? = null,
    val page: Int? = null
)

data class PlantingsItem(
    val id: String? = null,
    val plantingDate: String? = null,
    val harvestingDate: String? = null,
    val plantingSize: Double? = null,
    val plantingQuantity: Double? = null,
    val commodity: String? = null,
    val lastActivity: String? = null,
    val status: String? = null
)

