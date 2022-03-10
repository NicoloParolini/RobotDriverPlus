package com.livingcode.test.robotdriverplus.ui.controller

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun ControllerSetup(
    robots: List<ControlledRobotViewModel>,
    controllerName: String,
    rightStick: JoystickViewModel,
    leftStick: JoystickViewModel,
    selected: ControllerViewModel.ControllerButtons
) {
    Column(modifier = Modifier.fillMaxSize()) {
        Controller(
            name = controllerName,
            leftStick = leftStick,
            rightStick = rightStick,
            selected = selected,
            modifier = Modifier.weight(1f)
        )
        RobotSelector(
            robots = robots
        )
    }
}

@Composable
@Preview
fun ControllerSetupPreview() {
    ControllerSetup(
        robots = listOf(
            ControlledRobotViewModel("NXT-1") { _, _, _ -> },
            ControlledRobotViewModel("NXT-2") { _, _, _ -> }
        ),
        controllerName = "Blue",
        rightStick = JoystickViewModel("RIGHT STICK", {}),
        leftStick = JoystickViewModel("LEFT STICK", {}),
        selected = ControllerViewModel.ControllerButtons.NONE
    )
}

@Composable
fun ControllerSetupRoot(vm: ControllerSetupViewModel?) {
    vm?.let {
        val robots by it.robots.flow.collectAsState()
        val selected by it.selected.flow.collectAsState()
        ControllerSetup(
            robots = robots,
            controllerName = it.name,
            rightStick = it.rightStick,
            leftStick = it.leftStick,
            selected = selected
        )
    }
}