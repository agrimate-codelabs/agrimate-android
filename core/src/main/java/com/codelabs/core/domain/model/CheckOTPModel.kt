package com.codelabs.core.domain.model

import com.codelabs.core.data.source.remote.response.ResponseMessage

data class CheckOTPModel(
    val code: Int,
    val message: ResponseMessage? = null,
    val id: String? = null,
    val userId: String? = null,
    val expires: String? = null,
    val token: String? = null
)
