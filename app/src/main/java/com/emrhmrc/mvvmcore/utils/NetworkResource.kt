package com.emrhmrc.mvvmcore.utils

sealed class NetworkResource<T>(val data: T?, val message: String,val statusCode:Int) {
    class Success<T>(data: T?) : NetworkResource<T>(data, "Success",200)
    class Error<T>(message: String,statusCode: Int) : NetworkResource<T>(null, message,statusCode)
}