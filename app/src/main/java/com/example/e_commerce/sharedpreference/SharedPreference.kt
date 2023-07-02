package com.example.e_commerce.sharedpreference

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class SharedPreference(private val context: Context) {

    //to make sure there is only one instance
    companion object {
        private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "token")
        val TOKEN = stringPreferencesKey("token")
        val USERNAME = stringPreferencesKey("userName")
    }

    val getToken: Flow<String?> = context.dataStore.data
        .map { Preferences ->
            Preferences[TOKEN] ?: ""
        }
    val getUserName: Flow<String?> = context.dataStore.data
        .map { Preferences ->
            Preferences[USERNAME] ?: ""
        }

    suspend fun saveToken(token: String) {
        context.dataStore.edit { Preferences ->
            Preferences[TOKEN] = token
        }
    }
    suspend fun saveUserName(token: String) {
        context.dataStore.edit { Preferences ->
            Preferences[USERNAME] = token
        }
    }

    suspend fun removeToken() {
        context.dataStore.edit { preferences ->
            preferences.clear()
        }
    }

}
