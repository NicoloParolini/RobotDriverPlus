package com.livingcode.test.robotdriverplus.domain.configuration

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.livingcode.test.robotdriverplus.domain.controller.ControllerStorage
import com.livingcode.test.robotdriverplus.domain.robot.Motors
import com.livingcode.test.robotdriverplus.ui.models.Controller
import com.livingcode.test.robotdriverplus.ui.models.Robot
import timber.log.Timber

class Configurator(
    private val storage: ConfigurationStorage
) {
    private val configCache: MutableMap<String, Configuration>

    init {
        configCache =
            storage.loadConfigurationMap().mapValues { it.value.toConfiguration() }.toMutableMap()
    }

    fun addCommand(
        controller: Controller,
        button: ControllerButtons,
        robot: Robot,
        motor: Motors,
        dir: MotorCommand
    ) {
        val motorId = MotorId(robot.macAddress, motor)
        val command = mutableMapOf(motorId to dir)
        configCache[controller.descriptor]?.let { config ->
            config.commands[button]?.let{
                it[motorId] = dir
            }?: kotlin.run { config.commands[button] = command }
        }
            ?: configCache.put(
                controller.descriptor,
                Configuration(
                    controller = controller.descriptor,
                    commands = mutableMapOf(button to command)
                )
            )
        configCache[controller.descriptor]?.let { storage.saveConfiguration(it) }
    }

    fun getCommands(button: ControllerButtons, controller: String): List<Command> {
        return configCache[controller]?.let { config ->
            config.commands[button]?.entries?.map { cmd ->
                Command(robot = cmd.key.robot, motor = cmd.key.motor, cmd = cmd.value)
            }
        } ?: listOf()
    }

    fun getConfiguration(controller : String) : Configuration? {
        return configCache[controller]
    }

    private fun JsonConfiguration.toConfiguration(): Configuration {
        val mapCommands: MutableMap<ControllerButtons, MutableMap<MotorId, MotorCommand>> =
            mutableMapOf()
        commands.forEach { jCmd ->
            mapCommands.putIfAbsent(
                jCmd.button,
                mutableMapOf(MotorId(robot = jCmd.robot, motor = jCmd.motor) to jCmd.command)
            )?.let {
                it[MotorId(robot = jCmd.robot, motor = jCmd.motor)] = jCmd.command
            }
        }
        return Configuration(controller = controller, commands = mapCommands)
    }

    data class Command(val robot: String, val motor: Motors, val cmd: MotorCommand)
}