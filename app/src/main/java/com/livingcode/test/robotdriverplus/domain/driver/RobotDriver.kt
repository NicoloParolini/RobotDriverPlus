package com.livingcode.test.robotdriverplus.domain.driver

import com.livingcode.test.robotdriverplus.domain.configuration.Configurator
import com.livingcode.test.robotdriverplus.domain.configuration.ControllerButtons
import com.livingcode.test.robotdriverplus.domain.robot.Motors
import com.livingcode.test.robotdriverplus.domain.robot.RobotConnector
import com.livingcode.test.robotdriverplus.domain.robot.RobotStorage
import com.livingcode.test.robotdriverplus.ui.models.Robot
import timber.log.Timber

class RobotDriver(
    private val robotConnector: RobotConnector,
    private val configurator: Configurator,
    private val robotStorage: RobotStorage
) {
    private var lastRStickCmd: ControllerButtons = ControllerButtons.NONE
    private var lastLStickCmd: ControllerButtons = ControllerButtons.NONE
    private var lastButtonCmd: ControllerButtons = ControllerButtons.NONE

    fun processCommand(cmd: ControllerButtons, id: String, type: CommandType) {
        if (cmd != lastLStickCmd && cmd != lastRStickCmd && cmd != lastButtonCmd) {
            Timber.v("Received command $cmd from controller $id")
            when (type) {
                CommandType.RSTICK -> lastRStickCmd = cmd
                CommandType.LSTICK -> lastLStickCmd = cmd
                CommandType.BUTTON -> lastButtonCmd = cmd
            }
            val commands = configurator.getCommands(cmd, id)
            commands.forEach { comm ->
                robotStorage.getRobot(comm.robot)?.let {
                    robotConnector.commandRobot(robot = it, motor = comm.motor, command = comm.cmd)
                }
            }
        }
    }

    enum class CommandType {
        RSTICK, LSTICK, BUTTON
    }
}