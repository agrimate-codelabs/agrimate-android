package com.codelabs.core.domain.usecase

import kotlinx.coroutines.flow.Flow

interface DataStoreUseCase {
    fun getAccessToken(): Flow<String?>
    fun getRefreshToken(): Flow<String?>
    fun getUserId(): Flow<String?>
    fun getUsername(): Flow<String?>
    suspend fun saveAccessToken(accessToken: String)
    suspend fun saveRefreshToken(refreshToken: String)
    suspend fun saveUserId(id: String)
    suspend fun saveUsername(username: String)
    suspend fun deleteAccessToken()
    suspend fun deleteRefreshToken()
    suspend fun deleteUserId()
    suspend fun deleteUsername()
    suspend fun clearTokens()
}