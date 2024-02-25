package com.codelabs.core.domain.model

data class ResetPasswordModel(
    val code: Int,
    val message: String
)