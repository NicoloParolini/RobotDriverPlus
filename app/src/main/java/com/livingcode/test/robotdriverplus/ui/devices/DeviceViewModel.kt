package com.livingcode.test.robotdriverplus.ui.devices

import android.content.res.Resources
import com.livingcode.test.robotdriverplus.R
import com.livingcode.test.robotdriverplus.ui.models.Controller
import com.livingcode.test.robotdriverplus.ui.models.Device
import com.livingcode.test.robotdriverplus.ui.models.Robot

sealed interface DeviceViewModel {
    val device: Device
    val resources: Resources
    val displayName: String
}

data class ControllerViewModel(
    val robots: List<Robot>,
    override val device: Device,
    override val resources: Resources
) : DeviceViewModel {
    override val displayName = resources.getString(R.string.controllerName, device.name)
}

data class RobotViewModel(
    val controller : Controller? = null,
    override val device: Device,
    val onClick: (Robot) -> Unit,
    override val resources: Resources
) : DeviceViewModel {
    override val displayName = resources.getString(R.string.robotName, device.name)
}