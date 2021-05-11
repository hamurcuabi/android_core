package com.emrhmrc.mvvmcore.data.network

import com.emrhmrc.mvvmcore.data.network.model.ApiUser
import retrofit2.Response

interface ApiHelper {

    suspend fun getUsers():Response<List<ApiUser>>
}