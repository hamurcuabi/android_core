package com.emrhmrc.mvvmcore.utils

sealed class Resource<out T> {

    data class Success<out T>(val value: T?) : Resource<T>()

    data class Failure(val errorType: ErrorType) : Resource<Nothing>()

    object Loading : Resource<Nothing>()
}

sealed class ErrorType{
    object UnKnownError:ErrorType()
    data class NetworkError(val statusCode:Int,val message:String):ErrorType()
    data class ValidationError(val message: String):ErrorType()

}