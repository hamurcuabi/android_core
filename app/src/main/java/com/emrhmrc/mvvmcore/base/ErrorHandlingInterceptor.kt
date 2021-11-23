package com.emrhmrc.mvvmcore.base

import com.emrhmrc.mvvmcore.helper.NetworkHelper
import okhttp3.Interceptor
import okhttp3.Response
import retrofit2.HttpException
import java.io.IOException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import javax.inject.Inject

/**
 *  Rev           1.0
 *  Author        EmreHamurcu
 *  Date          11/23/2021
 *  FileName      ErrorHandlingInterceptor
 */
class ErrorHandlingInterceptor @Inject constructor(
    private val networkHandler: NetworkHelper
) : Interceptor {
    
    override fun intercept(chain: Interceptor.Chain): Response {
        if (!networkHandler.isNetworkConnected()) {
            throw NoConnectivityError
        }

        return try {
            chain.proceed(chain.request())
        } catch (e: Exception) {
            throw when (e) {
                is UnknownHostException -> UnknownHostError
                is HttpException -> HttpError(e.code(), e.message())
                is SocketTimeoutException -> TimeOutError(e.message)
                else -> IOException(e)
            }
        }
    }
}


sealed class Failure : IOException()
object JsonError : Failure()
object UnknownHostError : Failure()
object NoConnectivityError : Failure()

data class TimeOutError(
    override val message: String?
) : Failure()

data class UnknownError(
    val throwable: Throwable
) : Failure()

data class HttpError(
    val code: Int,
    override val message: String
) : Failure()