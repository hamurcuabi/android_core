package com.com.emrhmrc.mvvmcore.data.api

import com.com.emrhmrc.mvvmcore.data.api.model.ApiUser
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {

    @GET("users")
    suspend fun getUsers(): Response<List<ApiUser>>

}