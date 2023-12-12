package com.livingcode.test.robotdriverplus.ui.navigation

import com.livingcode.test.robotdriverplus.ui.controller.ControllerSetupViewModel
import com.livingcode.test.robotdriverplus.ui.devices.DevicesViewModel
import com.livingcode.test.robotdriverplus.ui.models.Controller
import com.livingcode.test.robotdriverplus.ui.splash.SplashViewModel
import org.koin.core.component.KoinComponent
import org.koin.core.component.get

class MainCoordinator(navigator: Navigator) : FlowCoordinator(), KoinComponent {

    private val controllerSetupStep = FlowStep().setAction { result ->
        (result.payload as? Controller)?.let { name ->
            val vm = ControllerSetupViewModel(name, get(), get(), get(), get())
            navigator.navigate(Screen(Screens.SCREEN_CONTROLLER_SETUP, vm))
            return@setAction vm.proceed()
        } ?: FlowResult.RESULT_MISSING_PAYLOAD
    }

    private val deviceListStep = FlowStep().useDefaultAction(
        viewModelConstructor = { DevicesViewModel(get(), get(), get(), get()) },
        navigator = navigator,
        screen = Screens.SCREEN_DEVICE_LIST
    ).addNextStep(FlowResult.RESULT_CONTROLLER_SELECTED, controllerSetupStep)

    private val splashStep = FlowStep().useDefaultAction(
        viewModelConstructor = { SplashViewModel() },
        navigator = navigator,
        screen = Screens.SCREEN_SPLASH
    ).addNextStep(FlowResult.RESULT_OK, deviceListStep)

    override val startStep: FlowStep = splashStep
}