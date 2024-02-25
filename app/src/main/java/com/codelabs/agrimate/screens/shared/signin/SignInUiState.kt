package com.codelabs.agrimate.screens.shared.signin

import com.codelabs.agrimate.common.FormHandler

data class SignInUiState(
    val loginId: String = "",
    val password: String = "",
    val inputEmail: FormHandler = FormHandler(true, ""),
    val inputPassword: FormHandler = FormHandler(true, ""),
    val isLoading: Boolean = false,
    val isSuccess: Boolean = false
)