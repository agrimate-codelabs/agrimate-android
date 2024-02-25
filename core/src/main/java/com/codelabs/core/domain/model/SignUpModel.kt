package com.codelabs.core.domain.model

import com.codelabs.core.data.source.remote.response.ResponseMessage

data class SignUpModel(
    val code: Int,
    val message: ResponseMessage? = null,
)