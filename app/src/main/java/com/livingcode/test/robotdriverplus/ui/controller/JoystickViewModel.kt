package com.livingcode.test.robotdriverplus.ui.controller

import android.text.Layout
import com.livingcode.test.robotdriverplus.R
import com.livingcode.test.robotdriverplus.StateFlowContainer
import com.livingcode.test.robotdriverplus.domain.configuration.ControllerButtons

class JoystickViewModel(val name : String, private val onSelected : (Directions) -> Unit) {
    fun onSelect(direction : Directions) {
        onSelected(direction)
    }

    enum class Directions(val iconResource : Int) {
        UP(R.drawable.icon_up),
        DOWN(R.drawable.icon_down),
        LEFT(R.drawable.icon_left),
        RIGHT(R.drawable.icon_right),
        UNUSED(0)
    }
}

fun ControllerButtons.toRightStick(): JoystickViewModel.Directions {
    return when (this) {
        ControllerButtons.RSTICK_UP -> JoystickViewModel.Directions.UP
        ControllerButtons.RSTICK_DOWN -> JoystickViewModel.Directions.DOWN
        ControllerButtons.RSTICK_LEFT -> JoystickViewModel.Directions.LEFT
        ControllerButtons.RSTICK_RIGHT -> JoystickViewModel.Directions.RIGHT
        else -> JoystickViewModel.Directions.UNUSED
    }
}

fun ControllerButtons.toLeftStick(): JoystickViewModel.Directions {
    return when (this) {
        ControllerButtons.LSTICK_UP -> JoystickViewModel.Directions.UP
        ControllerButtons.LSTICK_DOWN -> JoystickViewModel.Directions.DOWN
        ControllerButtons.LSTICK_LEFT -> JoystickViewModel.Directions.LEFT
        ControllerButtons.LSTICK_RIGHT -> JoystickViewModel.Directions.RIGHT
        else -> JoystickViewModel.Directions.UNUSED
    }
}