package com.lagina.mvvmcore.ui.main

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lagina.mvvmcore.utils.Resource
import com.lagina.mvvmcore.data.network.model.ApiUser
import com.lagina.mvvmcore.utils.ResourceProvider
import kotlinx.coroutines.launch
import retrofit2.Response

class MainViewModel @ViewModelInject constructor(
    private val mainRepository: MainRepository,
    private val resourceProvider: ResourceProvider,
) : ViewModel() {

    private val _users = MutableLiveData<Resource<Response<List<ApiUser>>>>()
    val users: LiveData<Resource<Response<List<ApiUser>>>> get() = _users

    init {
        fetchUsers()
    }

    private fun fetchUsers() = viewModelScope.launch {
        _users.value = Resource.Loading
        _users.value = mainRepository.getUsers()
    }
}