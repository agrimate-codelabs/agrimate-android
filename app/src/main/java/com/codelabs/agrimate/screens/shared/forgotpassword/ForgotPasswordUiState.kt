package com.codelabs.agrimate.screens.shared.forgotpassword

import com.codelabs.agrimate.common.FormHandler

data class ForgotPasswordUiState(
    val email: String = "",
    val isLoading: Boolean = false,
    val inputEmail: FormHandler = FormHandler(true, ""),
    val isCodeSent: Boolean = false
)