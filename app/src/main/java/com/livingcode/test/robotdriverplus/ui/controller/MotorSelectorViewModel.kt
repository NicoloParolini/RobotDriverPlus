package com.livingcode.test.robotdriverplus.ui.controller

import com.livingcode.test.robotdriverplus.R
import com.livingcode.test.robotdriverplus.StateFlowContainer

class MotorSelectorViewModel(val label : String, private val onSelect : (String, MotorDirection) -> Unit) {
    val position = StateFlowContainer(MotorDirection.MOTOR_UNUSED)

    fun onSelect(direction: MotorDirection){
        position.setValue(direction)
        onSelect(label, direction)
    }

    enum class MotorDirection(val iconRes : Int) {
        MOTOR_FWD(iconRes = R.drawable.icon_arrow_up),
        MOTOR_COAST(iconRes = R.drawable.icon_switch),
        MOTOR_BRAKE(iconRes = R.drawable.icon_x),
        MOTOR_BCK(iconRes = R.drawable.icon_arrow_down),
        MOTOR_UNUSED(iconRes = R.drawable.icon_forbidden),
    }
}