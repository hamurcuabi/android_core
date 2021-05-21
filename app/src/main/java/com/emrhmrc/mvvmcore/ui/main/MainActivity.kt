package com.emrhmrc.mvvmcore.ui.main

import androidx.activity.viewModels
import com.emrhmrc.mvvmcore.base.BaseActivity
import com.emrhmrc.mvvmcore.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>(ActivityMainBinding::inflate) {

    private val mainViewModel: MainViewModel by viewModels()
    private lateinit var userAdapter: UserAdapter
    private fun setupObserver() {
        mainViewModel.fetchLocalUsers().observe(this, {
            it?.let {
                userAdapter.submitList(it)
            }
        })
    }

    override fun init() {
        initRecycler()
        setupObserver()
        mainViewModel.fetchUsers()
    }

    private fun initRecycler() {
        userAdapter = UserAdapter()
        binding.rcv.adapter = userAdapter
    }
}
