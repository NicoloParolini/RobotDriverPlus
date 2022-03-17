package com.livingcode.test.robotdriverplus.ui.devices

import android.content.res.Resources
import com.livingcode.test.robotdriverplus.StateFlowContainer
import com.livingcode.test.robotdriverplus.domain.controller.ControllerStorage
import com.livingcode.test.robotdriverplus.domain.robot.RobotConnector
import com.livingcode.test.robotdriverplus.domain.robot.RobotStorage
import com.livingcode.test.robotdriverplus.ui.models.Controller
import com.livingcode.test.robotdriverplus.ui.models.DomainResult
import com.livingcode.test.robotdriverplus.ui.models.Errors
import com.livingcode.test.robotdriverplus.ui.models.Robot
import com.livingcode.test.robotdriverplus.ui.navigation.FlowResult
import com.livingcode.test.robotdriverplus.ui.navigation.FlowViewModel
import timber.log.Timber

class DevicesViewModel(
    private val resources: Resources,
    private val controllerStorage: ControllerStorage,
    private val robotConnector: RobotConnector,
    private val robotStorage: RobotStorage
) : FlowViewModel() {
    val devices: StateFlowContainer<List<DeviceViewModel>> = StateFlowContainer(listOf())

    init {
        getDevices()
    }

    fun onDeviceSelected(device: DeviceViewModel) {
        if (device is ControllerViewModel)
            next(FlowResult.RESULT_CONTROLLER_SELECTED, device.device)
        if (device is RobotViewModel)
            device.onClick(device.device as Robot)
    }

    private fun getDevices() {
        val controllers = controllerStorage.getControllers().map {
            ControllerViewModel(
                robots = it.robots,
                device = it,
                resources = resources
            )
        }
        val robots = robotStorage.getRobots().map {
            RobotViewModel(
                device = it,
                resources = resources,
                onClick = { robot ->
                    if (robot.connected) disconnectRobot(robot) else connectRobot(
                        robot
                    )
                }
            )
        }
        devices.setValue(
            controllers + robots
        )
    }

    private fun connectRobot(robot: Robot) {
        Timber.v("Connecting robot ${robot.name}")
        val result = robotConnector.connectRobot(robot)
        processResult(result)
    }

    private fun disconnectRobot(robot: Robot) {
        Timber.v("Disconnecting robot ${robot.name}")
        val result = robotConnector.disconnectRobot(robot)
        processResult(result)
    }

    private fun processResult(result: DomainResult<Robot>) {
        result.data?.let { robot ->
            val robots = devices.flow.value.map { dev ->
                (dev as? RobotViewModel)?.let { robotVm ->
                    if (dev.device.name == robot.name) robotVm.copy(device = robot)
                    else null
                }
                    ?: dev
            }
            devices.setValue(robots)
        }
    }
}