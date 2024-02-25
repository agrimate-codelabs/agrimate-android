package com.codelabs.core.domain.model

import com.codelabs.core.data.source.remote.response.farmerland.PolygonItem

data class FarmerLandModel(
    val id: String?,
    val name: String?,
    val province: String?,
    val provinceCode: String?,
    val city: String?,
    val cityCode: String?,
    val district: String?,
    val districtCode: String?,
    val village: String?,
    val villageCode: String?,
    val address: String?,
    val landArea: Double?,
    val polygon: List<PolygonItem?>,
    val photo: String?,
)