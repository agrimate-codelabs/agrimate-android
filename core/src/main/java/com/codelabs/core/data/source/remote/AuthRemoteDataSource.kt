package com.codelabs.core.data.source.remote

import com.codelabs.core.data.source.remote.body.CheckOTPBody
import com.codelabs.core.data.source.remote.body.ForgotPasswordBody
import com.codelabs.core.data.source.remote.body.ResetPasswordBody
import com.codelabs.core.data.source.remote.body.SignInBody
import com.codelabs.core.data.source.remote.body.SignUpBody
import com.codelabs.core.data.source.remote.network.AuthService
import com.codelabs.core.data.source.remote.response.Response
import com.codelabs.core.data.source.remote.response.auth.SignInResponse
import com.codelabs.core.data.source.remote.response.auth.SignUpResponse
import javax.inject.Inject

class AuthRemoteDataSource @Inject constructor(
    private val authService: AuthService,
) {
    suspend fun signUp(body: SignUpBody): Response<SignUpResponse> = authService.signUp(body)
    suspend fun signIn(body: SignInBody): Response<SignInResponse> = authService.signIn(body)
    suspend fun forgotPassword(body: ForgotPasswordBody) = authService.forgotPassword(body)
    suspend fun resetPassword(body: ResetPasswordBody) = authService.resetPassword(body)
    suspend fun refreshToken(token: String) = authService.refreshToken(token)
    suspend fun checkOTP(body: CheckOTPBody) = authService.checkOTP(body)
    suspend fun authMe() = authService.authMe()
    suspend fun logout() = authService.logout()
}