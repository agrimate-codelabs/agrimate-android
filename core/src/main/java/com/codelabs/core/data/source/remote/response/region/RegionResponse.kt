package com.codelabs.core.data.source.remote.response.region

import com.google.gson.annotations.SerializedName

data class RegionResponse(
    @field:SerializedName("code")
    val code: String,

    @field:SerializedName("name")
    val name: String
)