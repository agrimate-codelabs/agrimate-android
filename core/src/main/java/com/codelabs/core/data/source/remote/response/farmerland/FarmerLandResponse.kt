package com.codelabs.core.data.source.remote.response.farmerland

import com.google.gson.annotations.SerializedName

data class FarmerLandResponse(

	@field:SerializedName("cropping_pattern")
	val croppingPattern: String? = null,

	@field:SerializedName("address")
	val address: String? = null,

	@field:SerializedName("province")
	val province: String? = null,

	@field:SerializedName("polygon")
	val polygon: List<PolygonItem?>? = null,

	@field:SerializedName("city")
	val city: String? = null,

	@field:SerializedName("district")
	val district: String? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("landArea")
	val landArea: Double? = null,

	@field:SerializedName("photo")
	val photo: String? = null,

	@field:SerializedName("id")
	val id: String? = null,

	@field:SerializedName("village")
	val village: String? = null
)

data class PolygonItem(

	@field:SerializedName("latitude")
	val latitude: Double? = null,

	@field:SerializedName("longitude")
	val longitude: Double? = null
)
