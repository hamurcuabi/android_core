package com.emrhmrc.mvvmcore.base

import com.emrhmrc.mvvmcore.di.DispatcherProvider
import com.emrhmrc.mvvmcore.helper.NetworkHelper
import com.emrhmrc.mvvmcore.utils.NetworkResource
import com.emrhmrc.mvvmcore.utils.ResourceProvider
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException
import java.net.SocketTimeoutException

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
                    val result = apiCall.invoke()
                    when ((result as Response<*>).code()) {
                        in 200..300 -> NetworkResource.Success(result)
                        401 -> NetworkResource.Error(resourceProvider.getString(Str.unexpected_error),401)
                        else -> NetworkResource.Error(result.message(),result.code())
                    }
                } catch (throwable: Throwable) {
                    when (throwable) {
                        is HttpException -> NetworkResource.Error(throwable.message(),500)
                        is SocketTimeoutException -> NetworkResource.Error(
                            resourceProvider.getString(
                                Str.socket_exception
                            ),500
                        )
                        is IOException -> NetworkResource.Error(resourceProvider.getString(Str.io_exception),500)
                        else -> NetworkResource.Error(resourceProvider.getString(Str.unexpected_error),500)
                    }
                }

            } else {
                NetworkResource.Error(resourceProvider.getString(Str.no_internet_connection),500)
            }
        }
    }
}