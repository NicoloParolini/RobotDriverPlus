package com.livingcode.test.robotdriverplus.ui.controller

import com.livingcode.test.robotdriverplus.domain.robot.Motors
import com.livingcode.test.robotdriverplus.ui.models.Robot

class ControlledRobotViewModel(
    val robot : Robot,
    val onSelect: (Robot, Motors, MotorSelectorViewModel.MotorDirection) -> Unit
) {
    val motors =
        Motors.values().map { motorEnum ->
            motorEnum to MotorSelectorViewModel(motorEnum.id) { direction ->
                onSelect(
                    robot,
                    motorEnum,
                    direction
                )
            }
        }.toList()
}