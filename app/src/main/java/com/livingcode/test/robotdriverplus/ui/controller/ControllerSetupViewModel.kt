package com.livingcode.test.robotdriverplus.ui.controller

import com.livingcode.test.robotdriverplus.StateFlowContainer
import com.livingcode.test.robotdriverplus.ui.navigation.FlowViewModel
import timber.log.Timber

class ControllerSetupViewModel() : FlowViewModel() {
    val robots: StateFlowContainer<List<ControlledRobotViewModel>> = StateFlowContainer(listOf())

    init {
        getRobots()
    }

    private fun onSelect(robot : String, motor : String, direction : MotorSelectorViewModel.MotorDirection) {
        Timber.v("Robot $robot motor $motor direction $direction assigned to controller")
    }

    private fun getRobots() {
        robots.setValue(
            listOf(
                ControlledRobotViewModel("NXT-1") { robot, motor, direction ->
                    onSelect(robot, motor, direction)
                },
                ControlledRobotViewModel("NXT-2") { robot, motor, direction ->
                    onSelect(robot, motor, direction)
                },
                ControlledRobotViewModel("NXT-3") { robot, motor, direction ->
                    onSelect(robot, motor, direction)
                },
                ControlledRobotViewModel("NXT-4") { robot, motor, direction ->
                    onSelect(robot, motor, direction)
                }
            )
        )
    }
}