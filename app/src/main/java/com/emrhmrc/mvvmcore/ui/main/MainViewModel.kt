package com.emrhmrc.mvvmcore.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.emrhmrc.mvvmcore.data.network.model.ApiUser
import com.emrhmrc.mvvmcore.utils.DispatcherProvider
import com.emrhmrc.mvvmcore.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val mainRepository: MainRepository,
    private val dispatcherProvider: DispatcherProvider
) : ViewModel() {

    private val _users = MutableStateFlow<Resource<List<ApiUser>>>(Resource.Loading)
    val users: StateFlow<Resource<List<ApiUser>>> get() = _users

    init {
        fetchUsers()
    }

    private fun fetchUsers() = viewModelScope.launch(dispatcherProvider.io) {
        _users.value = Resource.Loading
        _users.value = mainRepository.getUsers()
    }
}