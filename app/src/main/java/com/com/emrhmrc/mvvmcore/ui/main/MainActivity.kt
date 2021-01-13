package com.com.emrhmrc.mvvmcore.ui.main

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.observe
import com.com.emrhmrc.mvvmcore.data.api.model.ApiUser
import com.com.emrhmrc.mvvmcore.databinding.ActivityMainBinding
import com.com.emrhmrc.mvvmcore.utils.Status
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
            when (it.status) {
                Status.SUCCESS -> {
                    activityMainBinding.progressBar.visibility = View.GONE
                    it.data?.let { users -> renderList(users) }
                }
                Status.LOADING -> {
                    activityMainBinding.progressBar.visibility = View.VISIBLE
                }
                Status.ERROR -> {
                    //Handle Error
                    activityMainBinding.progressBar.visibility = View.GONE
                    Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    private fun renderList(apiUsers: List<ApiUser>) {

        Toast.makeText(this, apiUsers.toString(), Toast.LENGTH_LONG).show()
    }
}
