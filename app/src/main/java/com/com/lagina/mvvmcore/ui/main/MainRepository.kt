package com.com.lagina.mvvmcore.ui.main

import com.com.lagina.mvvmcore.data.api.ApiHelper
import javax.inject.Inject

class MainRepository @Inject constructor(private val apiHelper: ApiHelper) {

    suspend fun getUsers() =  apiHelper.getUsers()

}