package com.emrhmrc.mvvmcore.utils

/**
 *  Rev           1.0
 *  Author        EmreHamurcu
 *  Date          9/17/2021
 *  FileName      Validator
 */

typealias Rule<T> = (value: T?) -> Boolean

class Validator<T>(private val data: T) {

    private val validationRules = ArrayList<Rule<T>>()
    private var errorMessages = ArrayList<String?>()

    fun addRule(errorMsg: String, rule: Rule<T>) {
        validationRules.add(rule)
        errorMessages.add(errorMsg)
    }

    fun isValid(): Pair<Boolean, String?> {
        for (i in 0 until validationRules.size) {
            if (validationRules[i](data)) {
                val errorMsg = errorMessages[i]
                return Pair(false, errorMsg)
            }
        }
        return Pair(false, "Data is valid")
    }

    fun isValidList(): List<Pair<Boolean, String?>> {
        val pairList = ArrayList<Pair<Boolean, String?>>()

        for (i in 0 until validationRules.size) {
            if (validationRules[i](data)) {
                val errorMsg = errorMessages[i]
                pairList.add(Pair(false, errorMsg))
            }
        }
        return pairList
    }
}

