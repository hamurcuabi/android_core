package com.lagina.mvvmcore.utils

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

/**Created by hamurcuabi on 28,October,2020 **/
@Singleton
class ResourceProvider @Inject constructor(@ApplicationContext private val context: Context) {
    fun getString(id: Int): String = context.getString(id)
}