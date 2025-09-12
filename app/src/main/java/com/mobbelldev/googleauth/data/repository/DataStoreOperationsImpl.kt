package com.mobbelldev.googleauth.data.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import com.mobbelldev.googleauth.domain.repository.DataStoreOperations
import com.mobbelldev.googleauth.util.Constant.PREFERENCES_SIGNED_IN_KEY
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import okio.IOException
import javax.inject.Inject

class DataStoreOperationsImpl @Inject constructor(
    private val dataStore: DataStore<Preferences>,
) : DataStoreOperations {
    override suspend fun saveSignedInState(signedIn: Boolean) {
        dataStore.edit { preference ->
            preference[PreferencesKey.signedInKey] = signedIn
        }
    }

    override fun readSignedInState(): Flow<Boolean> {
        return dataStore.data
            .catch { exception ->
                if (exception is IOException) {
                    emit(value = emptyPreferences())
                } else {
                    throw exception
                }
            }
            .map { preferences ->
                preferences[PreferencesKey.signedInKey] ?: false
            }
    }

    private object PreferencesKey {
        val signedInKey = booleanPreferencesKey(name = PREFERENCES_SIGNED_IN_KEY)
    }
}