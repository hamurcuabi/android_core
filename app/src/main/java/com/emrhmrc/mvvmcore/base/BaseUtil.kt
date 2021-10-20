package com.emrhmrc.mvvmcore.base

import android.app.Activity
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.RecyclerView
import com.emrhmrc.mvvmcore.R
import com.emrhmrc.mvvmcore.data.network.model.ApiUser
import com.emrhmrc.mvvmcore.ui.main.UserAdapter
import com.emrhmrc.mvvmcore.ui.main.UserLoadingAdapter

typealias InflateActivityView<T> = (LayoutInflater) -> T

typealias InflateFragmentView<T> = (LayoutInflater, ViewGroup?, Boolean) -> T

typealias Str = R.string

typealias Drw = R.drawable

fun String?.isNullOrStringNull(): Boolean {
    return this == null || this == "null"
}

fun FragmentActivity.showDialog(dialogFragment: DialogFragment) {
    dialogFragment.show(supportFragmentManager, dialogFragment.tag)
}

fun Fragment.getColor(@ColorRes colorId: Int): Int {
    return ContextCompat.getColor(requireContext(), colorId)
}

fun Activity.getColor(@ColorRes colorId: Int): Int {
    return ContextCompat.getColor(this, colorId)
}

fun Fragment.getDrawable(@DrawableRes drawableRes: Int): Drawable? {
    return ContextCompat.getDrawable(requireContext(), drawableRes)
}

fun Activity.getDrawable(@DrawableRes drawableRes: Int): Drawable? {
    return ContextCompat.getDrawable(this, drawableRes)
}

@BindingAdapter("submitList")
fun setRecyclerViewProperties(recyclerView: RecyclerView, items: List<ApiUser>?) {
    items?.let {
        val adapter = recyclerView.adapter as ConcatAdapter
        if (adapter.adapters[0] is UserAdapter) {
            (adapter.adapters[0] as UserAdapter).submitList(items)
        }
    }
}

@BindingAdapter("setLoading")
fun setRecyclerViewLoading(recyclerView: RecyclerView, isVisible: Boolean?) {
    isVisible?.let {
        val loadingAdapter = UserLoadingAdapter()
        val concatAdapter = recyclerView.adapter as ConcatAdapter
        if (isVisible) {
            val list = mutableListOf(1)
            loadingAdapter.submitList(list)
            val exist = concatAdapter.adapters.filterIsInstance<UserLoadingAdapter>().isEmpty()
            if (exist) {
                concatAdapter.addAdapter(1, loadingAdapter)
                concatAdapter.notifyItemInserted(1)
                return@let
            }

        } else {
            concatAdapter.adapters.forEachIndexed { i, adapter ->
                if (concatAdapter.adapters[i] is UserLoadingAdapter) {
                    concatAdapter.removeAdapter(adapter)
                    concatAdapter.notifyItemRemoved(i)
                }
            }
        }
    }
}