package com.livingcode.test.robotdriverplus.domain.controller

import android.view.InputDevice
import android.view.KeyEvent
import android.view.MotionEvent
import com.livingcode.test.robotdriverplus.domain.configuration.ControllerButtons

class ControllerHandler {
    private fun getCenteredAxis(
        event: MotionEvent,
        device: InputDevice,
        axis: Int,
        historyPos: Int
    ): Float {
        val range: InputDevice.MotionRange? = device.getMotionRange(axis, event.source)

        // A joystick at rest does not always report an absolute position of
        // (0,0). Use the getFlat() method to determine the range of values
        // bounding the joystick axis center.
        range?.apply {
            val value: Float = if (historyPos < 0) {
                event.getAxisValue(axis)
            } else {
                event.getHistoricalAxisValue(axis, historyPos)
            }

            // Ignore axis values that are within the 'flat' region of the
            // joystick axis center.
            if (Math.abs(value) > flat) {
                return value
            }
        }
        return 0f
    }

    fun getJoystickCommand(event : MotionEvent, historyPos: Int) : Pair<ControllerButtons, ControllerButtons> {
        val inputDevice = event.device

        val leftJUpDown = getCenteredAxis(event, inputDevice, MotionEvent.AXIS_Y, historyPos)
        val leftJLR = getCenteredAxis(event, inputDevice, MotionEvent.AXIS_X, historyPos)
        val rightJUpDown = getCenteredAxis(event, inputDevice, MotionEvent.AXIS_RZ, historyPos)
        val rightJLR = getCenteredAxis(event, inputDevice, MotionEvent.AXIS_Z, historyPos)

        val left = when {
            leftJUpDown > 0 -> ControllerButtons.LSTICK_DOWN
            leftJUpDown < 0 -> ControllerButtons.LSTICK_UP
            leftJLR < 0 -> ControllerButtons.LSTICK_LEFT
            leftJLR > 0 -> ControllerButtons.LSTICK_RIGHT
            else -> ControllerButtons.LSTICK_OFF
        }

        val right = when {
            rightJUpDown > 0 -> ControllerButtons.RSTICK_DOWN
            rightJUpDown < 0 -> ControllerButtons.RSTICK_UP
            rightJLR < 0 -> ControllerButtons.RSTICK_LEFT
            rightJLR > 0 -> ControllerButtons.RSTICK_RIGHT
            else -> ControllerButtons.RSTICK_OFF
        }
        return Pair(left, right)
    }

    fun getButtonCommand(event : KeyEvent, press : Boolean) : ControllerButtons {
        return when (event.keyCode) {
            KeyEvent.KEYCODE_BUTTON_L1 -> if (press) ControllerButtons.L1_DOWN else ControllerButtons.L1_UP
            KeyEvent.KEYCODE_BUTTON_R1 -> if (press) ControllerButtons.R1_DOWN else ControllerButtons.R1_UP
            KeyEvent.KEYCODE_BUTTON_L2 -> if (press) ControllerButtons.L2_DOWN else ControllerButtons.L2_UP
            KeyEvent.KEYCODE_BUTTON_R2 -> if (press) ControllerButtons.R2_DOWN else ControllerButtons.R2_UP
            else -> ControllerButtons.NONE
        }
    }
}