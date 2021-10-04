package com.emrhmrc.mvvmcore.ui.main

import android.app.Application
import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.emrhmrc.mvvmcore.BR
import com.emrhmrc.mvvmcore.baseviewstate.BaseMVIViewModel
import com.emrhmrc.mvvmcore.data.network.model.ApiUser
import com.emrhmrc.mvvmcore.di.DispatcherProvider
import com.emrhmrc.mvvmcore.ui.main.MainViewModel.*
import com.emrhmrc.mvvmcore.utils.*
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
    class Test : BaseObservable() {
        @get:Bindable
        var name: String = ""
            set(value) {
                field = value
                notifyPropertyChanged(BR.name)
            }
    }

    val testLiveData = MutableLiveData<Test>()
    private val tesValidator = LiveDataValidator(testLiveData).apply {
        //Whenever the condition of the predicate is true, the error message should be emitted
        addRule("Name null") { it?.name.isNullOrEmpty() }
    }


    //We will use a mediator so we can update the error state of our form fields
    //and the enabled state of our login button as the form data changes
    val isLoginFormValidMediator = MediatorLiveData<Pair<Boolean, String?>>()
    //This is called whenever the usernameLiveData and passwordLiveData changes

    init {
        viewState = MainViewState()

        val test = Test().also { it.name = "emre" }
        isLoginFormValidMediator.value = Pair(false, "Not valid")
        isLoginFormValidMediator.addSource(testLiveData) { validateForm() }
        testLiveData.value = test

    }

    private fun validateForm() {
        isLoginFormValidMediator.value = listOf(tesValidator).validateAll()
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
                       val message= when(response.errorType){
                            is ErrorType.NetworkError -> response.errorType.message
                            is ErrorType.UnKnownError -> "Unknown"
                            is ErrorType.ValidationError -> response.errorType.message
                        }.exhaustive
                        viewEffect = MainViewEffect.ShowToast(message = message)

                    }
                    is Resource.Loading -> {
                        viewState = viewState.copy(isLoading = true)
                        viewEffect = MainViewEffect.ShowToast(message = "Loading")
                    }
                    is Resource.Success -> {
                        viewState = viewState.copy(userList = response.value, isLoading = false)
                        viewEffect = MainViewEffect.ShowToast(message = "Success")
                    }
                }.exhaustive
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


