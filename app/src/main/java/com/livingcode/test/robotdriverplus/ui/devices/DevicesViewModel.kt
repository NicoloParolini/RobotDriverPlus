package com.livingcode.test.robotdriverplus.ui.devices

import android.content.res.Resources
import com.livingcode.test.robotdriverplus.StateFlowContainer
import com.livingcode.test.robotdriverplus.domain.controller.ControllerListener
import com.livingcode.test.robotdriverplus.domain.controller.ControllerStorage
import com.livingcode.test.robotdriverplus.models.Controller
import com.livingcode.test.robotdriverplus.models.Robot
import com.livingcode.test.robotdriverplus.ui.navigation.FlowResult
import com.livingcode.test.robotdriverplus.ui.navigation.FlowViewModel

class DevicesViewModel(
    private val resources: Resources,
    private val controllerStorage: ControllerStorage
) : FlowViewModel() {
    val devices: StateFlowContainer<List<DeviceViewModel>> = StateFlowContainer(listOf())

    init {
        getDevices()
    }

    fun onDeviceSelected(device: DeviceViewModel) {
        if (device is ControllerViewModel)
            next(FlowResult.RESULT_CONTROLLER_SELECTED, device.device.name)
    }

    private fun getDevices() {
        devices.setValue(
            controllerStorage.getControllers().map {
                ControllerViewModel(
                    robots = it.robots,
                    device = Controller(it.name, connected = true),
                    resources = resources
                )
            }
        )
    }
}