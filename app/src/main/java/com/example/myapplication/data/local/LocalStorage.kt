package com.example.myapplication.data.local

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.datastore: DataStore<Preferences> by preferencesDataStore("settings")

class LocalStorage(private val context: Context) {

    private val tokenKey = stringPreferencesKey("token_key")
    private val onboardingKey = booleanPreferencesKey("onboarding_key")
    private val userNameKey = stringPreferencesKey("user_name_key")
    val emailKey = stringPreferencesKey("user_email_key")
    val emailFlow: Flow<String> = context.datastore.data.map { preferences ->
        preferences[emailKey] ?: ""
    }
    suspend fun setEmail(email: String) {
        context.datastore.edit { it[emailKey] = email }
    }

    val tokenFlow: Flow<String> = context.datastore.data.map { preferences ->
        preferences[tokenKey] ?: ""
    }

    val userNameFlow: Flow<String> = context.datastore.data.map { preferences ->
        preferences[userNameKey] ?: ""
    }

    val onBoardingShownFlow: Flow<Boolean> = context.datastore.data.map { preferences ->
        preferences[onboardingKey] ?: false
    }

    suspend fun setToken(token: String) {
        context.datastore.edit { preferences ->
            preferences[tokenKey] = token
        }
    }

    suspend fun setUserName(userName: String) {
        context.datastore.edit { preferences ->
            preferences[userNameKey] = userName
        }
    }

    suspend fun clearToken() {
        context.datastore.edit { preferences ->
            preferences.remove(tokenKey)
        }
    }

    suspend fun clearUserName() {
        context.datastore.edit { preferences ->
            preferences.remove(userNameKey)
        }
    }

    suspend fun setOnBoardingShown(isShown: Boolean) {
        context.datastore.edit { preferences ->
            preferences[onboardingKey] = isShown
        }
    }
}
