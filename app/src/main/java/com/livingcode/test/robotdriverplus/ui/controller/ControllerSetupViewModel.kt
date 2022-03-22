package com.livingcode.test.robotdriverplus.ui.controller

import android.content.res.Resources
import androidx.lifecycle.viewModelScope
import com.livingcode.test.robotdriverplus.StateFlowContainer
import com.livingcode.test.robotdriverplus.domain.configuration.*
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

    private val configuration: Configuration?
        get() = configurator.getConfiguration(controller = controller.descriptor)

    init {
        getRobots()
    }

    fun onSelectControl(button: ControllerButtons) {
        selected.setValue(button)
        configuration?.commands?.get(button)?.let { cmd ->
            robots.flow.value.forEach { robotVm ->
                robotVm.motors.forEach { it.second.position.setValue(MotorSelectorViewModel.MotorDirection.MOTOR_UNUSED) }
            }
            robots.flow.value.forEach { robotVm ->
                cmd.keys.forEach { motorId ->
                    if (motorId.robot == robotVm.robot.macAddress) {
                        robotVm.motors.forEach { inst ->
                            if (inst.first.id == motorId.motor.id) {
                                cmd[motorId]?.toMotorDirection()?.let { dir ->
                                    inst.second.position.setValue(dir)
                                }
                            }
                        }
                    }
                }
            }
        }
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
                // Add stop commands to motors controlled by stick or L/R button
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
                    ControllerButtons.L1_DOWN -> configurator.addCommand(
                        controller = controller,
                        button = ControllerButtons.L1_UP,
                        robot = robot,
                        motor = motor,
                        dir = MotorCommand.BRAKE
                    )
                    ControllerButtons.L2_DOWN -> configurator.addCommand(
                        controller = controller,
                        button = ControllerButtons.L2_UP,
                        robot = robot,
                        motor = motor,
                        dir = MotorCommand.BRAKE
                    )
                    ControllerButtons.R1_DOWN -> configurator.addCommand(
                        controller = controller,
                        button = ControllerButtons.R1_UP,
                        robot = robot,
                        motor = motor,
                        dir = MotorCommand.BRAKE
                    )
                    ControllerButtons.R2_DOWN -> configurator.addCommand(
                        controller = controller,
                        button = ControllerButtons.R2_UP,
                        robot = robot,
                        motor = motor,
                        dir = MotorCommand.BRAKE
                    )
                    else -> { /*not needed*/
                    }
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

    private fun MotorCommand.toMotorDirection(): MotorSelectorViewModel.MotorDirection {
        return when (this) {
            MotorCommand.FWD -> MotorSelectorViewModel.MotorDirection.MOTOR_FWD
            MotorCommand.COAST -> MotorSelectorViewModel.MotorDirection.MOTOR_COAST
            MotorCommand.BRAKE -> MotorSelectorViewModel.MotorDirection.MOTOR_BRAKE
            MotorCommand.BACK -> MotorSelectorViewModel.MotorDirection.MOTOR_BCK
            MotorCommand.NONE -> MotorSelectorViewModel.MotorDirection.MOTOR_UNUSED
        }
    }
}