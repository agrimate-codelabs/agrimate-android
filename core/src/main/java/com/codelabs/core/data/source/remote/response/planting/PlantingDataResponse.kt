package com.codelabs.core.data.source.remote.response.planting

import com.codelabs.core.data.source.remote.response.harvest.HarvestResponse
import com.google.gson.annotations.SerializedName

data class PlantingDataResponse(
    @SerializedName("id")
    val id: String? = null,

    @SerializedName("farmlandId")
    val farmlandId: String? = null,

    @SerializedName("commodityId")
    val commodityId: String? = null,

    @SerializedName("unit")
    val unit: String? = null,

    @SerializedName("planting_size")
    val plantingSize: Double? = null,

    @SerializedName("planting_quantity")
    val plantingQuantity: Double? = null,

    @SerializedName("production_cost")

    val productionCost: Long? = null,
    @SerializedName("planting_type")
    val plantingType: String? = null,

    @SerializedName("longitude")
    val longitude: String? = null,

    @SerializedName("latitude")
    val latitude: String? = null,

    @SerializedName("date")
    val date: String? = null,

    @SerializedName("status")
    val status: String? = null,

    @SerializedName("createdAt")
    val createdAt: String? = null,

    @SerializedName("updatedAt")
    val updatedAt: String? = null,

    @SerializedName("harvesting")
    val harvesting: List<HarvestResponse> = emptyList()
)
