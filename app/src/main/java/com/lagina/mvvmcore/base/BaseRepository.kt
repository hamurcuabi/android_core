package com.lagina.mvvmcore.base

import com.lagina.mvvmcore.R
import com.lagina.mvvmcore.utils.NetworkHelper
import com.lagina.mvvmcore.utils.NetworkResource
import com.lagina.mvvmcore.utils.ResourceProvider
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException

abstract class BaseRepository constructor(
    private val networkHelper: NetworkHelper,
    private val resourceProvider: ResourceProvider
) {
    suspend fun <T> safeApiCall(
        apiCall: suspend () -> T
    ): NetworkResource<T> {


        return withContext(Dispatchers.IO) {

            if (networkHelper.isNetworkConnected()) {

                try {
                    NetworkResource.Success(apiCall.invoke())
                } catch (throwable: Throwable) {
                    when (throwable) {
                        is HttpException -> {
                            NetworkResource.Error(throwable.message())
                        }
                        else -> {
                            NetworkResource.Error(
                                resourceProvider.getString(R.string.unexcpected_error)
                            )
                        }
                    }
                }

            } else {
                NetworkResource.Error(
                    resourceProvider.getString(R.string.no_internet_connection)
                )
            }

        }
    }

}