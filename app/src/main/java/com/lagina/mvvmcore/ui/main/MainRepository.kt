package com.lagina.mvvmcore.ui.main

import com.lagina.mvvmcore.base.BaseRepository
import com.lagina.mvvmcore.utils.Resource
import com.lagina.mvvmcore.data.local.DatabaseHelper
import com.lagina.mvvmcore.data.network.ApiHelper
import com.lagina.mvvmcore.data.network.model.ApiUser
import com.lagina.mvvmcore.mapper.UserApiMapper
import com.lagina.mvvmcore.utils.NetworkHelper
import com.lagina.mvvmcore.utils.ResourceProvider
import retrofit2.Response
import javax.inject.Inject

class MainRepository @Inject constructor(
    private val apiHelper: ApiHelper,
    private val dbHelper: DatabaseHelper,
    private val networkHelper: NetworkHelper,
    private val resourceProvider: ResourceProvider

) : BaseRepository(networkHelper, resourceProvider) {
    @Inject
    lateinit var userApiMapper: UserApiMapper

    suspend fun getUsers(): Resource<Response<List<ApiUser>>> {
        val result = safeApiCall {
            apiHelper.getUsers()
        }
        when (result) {
            is Resource.Success -> {
            }
            is Resource.Loading -> {
            }
            is Resource.Failure -> {

            }
        }
        return result

    }


}