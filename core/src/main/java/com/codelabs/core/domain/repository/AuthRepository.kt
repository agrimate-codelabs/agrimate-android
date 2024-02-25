package com.codelabs.core.domain.repository

import com.codelabs.core.data.source.remote.body.CheckOTPBody
import com.codelabs.core.data.source.remote.body.ForgotPasswordBody
import com.codelabs.core.data.source.remote.body.ResetPasswordBody
import com.codelabs.core.data.source.remote.body.SignInBody
import com.codelabs.core.data.source.remote.body.SignUpBody
import com.codelabs.core.domain.model.AuthMeModel
import com.codelabs.core.domain.model.CheckOTPModel
import com.codelabs.core.domain.model.ForgotPasswordModel
import com.codelabs.core.domain.model.LogoutModel
import com.codelabs.core.domain.model.ResetPasswordModel
import com.codelabs.core.domain.model.SignInModel
import com.codelabs.core.domain.model.SignUpModel
import com.codelabs.core.utils.Resource
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    suspend fun signUp(body: SignUpBody): Flow<Resource<SignUpModel>>
    suspend fun signIn(body: SignInBody): Flow<Resource<SignInModel>>
    suspend fun forgotPassword(body: ForgotPasswordBody): Flow<Resource<ForgotPasswordModel>>
    suspend fun resetPassword(body: ResetPasswordBody): Flow<Resource<ResetPasswordModel>>
    suspend fun checkOTP(body: CheckOTPBody): Flow<Resource<CheckOTPModel>>
    suspend fun authMe(): Flow<Resource<AuthMeModel>>
    suspend fun logout(): Flow<Resource<LogoutModel>>
}