package com.livingcode.test.robotdriverplus.ui.splash

import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionState
import com.livingcode.test.robotdriverplus.StateFlowContainer
import com.livingcode.test.robotdriverplus.ui.navigation.FlowResult
import com.livingcode.test.robotdriverplus.ui.navigation.FlowViewModel

class SplashViewModel : FlowViewModel() {
    val permissionRequest : StateFlowContainer<String> = StateFlowContainer("This app requires Bluetooth permission to work")

    fun onPermissionGranted() {
        permissionRequest.setValue("Welcome to Robot Driver Plus")
        next(FlowResult.RESULT_OK)
    }
}