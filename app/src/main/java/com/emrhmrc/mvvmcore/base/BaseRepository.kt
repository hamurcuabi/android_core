package com.emrhmrc.mvvmcore.base

import com.emrhmrc.mvvmcore.R
import com.emrhmrc.mvvmcore.utils.DispatcherProvider
import com.emrhmrc.mvvmcore.utils.NetworkHelper
import com.emrhmrc.mvvmcore.utils.NetworkResource
import com.emrhmrc.mvvmcore.utils.ResourceProvider
import kotlinx.coroutines.withContext
import retrofit2.HttpException

abstract class BaseRepository constructor(
    private val networkHelper: NetworkHelper,
    private val resourceProvider: ResourceProvider,
    private val dispatcher: DispatcherProvider
) {
    suspend fun <T> safeApiCall(
        apiCall: suspend () -> T
    ): NetworkResource<T> {
        return withContext(dispatcher.io) {
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