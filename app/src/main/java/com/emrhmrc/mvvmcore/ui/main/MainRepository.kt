package com.emrhmrc.mvvmcore.ui.main

import com.emrhmrc.mvvmcore.base.BaseRepository
import com.emrhmrc.mvvmcore.data.local.AppDatabase
import com.emrhmrc.mvvmcore.data.network.ApiHelper
import com.emrhmrc.mvvmcore.data.network.model.ApiUser
import com.emrhmrc.mvvmcore.di.DispatcherProvider
import com.emrhmrc.mvvmcore.helper.NetworkHelper
import com.emrhmrc.mvvmcore.mapper.UserApiMapper
import com.emrhmrc.mvvmcore.utils.ErrorType
import com.emrhmrc.mvvmcore.utils.NetworkResource
import com.emrhmrc.mvvmcore.utils.Resource
import com.emrhmrc.mvvmcore.utils.ResourceProvider
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class MainRepository @Inject constructor(
    private val apiHelper: ApiHelper,
    private val appDatabase: AppDatabase,
    networkHelper: NetworkHelper,
    resourceProvider: ResourceProvider,
    dispatcher: DispatcherProvider
) : BaseRepository(networkHelper, resourceProvider, dispatcher) {
    @Inject
    lateinit var userApiMapper: UserApiMapper
    suspend fun getUsers(): Resource<List<ApiUser>> {
        val networkResponse = safeApiCall {
            apiHelper.getUsers()
        }
        return when (networkResponse) {
            is NetworkResource.Success -> Resource.Success(networkResponse.data?.body())
            is NetworkResource.Error -> Resource.Failure(ErrorType.NetworkError(networkResponse.statusCode,networkResponse.message))
        }
    }

    fun getUsersTest() = flow {
        emit(Resource.Loading)
        val networkResponse = safeApiCall {
            apiHelper.getUsers()
        }
        val response = when (networkResponse) {
            is NetworkResource.Success -> Resource.Success(networkResponse.data?.body())
            is NetworkResource.Error -> Resource.Failure(ErrorType.NetworkError(networkResponse.statusCode,networkResponse.message))
        }
        emit(response)
    }

    fun getAllUser() = appDatabase.userDao().getAll()
}