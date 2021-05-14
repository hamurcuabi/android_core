package com.emrhmrc.mvvmcore.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding

/**
 *  URL           https://macellan.net/
 *  Rev           1.0
 *  Author        EmreHamurcu
 *  Date          5/12/2021
 *  FileName      BaseActivity
 */

abstract class BaseActivity<VB : ViewBinding>(
    private val inflateActivityView: InflateActivityView<VB>
) : AppCompatActivity() {
    private var _binding: VB? = null
    protected val binding get() = _binding!!

    abstract fun init()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = inflateActivityView.invoke(layoutInflater)
        setContentView(binding.root)
        init()
    }
}
