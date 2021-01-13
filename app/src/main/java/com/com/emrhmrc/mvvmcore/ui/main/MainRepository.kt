package com.com.emrhmrc.mvvmcore.ui.main

import com.com.emrhmrc.mvvmcore.data.api.ApiHelper
import javax.inject.Inject

class MainRepository @Inject constructor(private val apiHelper: ApiHelper) {

    suspend fun getUsers() =  apiHelper.getUsers()

}