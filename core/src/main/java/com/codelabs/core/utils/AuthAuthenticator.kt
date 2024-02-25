package com.codelabs.core.utils

import com.codelabs.core.BuildConfig
import com.codelabs.core.data.source.remote.network.AuthService
import com.codelabs.core.data.source.remote.response.auth.RefreshTokenResponse
import com.codelabs.core.domain.repository.DataStoreRepository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import okhttp3.Authenticator
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Inject

class AuthAuthenticator @Inject constructor(
    private val dataStoreRepository: DataStoreRepository,
) : Authenticator {
    override fun authenticate(route: Route?, response: Response): Request? {
        val refreshToken = runBlocking {
            dataStoreRepository.getRefreshToken().first()
        }

        return runBlocking {
            val accessToken = try {
                val newToken = getNewToken(refreshToken)
                newToken.data!!.accessToken
            } catch (e: Exception) {
                dataStoreRepository.deleteAccessToken()
                dataStoreRepository.deleteRefreshToken()
                ""
            }
            dataStoreRepository.saveAccessToken(accessToken)
            if (accessToken.isNotEmpty()) {
                response.request.newBuilder().header("Authorization", "Bearer $accessToken").build()
            } else {
                null
            }
        }
    }

    private suspend fun getNewToken(refreshToken: String?): com.codelabs.core.data.source.remote.response.Response<RefreshTokenResponse> {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        val okHttpClient = OkHttpClient.Builder().addInterceptor(loggingInterceptor).build()

        val retrofit = Retrofit.Builder()
            .baseUrl(BuildConfig.API_AGRIMATE_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
        val service = retrofit.create(AuthService::class.java)
        return service.refreshToken("AGRIMATE_RT=$refreshToken")
    }
}