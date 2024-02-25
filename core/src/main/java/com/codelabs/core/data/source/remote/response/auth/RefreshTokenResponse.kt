package com.codelabs.core.data.source.remote.response.auth

import com.google.gson.annotations.SerializedName

data class RefreshTokenResponse(
    @field:SerializedName("accessToken")
    val accessToken: String
)