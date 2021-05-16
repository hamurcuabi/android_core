package com.emrhmrc.mvvmcore.utils

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.emrhmrc.mvvmcore.helper.NetworkHelper
import com.google.common.truth.Truth.assertThat
import org.junit.Test

/**
 * Rev           1.0
 * Author        hamurcuabi
 * Date          5/16/2021
 * FileName
 */
class NetworkHelperTest {

    private val context = ApplicationProvider.getApplicationContext<Context>()
    private val networkHelper = NetworkHelper(context)

    @Test
    fun isNetworkConnected() {
        assertThat(networkHelper.isNetworkConnected()).isTrue()
    }
}