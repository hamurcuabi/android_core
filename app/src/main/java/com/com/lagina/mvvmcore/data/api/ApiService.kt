package com.com.lagina.mvvmcore.data.api

import com.com.lagina.mvvmcore.data.api.model.ApiUser
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {

    @GET("users")
    suspend fun getUsers(): Response<List<ApiUser>>

}