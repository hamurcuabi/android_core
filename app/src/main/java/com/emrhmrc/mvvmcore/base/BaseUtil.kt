package com.emrhmrc.mvvmcore.base

import android.app.Activity
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.emrhmrc.mvvmcore.R

/**
 *  URL           https://macellan.net/
 *  Rev           1.0
 *  Author        EmreHamurcu
 *  Date          5/12/2021
 *  FileName      BaseUtil
 */


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
