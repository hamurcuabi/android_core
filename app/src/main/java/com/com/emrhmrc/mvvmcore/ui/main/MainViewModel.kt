package com.com.emrhmrc.mvvmcore.ui.main

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.com.emrhmrc.mvvmcore.R
import com.com.emrhmrc.mvvmcore.data.local.DatabaseHelper
import com.com.emrhmrc.mvvmcore.data.local.entity.UserEntity
import com.com.emrhmrc.mvvmcore.data.api.model.ApiUser
import com.com.emrhmrc.mvvmcore.utils.NetworkHelper
import com.com.emrhmrc.mvvmcore.utils.Resource
import com.com.emrhmrc.mvvmcore.utils.ResourceProvider
import kotlinx.coroutines.launch

class MainViewModel @ViewModelInject constructor(
    private val mainRepository: MainRepository,
    private val networkHelper: NetworkHelper,
    private val dbHelper: DatabaseHelper,
    private val resourceProvider: ResourceProvider
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
                    } else _users.postValue(Resource.error(it.errorBody().toString(), null))
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