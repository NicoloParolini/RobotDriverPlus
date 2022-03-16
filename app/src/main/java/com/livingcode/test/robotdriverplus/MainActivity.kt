package com.livingcode.test.robotdriverplus

import android.annotation.SuppressLint
import android.hardware.input.InputManager
import android.os.Bundle
import android.view.InputDevice
import android.view.KeyEvent
import android.view.MotionEvent
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.lifecycleScope
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.livingcode.test.robotdriverplus.domain.controller.ControllerListener
import com.livingcode.test.robotdriverplus.ui.MainViewModel
import com.livingcode.test.robotdriverplus.ui.controller.ControllerSetup
import com.livingcode.test.robotdriverplus.ui.controller.ControllerSetupRoot
import com.livingcode.test.robotdriverplus.ui.controller.ControllerSetupViewModel
import com.livingcode.test.robotdriverplus.ui.devices.DevicesRoot
import com.livingcode.test.robotdriverplus.ui.devices.DevicesViewModel
import com.livingcode.test.robotdriverplus.ui.navigation.*
import com.livingcode.test.robotdriverplus.ui.splash.SplashRoot
import com.livingcode.test.robotdriverplus.ui.splash.SplashViewModel
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import timber.log.Timber

@ExperimentalPermissionsApi
class MainActivity : ComponentActivity() {
    private val backStack: FlowBackStack by inject()
    private val controllerListener : ControllerListener by inject()
    private val currentScreen: MutableState<Screen> =
        mutableStateOf(Screen(Screens.SCREEN_DEVICE_LIST, null))
    private val viewModel: MainViewModel by viewModels()

    private val navigator = object : Navigator {
        override fun navigate(destination: Screen) {
            currentScreen.value = destination
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        registerInputDeviceListener()
        setContent {
            BackHandler {
                backStack.back()
            }

            Surface(
                modifier = Modifier.fillMaxSize(),
                color = MaterialTheme.colors.background
            ) {
                MainRoot(destination = currentScreen)
            }

        }

        lifecycleScope.launch {
            MainCoordinator(navigator = navigator).startFlow(FlowResult.RESULT_OK)
            finish()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterInputDeviceListener()
    }

    override fun dispatchGenericMotionEvent(event: MotionEvent): Boolean {
        if (event.source and InputDevice.SOURCE_JOYSTICK == InputDevice.SOURCE_JOYSTICK
            && event.action == MotionEvent.ACTION_MOVE
        ) {
            (0 until event.historySize).forEach { i ->
                viewModel.onJoystickEvent(event, i)
            }
            viewModel.onJoystickEvent(event, -1)
        }
        return true
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        when(keyCode){
            KeyEvent.KEYCODE_BUTTON_L1,
            KeyEvent.KEYCODE_BUTTON_L2,
            KeyEvent.KEYCODE_BUTTON_R1,
            KeyEvent.KEYCODE_BUTTON_R2 -> {
                viewModel.onKeyEvent(event, true)
                return true
            }
        }
        return super.onKeyDown(keyCode, event)
    }

    override fun onKeyUp(keyCode: Int, event: KeyEvent): Boolean {
        when(keyCode){
            KeyEvent.KEYCODE_BUTTON_L1,
            KeyEvent.KEYCODE_BUTTON_L2,
            KeyEvent.KEYCODE_BUTTON_R1,
            KeyEvent.KEYCODE_BUTTON_R2 -> {
                viewModel.onKeyEvent(event, false)
                return true
            }
        }
        return super.onKeyUp(keyCode, event)
    }

    private fun registerInputDeviceListener() {
        val inputManager = getSystemService(InputManager::class.java)
        inputManager.registerInputDeviceListener(controllerListener, null)
    }

    private fun unregisterInputDeviceListener() {
        val inputManager = getSystemService(InputManager::class.java)
        inputManager.unregisterInputDeviceListener(controllerListener)
    }
}

@ExperimentalPermissionsApi
@Composable
fun MainRoot(destination: MutableState<Screen>) {
    when (destination.value.screen) {
        Screens.SCREEN_SPLASH -> SplashRoot(destination.value.viewModel as? SplashViewModel)
        Screens.SCREEN_DEVICE_LIST -> DevicesRoot(destination.value.viewModel as? DevicesViewModel)
        Screens.SCREEN_CONTROLLER_SETUP -> ControllerSetupRoot(vm = destination.value.viewModel as? ControllerSetupViewModel)
    }
}