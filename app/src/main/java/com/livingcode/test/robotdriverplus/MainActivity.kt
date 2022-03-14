package com.livingcode.test.robotdriverplus

import android.annotation.SuppressLint
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
import com.livingcode.test.robotdriverplus.ui.MainViewModel
import com.livingcode.test.robotdriverplus.ui.controller.ControllerSetup
import com.livingcode.test.robotdriverplus.ui.controller.ControllerSetupRoot
import com.livingcode.test.robotdriverplus.ui.controller.ControllerSetupViewModel
import com.livingcode.test.robotdriverplus.ui.devices.DevicesRoot
import com.livingcode.test.robotdriverplus.ui.devices.DevicesViewModel
import com.livingcode.test.robotdriverplus.ui.navigation.*
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import timber.log.Timber

class MainActivity : ComponentActivity() {
    private val backStack: FlowBackStack by inject()
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

    @SuppressLint("RestrictedApi")
    override fun dispatchKeyEvent(event: KeyEvent): Boolean {
        viewModel.onKeyEvent(event)
        return true
    }
}

@Composable
fun MainRoot(destination: MutableState<Screen>) {
    when (destination.value.screen) {
        Screens.SCREEN_DEVICE_LIST -> DevicesRoot(destination.value.viewModel as? DevicesViewModel)
        Screens.SCREEN_CONTROLLER_SETUP -> ControllerSetupRoot(vm = destination.value.viewModel as? ControllerSetupViewModel)
    }
}