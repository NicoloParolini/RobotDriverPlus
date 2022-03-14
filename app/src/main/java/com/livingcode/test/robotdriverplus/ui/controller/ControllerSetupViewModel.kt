package com.livingcode.test.robotdriverplus.ui.controller

import android.content.res.Resources
import androidx.lifecycle.viewModelScope
import com.livingcode.test.robotdriverplus.StateFlowContainer
import com.livingcode.test.robotdriverplus.domain.configuration.Configurator
import com.livingcode.test.robotdriverplus.domain.configuration.ControllerButtons
import com.livingcode.test.robotdriverplus.domain.configuration.MotorCommand
import com.livingcode.test.robotdriverplus.models.Robot
import com.livingcode.test.robotdriverplus.ui.devices.RobotViewModel
import com.livingcode.test.robotdriverplus.ui.navigation.FlowViewModel
import kotlinx.coroutines.launch
import timber.log.Timber

class ControllerSetupViewModel(
    val name: String,
    val resources: Resources,
    val configurator: Configurator
) : FlowViewModel() {
    val robots: StateFlowContainer<List<ControlledRobotViewModel>> = StateFlowContainer(listOf())
    val rightStick = JoystickViewModel("RIGHT STICK") { onSelectControl(it.toButton(false)) }
    val leftStick = JoystickViewModel("LEFT STICK") { onSelectControl(it.toButton(true)) }
    val selected = StateFlowContainer(ControllerButtons.NONE)
    val availableRobots: StateFlowContainer<List<RobotViewModel>> = StateFlowContainer(listOf())

    init {
        getRobots()
    }

    fun onSelectControl(button: ControllerButtons) {
        selected.setValue(button)
    }

    private fun onSelectRobot(
        robot: String,
        motor: String,
        direction: MotorSelectorViewModel.MotorDirection
    ) {
        if(selected.flow.value != ControllerButtons.NONE) {
            Timber.v("Robot $robot motor $motor direction $direction assigned to controller $name ${selected.flow.value}")
            viewModelScope.launch {
                configurator.addCommand(
                    controller = name,
                    button = selected.flow.value,
                    robot = robot,
                    motor = motor,
                    dir = direction.toMotorCommand()
                )
            }
        }
    }

    private fun getRobots() {
        availableRobots.setValue(
            listOf(
                RobotViewModel(device = Robot(name = "NXT-1"), resources = resources, onClick = {
                    addRobot(
                        ControlledRobotViewModel(
                            label = it,
                            onSelect = { robot, motor, direction ->
                                onSelectRobot(
                                    robot,
                                    motor,
                                    direction
                                )
                            })
                    )
                }),
                RobotViewModel(device = Robot(name = "NXT-2"), resources = resources, onClick = {
                    addRobot(
                        ControlledRobotViewModel(
                            label = it,
                            onSelect = { robot, motor, direction ->
                                onSelectRobot(
                                    robot,
                                    motor,
                                    direction
                                )
                            })
                    )
                }),
                RobotViewModel(device = Robot(name = "NXT-3"), resources = resources, onClick = {
                    addRobot(
                        ControlledRobotViewModel(
                            label = it,
                            onSelect = { robot, motor, direction ->
                                onSelectRobot(
                                    robot,
                                    motor,
                                    direction
                                )
                            })
                    )
                }),
                RobotViewModel(device = Robot(name = "NXT-4"), resources = resources, onClick = {
                    addRobot(
                        ControlledRobotViewModel(
                            label = it,
                            onSelect = { robot, motor, direction ->
                                onSelectRobot(
                                    robot,
                                    motor,
                                    direction
                                )
                            })
                    )
                })
            )
        )
    }

    private fun addRobot(robot: ControlledRobotViewModel) {
        val addedRobots = robots.flow.value.toMutableList()
        addedRobots.find { it.label == robot.label }?.let { addedRobots.remove(it) }
            ?: addedRobots.add(robot)
        robots.setValue(addedRobots.toList())
    }

    private fun JoystickViewModel.Directions.toButton(left: Boolean): ControllerButtons {
        return when (this) {
            JoystickViewModel.Directions.UP -> if (left) ControllerButtons.LSTICK_UP else ControllerButtons.RSTICK_UP
            JoystickViewModel.Directions.DOWN -> if (left) ControllerButtons.LSTICK_DOWN else ControllerButtons.RSTICK_DOWN
            JoystickViewModel.Directions.LEFT -> if (left) ControllerButtons.LSTICK_LEFT else ControllerButtons.RSTICK_LEFT
            JoystickViewModel.Directions.RIGHT -> if (left) ControllerButtons.LSTICK_RIGHT else ControllerButtons.RSTICK_RIGHT
            JoystickViewModel.Directions.UNUSED -> ControllerButtons.NONE
        }
    }

    private fun MotorSelectorViewModel.MotorDirection.toMotorCommand(): MotorCommand {
        return when (this) {
            MotorSelectorViewModel.MotorDirection.MOTOR_FWD -> MotorCommand.FWD
            MotorSelectorViewModel.MotorDirection.MOTOR_COAST -> MotorCommand.COAST
            MotorSelectorViewModel.MotorDirection.MOTOR_BRAKE -> MotorCommand.BRAKE
            MotorSelectorViewModel.MotorDirection.MOTOR_BCK -> MotorCommand.BACK
            MotorSelectorViewModel.MotorDirection.MOTOR_UNUSED -> MotorCommand.NONE
        }
    }
}