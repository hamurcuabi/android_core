package com.emrhmrc.mvvmcore.base

import com.emrhmrc.mvvmcore.di.DispatcherProvider
import com.emrhmrc.mvvmcore.utils.NetworkResource
import com.emrhmrc.mvvmcore.utils.ResourceProvider
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

abstract class BaseRepository constructor(
    private val resourceProvider: ResourceProvider,
    private val dispatcher: DispatcherProvider
) {
    suspend fun <T> safeApiCall(
        apiCall: suspend () -> T
    ): NetworkResource<T> {
        return withContext(dispatcher.io) {
            try {
                val result = apiCall.invoke()
                NetworkResource.Success(result)
            } catch (e: Exception) {
                when (e) {
                    is UnknownHostException -> NetworkResource.Error(
                        resourceProvider.getString(Str.unexpected_error),
                        500
                    )
                    is HttpException -> NetworkResource.Error(
                        e.message(),
                        e.code()
                    )
                    is SocketTimeoutException -> NetworkResource.Error(
                        e.message.toString(),
                        500
                    )
                    else -> NetworkResource.Error(
                        resourceProvider.getString(Str.unexpected_error),
                        500
                    )
                }
            }
        }
    }
}
