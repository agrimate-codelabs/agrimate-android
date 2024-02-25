package com.codelabs.core.data.source.local

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class LocalDataStore @Inject constructor(private val context: Context) {

    val accessToken: Flow<String?> =
        context.dataStore.data.map { pref ->
            pref[ACCESS_TOKEN]
        }

    val refreshToken: Flow<String?> =
        context.dataStore.data.map { pref ->
            pref[REFRESH_TOKEN]
        }

    val userId: Flow<String?> =
        context.dataStore.data.map { pref ->
            pref[USER_ID]
        }

    val username: Flow<String?> =
        context.dataStore.data.map { pref ->
            pref[USERNAME]
        }

    suspend fun saveAccessToken(accessToken: String) {
        context.dataStore.edit { pref ->
            pref[ACCESS_TOKEN] = accessToken
        }
    }

    suspend fun saveRefreshToken(refreshToken: String) {
        context.dataStore.edit { pref ->
            pref[REFRESH_TOKEN] = refreshToken
        }
    }

    suspend fun deleteAccessToken() {
        context.dataStore.edit { pref ->
            pref.remove(ACCESS_TOKEN)
        }
    }

    suspend fun deleteRefreshToken() {
        context.dataStore.edit { pref ->
            pref.remove(REFRESH_TOKEN)
        }
    }

    suspend fun saveUserId(id: String) {
        context.dataStore.edit { pref ->
            pref[USER_ID] = id
        }
    }

    suspend fun deleteUserId() {
        context.dataStore.edit { pref ->
            pref.remove(USER_ID)
        }
    }

    suspend fun saveUsername(username: String) {
        context.dataStore.edit { pref ->
            pref[USERNAME] = username
        }
    }

    suspend fun deleteUsername() {
        context.dataStore.edit { pref ->
            pref.remove(USERNAME)
        }
    }

    suspend fun clearTokens() {
        context.dataStore.edit { pref ->
            pref.remove(ACCESS_TOKEN)
            pref.remove(REFRESH_TOKEN)
            pref.remove(USER_ID)
            pref.remove(USERNAME)
        }
    }

    companion object {
        private val Context.dataStore: DataStore<Preferences> by preferencesDataStore("agrimate_pref")
        val ACCESS_TOKEN = stringPreferencesKey("accessToken")
        val REFRESH_TOKEN = stringPreferencesKey("refreshToken")
        val USER_ID = stringPreferencesKey("USER_ID")
        val USERNAME = stringPreferencesKey("USERNAME")
    }
}