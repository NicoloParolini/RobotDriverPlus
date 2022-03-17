package com.livingcode.test.robotdriverplus.domain.configuration

import com.livingcode.test.robotdriverplus.domain.robot.Motors
import com.livingcode.test.robotdriverplus.ui.models.Controller
import com.livingcode.test.robotdriverplus.ui.models.Robot
import timber.log.Timber

class Configurator {
    private val configCache: MutableMap<String, Configuration> = mutableMapOf()

    fun addCommand(
        controller: Controller,
        button: ControllerButtons,
        robot: Robot,
        motor: Motors,
        dir: MotorCommand
    ) {
        val command = mutableMapOf(MotorId(robot.macAddress, motor) to dir)
        configCache[controller.descriptor]?.let { config ->
            config.commands[button] = command
        }
            ?: configCache.put(
                controller.descriptor,
                Configuration(controller = controller, commands = mutableMapOf(button to command))
            )
        Timber.v("Current commands = $configCache")
    }

    fun getCommands(button: ControllerButtons, controller: String): List<Command> {
        return configCache[controller]?.let { config ->
            config.commands[button]?.entries?.map { cmd ->
                Command(robot = cmd.key.robot, motor = cmd.key.motor, cmd = cmd.value)
            }
        } ?: listOf()
    }

    data class Command(val robot: String, val motor: Motors, val cmd: MotorCommand)
}