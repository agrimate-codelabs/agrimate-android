package com.codelabs.core.data.source.remote.network

import com.codelabs.core.data.source.remote.body.CheckOTPBody
import com.codelabs.core.data.source.remote.body.ForgotPasswordBody
import com.codelabs.core.data.source.remote.body.ResetPasswordBody
import com.codelabs.core.data.source.remote.body.SignInBody
import com.codelabs.core.data.source.remote.body.SignUpBody
import com.codelabs.core.data.source.remote.response.Response
import com.codelabs.core.data.source.remote.response.auth.AuthMeResponse
import com.codelabs.core.data.source.remote.response.auth.CheckOTPResponse
import com.codelabs.core.data.source.remote.response.auth.RefreshTokenResponse
import com.codelabs.core.data.source.remote.response.auth.SignInResponse
import com.codelabs.core.data.source.remote.response.auth.SignUpResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface AuthService {
    @POST("auth/signup")
    suspend fun signUp(@Body body: SignUpBody): Response<SignUpResponse>

    @POST("auth/signin")
    suspend fun signIn(@Body body: SignInBody): Response<SignInResponse>

    @POST("auth/forgot-password")
    suspend fun forgotPassword(@Body body: ForgotPasswordBody): Response<Nothing>

    @POST("auth/reset-password")
    suspend fun resetPassword(@Body body: ResetPasswordBody): Response<Nothing>

    @POST("auth/refresh-token")
    suspend fun refreshToken(@Header("Cookie") refreshToken: String): Response<RefreshTokenResponse>


    @POST("auth/check-otp")
    suspend fun checkOTP(@Body body: CheckOTPBody): Response<CheckOTPResponse>

    @GET("auth/current-user")
    suspend fun authMe(): Response<AuthMeResponse>

    @POST("auth/logout")
    suspend fun logout(): Response<Nothing>
}