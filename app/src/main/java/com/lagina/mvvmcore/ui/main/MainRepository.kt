package com.lagina.mvvmcore.ui.main

import com.lagina.mvvmcore.base.BaseRepository
import com.lagina.mvvmcore.data.local.DatabaseHelper
import com.lagina.mvvmcore.data.network.ApiHelper
import com.lagina.mvvmcore.data.network.model.ApiUser
import com.lagina.mvvmcore.mapper.UserApiMapper
import com.lagina.mvvmcore.utils.NetworkHelper
import com.lagina.mvvmcore.utils.NetworkResource
import com.lagina.mvvmcore.utils.Resource
import com.lagina.mvvmcore.utils.ResourceProvider
import javax.inject.Inject

class MainRepository @Inject constructor(
    private val apiHelper: ApiHelper,
    private val dbHelper: DatabaseHelper,
    private val networkHelper: NetworkHelper,
    private val resourceProvider: ResourceProvider

) : BaseRepository(networkHelper, resourceProvider) {
    @Inject
    lateinit var userApiMapper: UserApiMapper

    suspend fun getUsers(): Resource<List<ApiUser>> {
        val networkResponse = safeApiCall {
            apiHelper.getUsers()
        }
        return when (networkResponse) {
            is NetworkResource.Success -> Resource.Success(networkResponse.data!!.body()!!)
            is NetworkResource.Error -> Resource.Failure(networkResponse.message)
        }
    }

}