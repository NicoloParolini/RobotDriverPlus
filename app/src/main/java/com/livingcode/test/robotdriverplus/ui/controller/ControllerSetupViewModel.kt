package com.livingcode.test.robotdriverplus.ui.controller

import android.content.res.Resources
import androidx.lifecycle.viewModelScope
import com.livingcode.test.robotdriverplus.StateFlowContainer
import com.livingcode.test.robotdriverplus.domain.configuration.Configurator
import com.livingcode.test.robotdriverplus.domain.configuration.ControllerButtons
import com.livingcode.test.robotdriverplus.domain.configuration.MotorCommand
import com.livingcode.test.robotdriverplus.domain.robot.Motors
import com.livingcode.test.robotdriverplus.domain.robot.RobotStorage
import com.livingcode.test.robotdriverplus.ui.models.Robot
import com.livingcode.test.robotdriverplus.ui.devices.RobotViewModel
import com.livingcode.test.robotdriverplus.ui.models.Controller
import com.livingcode.test.robotdriverplus.ui.navigation.FlowViewModel
import kotlinx.coroutines.launch
import timber.log.Timber

class ControllerSetupViewModel(
    val controller: Controller,
    val resources: Resources,
    private val configurator: Configurator,
    private val robotStorage: RobotStorage
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
        robot: Robot,
        motor: Motors,
        direction: MotorSelectorViewModel.MotorDirection
    ) {
        val button = selected.flow.value
        if (button != ControllerButtons.NONE) {
            Timber.v("Robot $robot motor $motor direction $direction assigned to controller ${controller.descriptor} ${button}")
            viewModelScope.launch {
                configurator.addCommand(
                    controller = controller,
                    button = button,
                    robot = robot,
                    motor = motor,
                    dir = direction.toMotorCommand()
                )
                // Add stop commands to motors controlled by stick
                when (button) {
                    ControllerButtons.LSTICK_UP,
                    ControllerButtons.LSTICK_DOWN,
                    ControllerButtons.LSTICK_LEFT,
                    ControllerButtons.LSTICK_RIGHT -> configurator.addCommand(
                        controller = controller,
                        button = ControllerButtons.LSTICK_OFF,
                        robot = robot,
                        motor = motor,
                        dir = MotorCommand.BRAKE
                    )
                    ControllerButtons.RSTICK_UP,
                    ControllerButtons.RSTICK_DOWN,
                    ControllerButtons.RSTICK_LEFT,
                    ControllerButtons.RSTICK_RIGHT -> configurator.addCommand(
                        controller = controller,
                        button = ControllerButtons.RSTICK_OFF,
                        robot = robot,
                        motor = motor,
                        dir = MotorCommand.BRAKE
                    )
                    else -> { /*not needed*/ }
                }
            }
        }
    }

    private fun Robot.toRobotViewModel(): RobotViewModel {
        return RobotViewModel(device = this, resources = resources, onClick = {
            addRobot(
                ControlledRobotViewModel(
                    robot = it,
                    onSelect = { robot, motor, direction ->
                        onSelectRobot(
                            robot,
                            motor,
                            direction
                        )
                    })
            )
        })
    }

    private fun getRobots() {
        availableRobots.setValue(
            robotStorage.getRobots().map { robot -> robot.toRobotViewModel() }
        )
    }

    private fun addRobot(robot: ControlledRobotViewModel) {
        val addedRobots = robots.flow.value.toMutableList()
        addedRobots.find { it.robot.macAddress == robot.robot.macAddress }
            ?.let { addedRobots.remove(it) }
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