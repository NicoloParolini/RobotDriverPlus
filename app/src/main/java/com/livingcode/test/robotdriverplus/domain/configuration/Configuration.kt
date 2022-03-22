package com.livingcode.test.robotdriverplus.domain.configuration

import com.livingcode.test.robotdriverplus.domain.robot.Motors
import com.livingcode.test.robotdriverplus.ui.models.Controller
import com.livingcode.test.robotdriverplus.ui.models.Robot

data class Configuration(
    val controller: String,
    val commands: MutableMap<ControllerButtons, MutableMap<MotorId, MotorCommand>>
)

data class MotorId(val robot: String, val motor: Motors)

// for serializing purposes
data class JsonConfiguration(
    val controller: String,
    val commands: List<JsonCommand>
)

data class JsonCommand(
    val button: ControllerButtons,
    val robot: String,
    val motor: Motors,
    val command: MotorCommand
)

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
