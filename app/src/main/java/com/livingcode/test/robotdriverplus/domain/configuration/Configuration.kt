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
    L1_DOWN,
    L1_UP,
    L2_DOWN,
    L2_UP,
    R1_DOWN,
    R1_UP,
    R2_DOWN,
    R2_UP,
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
