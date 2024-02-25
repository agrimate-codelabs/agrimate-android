package com.codelabs.core.data.source.remote.body

data class ResetPasswordBody(
    val userId: String,
    val password: String,
    val confirmPassword: String
)