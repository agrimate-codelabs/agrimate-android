package com.codelabs.core.data.source.remote.response.farmerland

import com.google.gson.annotations.SerializedName

data class RemainingLandAreaResponse(

    @field:SerializedName("usedArea")
    val usedArea: Double? = null,

    @field:SerializedName("totalArea")
    val totalArea: Double? = null
)
