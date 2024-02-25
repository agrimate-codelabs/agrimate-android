package com.codelabs.core.data.source.remote.response.farmerland

import com.google.gson.annotations.SerializedName

data class FarmerLandListItemResponse(
    @field:SerializedName("address")
    val address: String? = null,

    @field:SerializedName("latitude")
    val latitude: String? = null,

    @field:SerializedName("landArea")
    val landArea: Double? = null,

    @field:SerializedName("photo")
    val photo: String? = null,

    @field:SerializedName("city")
    val city: String? = null,

    @field:SerializedName("province")
    val province: String? = null,

    @field:SerializedName("createdAt")
    val createdAt: String? = null,

    @field:SerializedName("farmerId")
    val farmerId: String? = null,

    @field:SerializedName("deletedAt")
    val deletedAt: Any? = null,

    @field:SerializedName("district")
    val district: String? = null,

    @field:SerializedName("polygon")
    val polygon: List<LandPolygonItem>? = null,

    @field:SerializedName("name")
    val name: String? = null,

    @field:SerializedName("id")
    val id: String? = null,

    @field:SerializedName("village")
    val village: String? = null,

    @field:SerializedName("longitude")
    val longitude: String? = null,

    @field:SerializedName("updatedAt")
    val updatedAt: String? = null
)


data class LandPolygonItem(
    val latitude: String?,
    val longitude: String?
)