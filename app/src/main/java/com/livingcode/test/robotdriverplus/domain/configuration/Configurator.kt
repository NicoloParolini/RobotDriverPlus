package com.livingcode.test.robotdriverplus.domain.configuration

import timber.log.Timber

class Configurator {
    private val configCache: MutableMap<String, Configuration> = mutableMapOf()

    suspend fun addCommand(
        controller: String,
        button: ControllerButtons,
        robot: String,
        motor: String,
        dir: MotorCommand
    ) {
        val motorId = MotorId(robot, motor)
        val command = mutableMapOf(button to mutableMapOf(MotorId(robot, motor) to dir))
        configCache[controller]?.let { config ->
            config.commands.find { comm -> comm.containsKey(button) }?.let { bcomm ->
                bcomm[button]?.put(motorId, dir)
            }
                ?: config.commands.add(command)
        }
            ?: configCache.put(
                controller,
                Configuration(controller = controller, commands = mutableListOf(command))
            )
        Timber.v("Current commands = $configCache")
    }
}