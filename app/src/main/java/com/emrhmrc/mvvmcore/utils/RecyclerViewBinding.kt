package com.emrhmrc.mvvmcore.utils

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

/**
 *  URL           https://macellan.net/
 *  Rev           1.0
 *  Author        EmreHamurcu
 *  Date          5/29/2021
 *  FileName      RecyclerViewBinding
 */
object RecyclerViewBinding {
    @JvmStatic
    @BindingAdapter("adapter")
    fun bindAdapter(view: RecyclerView, listAdapter: ListAdapter<*, *>) {
        view.adapter = listAdapter
    }
}