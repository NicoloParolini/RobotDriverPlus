package com.livingcode.test.robotdriverplus.ui

import android.hardware.input.InputManager
import android.view.KeyEvent
import android.view.MotionEvent
import androidx.lifecycle.ViewModel
import com.livingcode.test.robotdriverplus.domain.controller.ControllerHandler
import com.livingcode.test.robotdriverplus.domain.driver.RobotDriver
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class MainViewModel : ViewModel(), KoinComponent {
    private val robotDriver: RobotDriver by inject()
    private val inputManager : InputManager by inject()
    private val controllerHandler = ControllerHandler()

    fun onKeyEvent(event: KeyEvent, press : Boolean) {
        robotDriver.processCommand(controllerHandler.getButtonCommand(event, press), inputManager.getInputDevice(event.deviceId).descriptor, RobotDriver.CommandType.BUTTON)
    }

    fun onJoystickEvent(event: MotionEvent, historyPos: Int) {
        val command = controllerHandler.getJoystickCommand(event, historyPos)
        robotDriver.processCommand(command.first, inputManager.getInputDevice(event.deviceId).descriptor, RobotDriver.CommandType.LSTICK)
        robotDriver.processCommand(command.second, inputManager.getInputDevice(event.deviceId).descriptor, RobotDriver.CommandType.RSTICK)
    }
}