package com.livingcode.test.robotdriverplus.ui.models

class DomainResult<T>(val data : T? = null, val error : Errors? = null) {
    fun isSuccessful() : Boolean {
        return error == null
    }
}

enum class Errors {
    ERROR_CONNECTION_FAILED, ERROR_NO_SOCKET, ERROR_NOT_CONNECTED, ERROR_CLOSE_FAILED
}