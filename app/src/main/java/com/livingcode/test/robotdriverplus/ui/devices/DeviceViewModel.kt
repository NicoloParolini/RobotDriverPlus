package com.livingcode.test.robotdriverplus.ui.devices

import android.content.res.Resources
import com.livingcode.test.robotdriverplus.R
import com.livingcode.test.robotdriverplus.models.Controller
import com.livingcode.test.robotdriverplus.models.Device
import com.livingcode.test.robotdriverplus.models.Robot

sealed interface DeviceViewModel {
    val device: Device
    val resources: Resources
    val displayName: String
}

class ControllerViewModel(
    val robots: List<Robot>,
    override val device: Device,
    override val resources: Resources
) : DeviceViewModel {
    override val displayName = resources.getString(R.string.controllerName, device.name)
}

class RobotViewModel(
    val controller : Controller? = null,
    override val device: Device,
    val onClick: (String) -> Unit,
    override val resources: Resources
) : DeviceViewModel {
    override val displayName = resources.getString(R.string.robotName, device.name)
}