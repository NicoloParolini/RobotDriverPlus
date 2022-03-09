package com.livingcode.test.robotdriverplus.models

sealed interface Device {
    val name : String
    var connected : Boolean
}

data class Controller(override val name : String, override var connected : Boolean) : Device

data class Robot (override val name : String, override var connected : Boolean) : Device