package com.emrhmrc.mvvmcore.ui.main

import android.app.Application
import androidx.lifecycle.viewModelScope
import com.emrhmrc.mvvmcore.baseviewstate.BaseMVIViewModel
import com.emrhmrc.mvvmcore.data.network.model.ApiUser
import com.emrhmrc.mvvmcore.di.DispatcherProvider
import com.emrhmrc.mvvmcore.ui.main.MainViewModel.*
import com.emrhmrc.mvvmcore.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    application: Application,
    private val mainRepository: MainRepository,
    private val dispatcherProvider: DispatcherProvider
) : BaseMVIViewModel<MainViewState, MainViewEffect, MainViewEvent>(application) {

    init {
        viewState = MainViewState()
    }

    override fun process(viewEvent: MainViewEvent) {
        super.process(viewEvent)
        when (viewEvent) {
            is MainViewEvent.GetUserListEvent -> fetchUsers()
            is MainViewEvent.ClickToItem -> itemClicked(viewEvent.item)
            is MainViewEvent.ClickToFab -> fetchUsers()
        }
    }

    private fun itemClicked(newsItem: ApiUser) {
        viewEffect = MainViewEffect.ShowToast(newsItem.toString())
    }

    private fun fetchUsers() {
        viewModelScope.launch {
            val responseFlow = mainRepository.getUsersTest().flowOn(Dispatchers.IO)
            responseFlow.collect {
                when (val response = it) {
                    is Resource.Failure -> {
                        viewState = viewState.copy(isLoading = false)
                        viewEffect = MainViewEffect.ShowToast(message = response.errorMessage)
                    }
                    is Resource.Loading -> {
                        viewState = viewState.copy(isLoading = true)
                        viewEffect = MainViewEffect.ShowToast(message = "Loading")
                    }
                    is Resource.Success -> {
                        viewState = viewState.copy(userList = response.value, isLoading = false)
                        viewEffect = MainViewEffect.ShowToast(message = "Success")
                    }
                }
            }
        }
    }

    sealed class MainViewEvent {
        object GetUserListEvent : MainViewEvent()
        data class ClickToItem(val item: ApiUser) : MainViewEvent()
        object ClickToFab : MainViewEvent()
    }

    data class MainViewState(
        val userList: List<ApiUser>? = emptyList(),
        val isLoading: Boolean = false
    )

    sealed class MainViewEffect {
        data class ShowToast(val message: String) : MainViewEffect()
    }
}