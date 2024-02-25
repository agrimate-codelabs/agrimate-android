package com.codelabs.agrimate.screens.shared.resetpassword

data class ResetPasswordUiState(
    val password: String = "",
    val confirmPassword: String = "",
    val isLoading: Boolean = false,
    val isSuccess: Boolean = false
)
