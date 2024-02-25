package com.codelabs.core.data.source.remote.response.auth

import com.google.gson.annotations.SerializedName

data class SignUpResponse(
    @field:SerializedName("createdAt")
    val createdAt: String? = null,

    @field:SerializedName("password")
    val password: String? = null,

    @field:SerializedName("roles")
    val roles: String? = null,

    @field:SerializedName("id")
    val id: String? = null,

    @field:SerializedName("email")
    val email: String? = null,

    @field:SerializedName("refreshToken")
    val refreshToken: String? = null,

    @field:SerializedName("updatedAt")
    val updatedAt: String? = null
)
