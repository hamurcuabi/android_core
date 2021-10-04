package com.emrhmrc.mvvmcore.utils

class ValidatorResolver(private val validators: List<Validator<*>>) {

    fun isValid(): Pair<Boolean, String?> {
        for (validator in validators) {
            val isValid = validator.isValid()
            if (isValid.first.not()) return isValid
        }
        return Pair(true, "Valid")
    }

    fun isValidList(): List<Pair<Boolean, String?>> {

        val pairList = ArrayList<Pair<Boolean, String?>>()
        for (validator in validators) {
            pairList.add(validator.isValid())
        }
        return pairList
    }
}