package com.lagina.mvvmcore.ui.main

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.observe
import com.lagina.mvvmcore.utils.Resource
import com.lagina.mvvmcore.data.network.model.ApiUser
import com.lagina.mvvmcore.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val mainViewModel: MainViewModel by viewModels()
    private lateinit var activityMainBinding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(activityMainBinding.root)
        setupUI()
        setupObserver()
    }

    private fun setupUI() {

    }

    private fun setupObserver() {
        mainViewModel.users.observe(this) {
            when (it) {
                is Resource.Success -> {
                    activityMainBinding.progressBar.visibility = View.GONE
                    renderList(it.value.body())
                }
                is Resource.Loading -> {
                    activityMainBinding.progressBar.visibility = View.VISIBLE
                }
                is Resource.Failure -> {
                    activityMainBinding.progressBar.visibility = View.GONE
                    Toast.makeText(this, it.errorMessage, Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    private fun renderList(apiUsers: List<ApiUser>?) {

        Toast.makeText(this, apiUsers.toString(), Toast.LENGTH_LONG).show()
    }
}
