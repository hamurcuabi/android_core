package com.lagina.mvvmcore.data.network

import com.lagina.mvvmcore.data.network.model.ApiUser
import retrofit2.Response
import javax.inject.Inject

class ApiHelperImpl @Inject constructor(private val apiService: ApiService) : ApiHelper {

    override suspend fun getUsers(): Response<List<ApiUser>> = apiService.getUsers()

}