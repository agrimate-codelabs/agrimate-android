package com.codelabs.core.domain.model

import com.codelabs.core.data.source.remote.response.ResponseMessage

data class SignInModel(
    val code: Int,
    val message: ResponseMessage? = null,
    val accessToken: String? = null,
    val refreshToken: String? = null
)