package com.codelabs.core.data.impl

import com.codelabs.core.data.source.local.LocalDataStore
import com.codelabs.core.domain.repository.DataStoreRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DataStoreRepositoryImpl @Inject constructor(private val localDataStore: LocalDataStore) :
    DataStoreRepository {
    override fun getAccessToken(): Flow<String?> = localDataStore.accessToken
    override fun getRefreshToken(): Flow<String?> = localDataStore.refreshToken
    override fun getUserId(): Flow<String?> = localDataStore.userId
    override fun getUsername(): Flow<String?> = localDataStore.username

    override suspend fun saveAccessToken(accessToken: String) =
        localDataStore.saveAccessToken(accessToken)
    override suspend fun saveRefreshToken(refreshToken: String) =
        localDataStore.saveRefreshToken(refreshToken)
    override suspend fun deleteAccessToken() = localDataStore.deleteAccessToken()
    override suspend fun deleteRefreshToken() = localDataStore.deleteRefreshToken()
    override suspend fun saveUserId(id: String) = localDataStore.saveUserId(id)
    override suspend fun deleteUserId() = localDataStore.deleteUserId()
    override suspend fun saveUsername(username: String)= localDataStore.saveUsername(username)

    override suspend fun deleteUsername() = localDataStore.deleteUsername()

    override suspend fun clearTokens() = localDataStore.clearTokens()
}