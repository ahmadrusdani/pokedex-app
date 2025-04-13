package com.amd.pokedexapp.data.local.datasource

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.IOException
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.amd.pokedexapp.domain.model.user.UserModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DataPreference @Inject constructor(
    @ApplicationContext private val context: Context
) {

    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "user_preferences")

    val isLoggedIn: Flow<Boolean> = context.dataStore.data
        .catch { exception ->
            if (exception is IOException) {
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }
        .map { preferences ->
            preferences[PreferencesKeys.IS_LOGGED_IN] ?: false
        }

    suspend fun saveLoginState(isLoggedIn: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[PreferencesKeys.IS_LOGGED_IN] = isLoggedIn
        }
    }

    suspend fun saveUserInfo(username: String, email: String) {
        context.dataStore.edit { preferences ->
            preferences[PreferencesKeys.USERNAME] = username
            preferences[PreferencesKeys.EMAIL] = email
            // Save other user info here
        }
    }

    fun getUserInfo(): Flow<UserModel> = context.dataStore.data
        .catch { exception ->
            if (exception is IOException) {
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }
        .map { preferences ->
            val username = preferences[PreferencesKeys.USERNAME] ?: ""
            val email = preferences[PreferencesKeys.EMAIL] ?: ""
            UserModel(username, email)
            // Retrieve other user info here
        }
}

object PreferencesKeys {
    val IS_LOGGED_IN = booleanPreferencesKey("is_logged_in")
    val USERNAME = stringPreferencesKey("username")
    val EMAIL = stringPreferencesKey("email")
    // Add other user info keys as needed
}