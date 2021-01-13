package com.lagina.mvvmcore.ui.main

import com.lagina.mvvmcore.data.network.ApiHelper
import javax.inject.Inject

class MainRepository @Inject constructor(private val apiHelper: ApiHelper) {

    suspend fun getUsers() =  apiHelper.getUsers()

}