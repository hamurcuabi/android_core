package com.lagina.mvvmcore.data.api

import com.lagina.mvvmcore.data.api.model.ApiUser
import retrofit2.Response

interface ApiHelper {

    suspend fun getUsers(): Response<List<ApiUser>>
}