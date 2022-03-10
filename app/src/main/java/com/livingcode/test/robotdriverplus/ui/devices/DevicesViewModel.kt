package com.livingcode.test.robotdriverplus.ui.devices

import android.content.res.Resources
import com.livingcode.test.robotdriverplus.StateFlowContainer
import com.livingcode.test.robotdriverplus.models.Controller
import com.livingcode.test.robotdriverplus.models.Robot
import com.livingcode.test.robotdriverplus.ui.navigation.FlowResult
import com.livingcode.test.robotdriverplus.ui.navigation.FlowViewModel

class DevicesViewModel(private val resources : Resources) : FlowViewModel() {
    val devices : StateFlowContainer<List<DeviceViewModel>> = StateFlowContainer(listOf())

    init {
        getDevices()
    }

    fun onDeviceSelected(deviceName : String){
        next(FlowResult.RESULT_CONTROLLER_SELECTED, deviceName)
    }

    private fun getDevices() {
        devices.setValue(listOf(
            ControllerViewModel(
                device = Controller(name = "Blue", connected = true),
                onClick = {},
                resources = resources,
                robots = listOf(Robot("NXT-3", true))
            ),
            ControllerViewModel(
                device = Controller(name = "Red", connected = false),
                onClick = {},
                resources = resources,
                robots = listOf()
            ),
            ControllerViewModel(
                device = Controller(name = "Gold", connected = true),
                onClick = {},
                resources = resources,
                robots = listOf(
                    Robot("NXT-1", false),
                    Robot("NXT-2", true)
                )
            ),
            ControllerViewModel(
                device = Controller(name = "Silver", connected = true),
                onClick = {},
                resources = resources,
                robots = listOf()
            ),
            RobotViewModel(
                device = Robot(name = "NXT-1", connected = true),
                onClick = {},
                resources = resources
            ),
            RobotViewModel(
                device = Robot(name = "NXT-2", connected = false),
                onClick = {},
                resources = resources,
                controller = Controller("Gold", false)
            ),
        ))
    }
}