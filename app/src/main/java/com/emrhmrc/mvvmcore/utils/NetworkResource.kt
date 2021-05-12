package com.emrhmrc.mvvmcore.utils

sealed class NetworkResource<T>(val data: T?, val message: String) {
    class Success<T>(data: T?) : NetworkResource<T>(data, "Succes")
    class Error<T>(message: String) : NetworkResource<T>(null, message)
}