package com.emrhmrc.mvvmcore.ui.main

import com.emrhmrc.mvvmcore.base.BaseRepository
import com.emrhmrc.mvvmcore.data.network.ApiHelper
import com.emrhmrc.mvvmcore.data.network.model.ApiUser
import com.emrhmrc.mvvmcore.mapper.UserApiMapper
import com.emrhmrc.mvvmcore.utils.NetworkHelper
import com.emrhmrc.mvvmcore.utils.NetworkResource
import com.emrhmrc.mvvmcore.utils.Resource
import com.emrhmrc.mvvmcore.utils.ResourceProvider
import javax.inject.Inject

class MainRepository @Inject constructor(
    private val apiHelper: ApiHelper,
    networkHelper: NetworkHelper,
    resourceProvider: ResourceProvider

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