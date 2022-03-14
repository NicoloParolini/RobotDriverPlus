package com.livingcode.test.robotdriverplus.ui

import android.view.KeyEvent
import android.view.MotionEvent
import androidx.lifecycle.ViewModel
import com.livingcode.test.robotdriverplus.domain.controller.ControllerHandler
import com.livingcode.test.robotdriverplus.domain.driver.RobotDriver
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class MainViewModel : ViewModel(), KoinComponent {
    private val robotDriver: RobotDriver by inject()
    private val controllerHandler = ControllerHandler()

    fun onKeyEvent(event: KeyEvent) {
        robotDriver.processCommand(controllerHandler.getButtonCommand(event), event.deviceId, RobotDriver.CommandType.BUTTON)
    }

    fun onJoystickEvent(event: MotionEvent, historyPos: Int) {
        val command = controllerHandler.getJoystickCommand(event, historyPos)
        robotDriver.processCommand(command.first, event.deviceId, RobotDriver.CommandType.LSTICK)
        robotDriver.processCommand(command.second, event.deviceId, RobotDriver.CommandType.RSTICK)
    }
}