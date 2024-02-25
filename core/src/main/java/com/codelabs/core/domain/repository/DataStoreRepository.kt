package com.codelabs.core.domain.repository

import kotlinx.coroutines.flow.Flow

interface DataStoreRepository {
    fun getAccessToken(): Flow<String?>
    fun getRefreshToken(): Flow<String?>
    fun getUserId(): Flow<String?>
    fun getUsername(): Flow<String?>
    suspend fun saveAccessToken(accessToken: String)
    suspend fun saveRefreshToken(refreshToken: String)
    suspend fun deleteAccessToken()
    suspend fun deleteRefreshToken()
    suspend fun saveUserId(id: String)
    suspend fun deleteUserId()
    suspend fun saveUsername(username: String)
    suspend fun deleteUsername()
    suspend fun clearTokens()
}