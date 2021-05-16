package com.emrhmrc.mvvmcore.helper

import android.content.Context
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Inject

const val USER_PREFERENCES_NAME = "data_store_preferences"

//extension for data store
private val Context.myDataStore by preferencesDataStore(
    name = USER_PREFERENCES_NAME
)

class DataStoreHelper @Inject constructor(@ApplicationContext private val context: Context) {

    suspend fun clearDataStore() {
        context.myDataStore.edit {
            it.clear()
        }
    }

    //region String Read/Write
    /**
     * Reading string
     */
    fun readString(
        key: Preferences.Key<String>,
        defaultValue: String = "no-value"
    ): Flow<String> {
        return context.myDataStore.data
            .catch { ex ->
                if (ex is IOException) {
                    emit(emptyPreferences())
                } else throw ex
            }
            .map { preferences ->
                val showCompleted = preferences[key] ?: defaultValue
                showCompleted
            }
    }

    /**
     * Save string
     */
    suspend fun saveString(key: Preferences.Key<String>, name: String) {
        context.myDataStore.edit { preferences ->
            preferences[key] = name
        }
    }

    //endregion

    //region Boolean Read/Write
    /**
     * Reading Boolean
     */
    fun readBoolean(
        key: Preferences.Key<Boolean>,
        defaultValue: Boolean = false
    ): Flow<Boolean> {
        return context.myDataStore.data
            .catch { ex ->
                if (ex is IOException) {
                    emit(emptyPreferences())
                } else throw ex
            }
            .map { preferences ->
                val showCompleted = preferences[key] ?: defaultValue
                showCompleted
            }
    }

    /**
     * Save Boolean
     */
    suspend fun saveBoolean(key: Preferences.Key<Boolean>, name: Boolean) {
        context.myDataStore.edit { preferences ->
            preferences[key] = name
        }
    }

    //endregion

    //region Int Read/Write
    /**
     * Reading Int
     */
    fun readInt(
        key: Preferences.Key<Int>,
        defaultValue: Int = -1
    ): Flow<Int> {
        return context.myDataStore.data
            .catch { ex ->
                if (ex is IOException) {
                    emit(emptyPreferences())
                } else throw ex
            }
            .map { preferences ->
                val showCompleted = preferences[key] ?: defaultValue
                showCompleted
            }
    }

    /**
     * Save Int
     */
    suspend fun saveInt(key: Preferences.Key<Int>, name: Int) {
        context.myDataStore.edit { preferences ->
            preferences[key] = name
        }
    }

    //endregion

    //region Float Read/Write
    /**
     * Reading Float
     */
    fun readFloat(
        key: Preferences.Key<Float>,
        defaultValue: Float = 0f
    ): Flow<Float> {
        return context.myDataStore.data
            .catch { ex ->
                if (ex is IOException) {
                    emit(emptyPreferences())
                } else throw ex
            }
            .map { preferences ->
                val showCompleted = preferences[key] ?: defaultValue
                showCompleted
            }
    }

    /**
     * Save Float
     */
    suspend fun saveFloat(key: Preferences.Key<Float>, name: Float) {
        context.myDataStore.edit { preferences ->
            preferences[key] = name
        }
    }

    //endregion
}