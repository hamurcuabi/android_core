package com.com.lagina.mvvmcore.data.api

import com.com.lagina.mvvmcore.data.api.model.ApiUser
import retrofit2.Response
import javax.inject.Inject

class ApiHelperImpl @Inject constructor(private val apiService: ApiService) : ApiHelper {

    override suspend fun getUsers(): Response<List<ApiUser>> = apiService.getUsers()

}