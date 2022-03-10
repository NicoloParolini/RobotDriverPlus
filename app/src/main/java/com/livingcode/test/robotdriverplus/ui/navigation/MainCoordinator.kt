package com.livingcode.test.robotdriverplus.ui.navigation

import com.livingcode.test.robotdriverplus.ui.devices.DevicesViewModel
import org.koin.core.component.KoinComponent
import org.koin.core.component.get

class MainCoordinator(navigator: Navigator) : FlowCoordinator(), KoinComponent {
    private val deviceListStep = FlowStep().useDefaultAction(
        viewModelConstructor = { DevicesViewModel(get()) },
        navigator = navigator,
        screen = Screens.SCREEN_DEVICE_LIST
    )

    override val startStep: FlowStep = deviceListStep
}