package com.demoapp.passwordmanager.core.session

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SessionManagement @Inject constructor(private val context: Context) {

    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "session")
    private val SESSION_KEY = intPreferencesKey("session_key")

    suspend fun editSession(userId: Int) {
        context.dataStore.edit { preference ->
            preference[SESSION_KEY] = userId
        }
    }

    fun getSession(): Flow<Int> {
        return context.dataStore.data.map { preference ->
            preference[SESSION_KEY] ?: -1
        }
    }

}