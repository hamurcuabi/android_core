package com.lagina.mvvmcore.base

import com.lagina.mvvmcore.R
import com.lagina.mvvmcore.utils.NetworkHelper
import com.lagina.mvvmcore.utils.Resource
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
    ): Resource<T> {


        return withContext(Dispatchers.IO) {

            if (networkHelper.isNetworkConnected()) {

                try {
                    Resource.Success(apiCall.invoke())
                } catch (throwable: Throwable) {
                    when (throwable) {
                        is HttpException -> {
                            Resource.Failure(
                                false,
                                throwable.code(),
                                throwable.response()?.errorBody(),
                                resourceProvider.getString(R.string.unexcpected_error)
                            )
                        }
                        else -> {
                            Resource.Failure(
                                true,
                                null,
                                null,
                                resourceProvider.getString(R.string.unexcpected_error)
                            )
                        }
                    }
                }

            } else {

                Resource.Failure(
                    true,
                    null,
                    null,
                    resourceProvider.getString(R.string.no_internet_connection)
                )
            }

        }
    }

}