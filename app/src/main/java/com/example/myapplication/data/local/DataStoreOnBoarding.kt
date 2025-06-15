package com.example.myapplication.data.local

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class DataStoreOnBoarding(private val context: Context){
    private val onBoardingKey = booleanPreferencesKey("onboarding")
    private val localStorage = context.datastore
    private val userNameKey = stringPreferencesKey("userName")

    suspend fun setOnBoardingCompleted(completed: Boolean){
        localStorage.edit { settings ->
            settings[onBoardingKey] = completed
        }
    }

    val onBoardingCompleted: Flow<Boolean> = localStorage.data
        .map { preferences -> preferences[onBoardingKey] ?: false }

    suspend fun saveUserName(name: String) {
        localStorage.edit { preferences ->
            preferences[userNameKey] = name
        }
    }

    val getUserName: Flow<String?> = localStorage.data
        .map { preferences -> preferences[userNameKey] }

    suspend fun clearAuthState() {
        localStorage.edit { preferences ->
            preferences.clear()
        }
    }

}