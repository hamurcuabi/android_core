package com.emrhmrc.mvvmcore.ui.main

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.emrhmrc.mvvmcore.data.network.model.ApiUser
import com.emrhmrc.mvvmcore.databinding.ActivityMainBinding
import com.emrhmrc.mvvmcore.utils.Resource
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val mainViewModel: MainViewModel by viewModels()
    private lateinit var activityMainBinding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(activityMainBinding.root)
        setupObserver()
    }

    private fun setupObserver() {
        lifecycleScope.launchWhenStarted {
            mainViewModel.users.collect {
                when (it) {
                    is Resource.Success -> {
                        activityMainBinding.progressBar.visibility = View.GONE
                        renderList(it.value)
                    }
                    is Resource.Loading -> {
                        activityMainBinding.progressBar.visibility = View.VISIBLE
                    }
                    is Resource.Failure -> {
                        activityMainBinding.progressBar.visibility = View.GONE
                    }
                }
            }
        }
    }

    private fun renderList(apiUsers: List<ApiUser>?) {
        Toast.makeText(this, apiUsers.toString(), Toast.LENGTH_LONG).show()
    }
}
