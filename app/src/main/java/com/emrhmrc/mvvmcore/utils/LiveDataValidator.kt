package com.emrhmrc.mvvmcore.utils

import androidx.lifecycle.MutableLiveData

/**
 *  Rev           1.0
 *  Author        EmreHamurcu
 *  Date          9/12/2021
 *  FileName      LiveDataValidator
 */

//A predicate is a function that evaluates to true when its param matches the condition of the predicate
typealias Predicate<T> = (value: T?) -> Boolean

class LiveDataValidator<T>(private val liveData: MutableLiveData<T>) {
    private val validationRules = mutableListOf<Predicate<T>>()
    private val errorMessages = mutableListOf<String>()

    var error = MutableLiveData<String?>()

    //For checking if the liveData value matches the error condition set in the validation rule predicate
    //The livedata value is said to be valid when its value doesn't match an error condition set in the predicate
    fun isValid(): Boolean {
        for (i in 0 until validationRules.size) {
            if (validationRules[i](liveData.value)) {
                emitErrorMessage(errorMessages[i])
                return false
            }
        }

        emitErrorMessage(null)
        return true
    }

    //For emitting error messages
    private fun emitErrorMessage(messageRes: String?) {
        error.value = messageRes
    }

    //For adding validation rules
    fun addRule(errorMsg: String, predicate: Predicate<T>) {
        validationRules.add(predicate)
        errorMessages.add(errorMsg)
    }
}

fun List<LiveDataValidator<*>>.validateAll(): Pair<Boolean, String?> {
    val validatorResolver = LiveDataValidatorResolver(this)
    return validatorResolver.isValid()
}

class LiveDataValidatorResolver(private val validators: List<LiveDataValidator<*>>) {
    fun isValid(): Pair<Boolean, String?> {
        for (validator in validators) {
            if (!validator.isValid()) return Pair(false, validator.error.value)
        }
        return Pair(true, "Valid")
    }
}