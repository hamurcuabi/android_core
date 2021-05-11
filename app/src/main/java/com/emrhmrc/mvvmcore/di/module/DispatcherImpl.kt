package com.emrhmrc.mvvmcore.di.module

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

/**
 *  URL           https://macellan.net/
 *  Rev           1.0
 *  Author        EmreHamurcu
 *  Date          5/11/2021
 *  FileName     DispatcherImpl
 */
class DispatcherImpl @Inject constructor() : DispatcherProvider {
    override val main: CoroutineDispatcher
        get() = Dispatchers.Main
    override val io: CoroutineDispatcher
        get() = Dispatchers.IO
    override val default: CoroutineDispatcher
        get() = Dispatchers.Default
    override val unconfined: CoroutineDispatcher
        get() = Dispatchers.Unconfined
}