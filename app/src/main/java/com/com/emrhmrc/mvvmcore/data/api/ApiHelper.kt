package com.com.emrhmrc.mvvmcore.data.api

import com.com.emrhmrc.mvvmcore.data.api.model.ApiUser
import retrofit2.Response

interface ApiHelper {

    suspend fun getUsers(): Response<List<ApiUser>>
}