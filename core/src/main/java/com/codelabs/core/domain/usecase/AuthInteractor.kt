package com.codelabs.core.domain.usecase

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
import com.codelabs.core.domain.repository.AuthRepository
import com.codelabs.core.utils.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AuthInteractor @Inject constructor(private val authRepository: AuthRepository) : AuthUseCase {
    override suspend fun signUp(body: SignUpBody): Flow<Resource<SignUpModel>> =
        authRepository.signUp(body)

    override suspend fun signIn(body: SignInBody): Flow<Resource<SignInModel>> =
        authRepository.signIn(body)

    override suspend fun forgotPassword(body: ForgotPasswordBody): Flow<Resource<ForgotPasswordModel>> =
        authRepository.forgotPassword(body)

    override suspend fun resetPassword(body: ResetPasswordBody): Flow<Resource<ResetPasswordModel>> =
        authRepository.resetPassword(body)

    override suspend fun checkOTP(body: CheckOTPBody): Flow<Resource<CheckOTPModel>> =
        authRepository.checkOTP(body)

    override suspend fun authMe(): Flow<Resource<AuthMeModel>> = authRepository.authMe()
    override suspend fun logout(): Flow<Resource<LogoutModel>> = authRepository.logout()
}