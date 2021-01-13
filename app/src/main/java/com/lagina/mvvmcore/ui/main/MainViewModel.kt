package com.lagina.mvvmcore.ui.main

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lagina.mvvmcore.R
import com.lagina.mvvmcore.data.local.DatabaseHelper
import com.lagina.mvvmcore.data.local.entity.UserEntity
import com.lagina.mvvmcore.data.api.model.ApiUser
import com.lagina.mvvmcore.utils.DataStoreHelper
import com.lagina.mvvmcore.utils.NetworkHelper
import com.lagina.mvvmcore.utils.Resource
import com.lagina.mvvmcore.utils.ResourceProvider
import kotlinx.coroutines.launch
import kotlin.math.log

class MainViewModel @ViewModelInject constructor(
    private val mainRepository: MainRepository,
    private val networkHelper: NetworkHelper,
    private val dbHelper: DatabaseHelper,
    private val resourceProvider: ResourceProvider,
    private val dataStoreHelper: DataStoreHelper
) : ViewModel() {

    private val _users = MutableLiveData<Resource<List<ApiUser>>>()
    val users: LiveData<Resource<List<ApiUser>>> get() = _users

    init {
        fetchUsers()
    }

    private fun fetchUsers() {
        viewModelScope.launch {
            _users.postValue(Resource.loading(null))
            if (networkHelper.isNetworkConnected()) {
                mainRepository.getUsers().let {
                    if (it.isSuccessful) {
                        _users.postValue(Resource.success(it.body()))
                        val apiUser = it.body()!![0]
                        val user = UserEntity()
                        user.apply {
                            name = apiUser.name
                            email = apiUser.email
                            avatar = apiUser.avatar

                        }
                        dbHelper.insert(user)
                    }
                    else _users.postValue(Resource.error(it.errorBody().toString(), null))
                }
            } else _users.postValue(
                Resource.error(
                    resourceProvider.getString(R.string.no_internet_connection),
                    null
                )
            )
        }
    }
}