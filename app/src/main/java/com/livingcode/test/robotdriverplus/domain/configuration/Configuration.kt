package com.livingcode.test.robotdriverplus.domain.configuration

import android.media.VolumeShaper
import com.livingcode.test.robotdriverplus.ui.controller.JoystickViewModel
import com.livingcode.test.robotdriverplus.ui.controller.MotorSelectorViewModel

data class Configuration(val controller : String, val commands : MutableList<MutableMap<ControllerButtons,MutableMap<MotorId, MotorCommand>>>)

data class MotorId(val robot : String, val motor : String)

enum class MotorCommand {
    FWD, BACK, COAST, BRAKE, NONE
}

enum class ControllerButtons {
    L1,
    L2,
    R1,
    R2,
    LSTICK_UP,
    LSTICK_DOWN,
    LSTICK_LEFT,
    LSTICK_RIGHT,
    LSTICK_OFF,
    RSTICK_UP,
    RSTICK_DOWN,
    RSTICK_LEFT,
    RSTICK_RIGHT,
    RSTICK_OFF,
    NONE
}
