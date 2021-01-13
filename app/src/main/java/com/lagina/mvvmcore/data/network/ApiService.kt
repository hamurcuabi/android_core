package com.lagina.mvvmcore.data.network

import com.lagina.mvvmcore.data.network.model.ApiUser
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {

    @GET("users")
    suspend fun getUsers(): Response<List<ApiUser>>

}