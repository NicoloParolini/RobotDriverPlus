package com.livingcode.test.robotdriverplus.ui.controller

import com.livingcode.test.robotdriverplus.StateFlowContainer

class ControllerViewModel {
    val selected = StateFlowContainer(ControllerButtons.NONE)

    enum class ControllerButtons {
        L1,
        L2,
        R1,
        R2,
        LSTICK_UP,
        LSTICK_DOWN,
        LSTICK_LEFT,
        LSTICK_RIGHT,
        RSTICK_UP,
        RSTICK_DOWN,
        RSTICK_LEFT,
        RSTICK_RIGHT,
        NONE;

        fun toRightStick(): JoystickViewModel.Directions {
            return when (this) {
                RSTICK_UP -> JoystickViewModel.Directions.UP
                RSTICK_DOWN -> JoystickViewModel.Directions.DOWN
                RSTICK_LEFT -> JoystickViewModel.Directions.LEFT
                RSTICK_RIGHT -> JoystickViewModel.Directions.RIGHT
                else -> JoystickViewModel.Directions.UNUSED
            }
        }

        fun toLeftStick(): JoystickViewModel.Directions {
            return when (this) {
                LSTICK_UP -> JoystickViewModel.Directions.UP
                LSTICK_DOWN -> JoystickViewModel.Directions.DOWN
                LSTICK_LEFT -> JoystickViewModel.Directions.LEFT
                LSTICK_RIGHT -> JoystickViewModel.Directions.RIGHT
                else -> JoystickViewModel.Directions.UNUSED
            }
        }
    }
}