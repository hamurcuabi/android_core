package com.emrhmrc.mvvmcore.utils

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.floatPreferencesKey
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.lifecycle.asLiveData
import androidx.test.core.app.ApplicationProvider
import androidx.test.filters.SmallTest
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import com.emrhmrc.getOrAwaitValueTest
import com.emrhmrc.mvvmcore.helper.DataStoreHelper
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Rev           1.0
 * Author        hamurcuabi
 * Date          5/16/2021
 * FileName      DataStoreHelperTest
 */
@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4ClassRunner::class)
@SmallTest
class DataStoreHelperTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private val context = ApplicationProvider.getApplicationContext<Context>()
    private val dataStoreHelper = DataStoreHelper(context)

    private val stringKey = "stringKey"
    private val intKey = "intKey"
    private val floatKey = "floatKey"
    private val booleanKey = "booleanKey"

    @Test
    fun saveAndReadString() = runBlocking {
        val text = "test"
        val stringKey = stringPreferencesKey(stringKey)
        dataStoreHelper.saveString(stringKey, text)
        val messageSaved = dataStoreHelper.readString(stringKey).asLiveData().getOrAwaitValueTest()
        assertThat(messageSaved).isEqualTo(text)
        dataStoreHelper.clearDataStore()
    }

    @Test
    fun saveAndReadInt() = runBlocking {
        val intKey = intPreferencesKey(intKey)
        dataStoreHelper.saveInt(intKey, 1)
        val messageSaved = dataStoreHelper.readInt(intKey).asLiveData().getOrAwaitValueTest()
        assertThat(messageSaved).isEqualTo(1)
        dataStoreHelper.clearDataStore()
    }

    @Test
    fun saveAndReadFloat() = runBlocking {
        val floatKey = floatPreferencesKey(floatKey)
        dataStoreHelper.saveFloat(floatKey, 1.0f)
        val messageSaved = dataStoreHelper.readFloat(floatKey).asLiveData().getOrAwaitValueTest()
        assertThat(messageSaved).isEqualTo(1.0f)
        dataStoreHelper.clearDataStore()
    }

    @Test
    fun saveAndReadBoolean() = runBlocking {
        val booleanKey = booleanPreferencesKey(booleanKey)
        dataStoreHelper.saveBoolean(booleanKey, true)
        val messageSaved =
            dataStoreHelper.readBoolean(booleanKey).asLiveData().getOrAwaitValueTest()
        assertThat(messageSaved).isEqualTo(true)
        dataStoreHelper.clearDataStore()
    }

}