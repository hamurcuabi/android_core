package com.emrhmrc.mvvmcore.ui.main

import android.widget.Toast
import androidx.activity.viewModels
import androidx.recyclerview.widget.ConcatAdapter
import com.emrhmrc.mvvmcore.base.BaseActivity
import com.emrhmrc.mvvmcore.databinding.ActivityMainBinding
import com.emrhmrc.mvvmcore.ui.main.MainViewModel.*
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.InternalCoroutinesApi

@InternalCoroutinesApi
@AndroidEntryPoint
class MainActivity :
    BaseActivity<ActivityMainBinding, MainViewState, MainViewEffect, MainViewEvent, MainViewModel>(
        ActivityMainBinding::inflate
    ) {
    private val TAG = "MainActivity"
    override val viewModel: MainViewModel by viewModels()
    private lateinit var userAdapter: UserAdapter

    override fun init() {
        initRecycler()
        binding.viewModel = viewModel
        binding.fabEvent = MainViewEvent.ClickToFab
        viewModel.isLoginFormValidMediator.observe(this, {
            it?.let {
                Toast.makeText(this, it.toString(), Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun initRecycler() {
        userAdapter = UserAdapter {
            viewModel.process(MainViewEvent.ClickToItem(it))
        }
        val concatAdapter = ConcatAdapter(userAdapter)
        binding.rcv.adapter = concatAdapter
    }

    override fun renderViewState(viewState: MainViewState) {
        binding.viewState = viewState
    }

    override fun renderViewEffect(viewEffect: MainViewEffect) {
        when (viewEffect) {
            is MainViewEffect.ShowToast -> showToast(viewEffect.message)
        }
    }

    private fun showToast(toString: String) {
        Toast.makeText(this, toString, Toast.LENGTH_SHORT).show()
    }

}
