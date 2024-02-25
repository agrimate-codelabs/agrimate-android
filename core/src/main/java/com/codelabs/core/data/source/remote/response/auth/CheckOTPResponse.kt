package com.codelabs.core.data.source.remote.response.auth

import com.google.gson.annotations.SerializedName

data class CheckOTPResponse(
    @field:SerializedName("id")
    val id: String,

    @field:SerializedName("userId")
    val userId: String,

    @field:SerializedName("expires")
    val expires: String,

    @field:SerializedName("token")
    val token: String
)