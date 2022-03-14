package com.livingcode.test.robotdriverplus.ui.navigation

import com.livingcode.test.robotdriverplus.ui.controller.ControllerSetupViewModel
import com.livingcode.test.robotdriverplus.ui.devices.DevicesViewModel
import org.koin.core.component.KoinComponent
import org.koin.core.component.get

class MainCoordinator(navigator: Navigator) : FlowCoordinator(), KoinComponent {

    private val controllerSetupStep = FlowStep().setAction { result ->
        (result.payload as? String)?.let { name ->
            val vm = ControllerSetupViewModel(name, get(), get())
            navigator.navigate(Screen(Screens.SCREEN_CONTROLLER_SETUP, vm))
            return@setAction vm.proceed()
        } ?: FlowResult.RESULT_MISSING_PAYLOAD
    }

    private val deviceListStep = FlowStep().useDefaultAction(
        viewModelConstructor = { DevicesViewModel(get(), get()) },
        navigator = navigator,
        screen = Screens.SCREEN_DEVICE_LIST
    ).addNextStep(FlowResult.RESULT_CONTROLLER_SELECTED, controllerSetupStep)

    override val startStep: FlowStep = deviceListStep
}