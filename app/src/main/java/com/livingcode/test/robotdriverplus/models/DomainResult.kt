package com.livingcode.test.robotdriverplus.models

class DomainResult<T>(val data : T? = null, val error : Errors? = null) {
    fun isSuccessful() : Boolean {
        return error == null
    }
}

enum class Errors {
    ERROR_CONNECTION_FAILED
}