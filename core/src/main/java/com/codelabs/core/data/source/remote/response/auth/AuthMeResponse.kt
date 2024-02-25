package com.codelabs.core.data.source.remote.response.auth

import com.google.gson.annotations.SerializedName

data class AuthMeResponse(

    @field:SerializedName("role")
    val role: String,

    @field:SerializedName("userDetail")
    val userDetail: UserDetailResponse,

    @field:SerializedName("id")
    val id: String,

    @field:SerializedName("email")
    val email: String
)

data class UserDetailResponse(
    @field:SerializedName("name")
    val name: String
)
