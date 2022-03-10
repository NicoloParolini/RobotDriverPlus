package com.livingcode.test.robotdriverplus.ui.navigation

enum class FlowResult(var payload : Any? = null) {
    RESULT_BACK,
    RESULT_OK,
    RESULT_ELSE,
    RESULT_CONTROLLER_SELECTED
}