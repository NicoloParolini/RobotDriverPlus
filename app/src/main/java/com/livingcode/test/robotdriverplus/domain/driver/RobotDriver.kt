package com.livingcode.test.robotdriverplus.domain.driver

import com.livingcode.test.robotdriverplus.domain.configuration.ControllerButtons
import timber.log.Timber

class RobotDriver {
    private var lastRStickCmd: ControllerButtons = ControllerButtons.NONE
    private var lastLStickCmd: ControllerButtons = ControllerButtons.NONE
    fun processCommand(cmd: ControllerButtons, id: Int, type: CommandType) {
        if (cmd != lastLStickCmd && cmd != lastRStickCmd) {
            Timber.v("Received command $cmd from controller $id")
            when (type) {
                CommandType.RSTICK -> lastRStickCmd = cmd
                CommandType.LSTICK -> lastLStickCmd = cmd
                CommandType.BUTTON -> {
                }
            }
        }
    }

    enum class CommandType {
        RSTICK, LSTICK, BUTTON
    }
}