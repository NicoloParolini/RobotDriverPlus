package com.livingcode.test.robotdriverplus.ui.models

sealed interface Device {
    val name: String
    val connected: Boolean
}

data class Controller(
    override val name: String,
    override val connected: Boolean,
    val descriptor: String,
    val id: Int,
    val robots: List<Robot> = listOf()
) : Device

data class Robot(
    override val name: String,
    override val connected: Boolean = false,
    val macAddress: String
) : Device