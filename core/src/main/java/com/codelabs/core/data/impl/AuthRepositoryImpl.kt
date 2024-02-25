package com.codelabs.core.data.impl

import com.codelabs.core.data.source.remote.AuthRemoteDataSource
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
import com.codelabs.core.mapper.AuthMeMapper
import com.codelabs.core.mapper.CheckOTPMapper
import com.codelabs.core.mapper.ForgotPasswordMapper
import com.codelabs.core.mapper.SignInMapper
import com.codelabs.core.mapper.SignUpMapper
import com.codelabs.core.utils.Resource
import com.codelabs.core.utils.apiRequestFlow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val authRemoteDataSource: AuthRemoteDataSource,
    private val signUpMapper: SignUpMapper,
    private val signInMapper: SignInMapper,
    private val forgotPasswordMapper: ForgotPasswordMapper,
    private val authMeMapper: AuthMeMapper,
    private val checkOTPMapper: CheckOTPMapper
) :
    AuthRepository {
    override suspend fun signUp(body: SignUpBody): Flow<Resource<SignUpModel>> =
        apiRequestFlow({ authRemoteDataSource.signUp(body) }, signUpMapper)

    override suspend fun signIn(body: SignInBody): Flow<Resource<SignInModel>> =
        apiRequestFlow({ authRemoteDataSource.signIn(body) }, signInMapper)

    override suspend fun forgotPassword(body: ForgotPasswordBody): Flow<Resource<ForgotPasswordModel>> =
        apiRequestFlow({ authRemoteDataSource.forgotPassword(body) }, forgotPasswordMapper)

    override suspend fun resetPassword(body: ResetPasswordBody): Flow<Resource<ResetPasswordModel>> = flow {
        emit(Resource.Loading())
        try {
            val response = authRemoteDataSource.resetPassword(body)
            emit(Resource.Success(ResetPasswordModel(response.code, "Berhasil Mengganti Password")))
        } catch (e: HttpException) {
            if (e.code() >= 400) {
                emit(Resource.Error("Gagal Mengganti Password", ResetPasswordModel(e.code(), e.message())))
            } else {
                emit(Resource.Error(message = e.message()))
            }
        } catch (e: Exception) {
            emit(Resource.Error(message = e.message.toString()))
        }
    }

    override suspend fun checkOTP(body: CheckOTPBody): Flow<Resource<CheckOTPModel>> =
        apiRequestFlow({ authRemoteDataSource.checkOTP(body) }, checkOTPMapper)

    override suspend fun authMe(): Flow<Resource<AuthMeModel>> =
        apiRequestFlow({ authRemoteDataSource.authMe() }, authMeMapper)

    override suspend fun logout(): Flow<Resource<LogoutModel>> = flow {
        emit(Resource.Loading())
        try {
            val response = authRemoteDataSource.logout()
            emit(Resource.Success(LogoutModel(response.code, "Berhasil Logout")))
        } catch (e: HttpException) {
            if (e.code() >= 400) {
                emit(Resource.Error("Error", LogoutModel(e.code(), e.message())))
            } else {
                emit(Resource.Error(message = e.message()))
            }
        } catch (e: Exception) {
            emit(Resource.Error(message = e.message.toString()))
        }
    }

}