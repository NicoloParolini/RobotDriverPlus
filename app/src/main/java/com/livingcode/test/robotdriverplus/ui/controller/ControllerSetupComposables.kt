package com.livingcode.test.robotdriverplus.ui.controller

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import com.livingcode.test.robotdriverplus.domain.configuration.ControllerButtons
import com.livingcode.test.robotdriverplus.ui.devices.RobotViewModel
import com.livingcode.test.robotdriverplus.ui.models.Robot
import com.livingcode.test.robotdriverplus.ui.theme.defaultPadding
import com.livingcode.test.robotdriverplus.ui.theme.listElementName

@Composable
fun ControllerSetup(
    controlledRobots: List<ControlledRobotViewModel>,
    availableRobots: List<RobotViewModel>,
    controllerName: String,
    rightStick: JoystickViewModel,
    leftStick: JoystickViewModel,
    selected: ControllerButtons,
    onSelect: (ControllerButtons) -> Unit,
    onNameChange: (String) -> Unit
) {
    Column(modifier = Modifier.fillMaxSize()) {
        Controller(
            name = controllerName,
            leftStick = leftStick,
            rightStick = rightStick,
            selected = selected,
            modifier = Modifier.weight(1f),
            onSelect = onSelect,
            onNameChange = onNameChange
        )
        RobotSelector(
            robots = controlledRobots
        )
        ControllerRobotSelector(robots = availableRobots)
    }
}

@Composable
@Preview
fun ControllerSetupPreview() {
    ControllerSetup(
        controlledRobots = listOf(
            ControlledRobotViewModel(Robot(name = "NXT-1", connected = false, macAddress = "")) { _, _, _ -> },
            ControlledRobotViewModel(Robot(name = "NXT-1", connected = false, macAddress = "")) { _, _, _ -> }
        ),
        controllerName = "Blue",
        rightStick = JoystickViewModel("RIGHT STICK", {}),
        leftStick = JoystickViewModel("LEFT STICK", {}),
        selected = ControllerButtons.NONE,
        onSelect = {},
        availableRobots = listOf(
            RobotViewModel(
                onClick = {},
                resources = LocalContext.current.resources,
                device = Robot(name = "NXT-1", connected = false, macAddress = "")
            ),
            RobotViewModel(
                onClick = {},
                resources = LocalContext.current.resources,
                device = Robot(name = "NXT-2", connected = false, macAddress = "")
            )
        ),
        onNameChange = {}
    )
}

@Composable
fun ControllerSetupRoot(vm: ControllerSetupViewModel?) {
    vm?.let {
        val robots by it.robots.flow.collectAsState()
        val selected by it.selected.flow.collectAsState()
        val availableRobots by it.availableRobots.flow.collectAsState()
        val controller by it.localController.flow.collectAsState()
        ControllerSetup(
            controlledRobots = robots,
            controllerName = controller.name,
            rightStick = it.rightStick,
            leftStick = it.leftStick,
            selected = selected,
            onSelect = { button -> it.onSelectControl(button) },
            availableRobots = availableRobots,
            onNameChange = { new -> it.changeControllerName(new) }
        )
    }
}

@Composable
fun ControllerRobotSelector(robots: List<RobotViewModel>) {
    LazyRow {
        items(robots) { robot ->
            Text(text = robot.displayName, modifier = Modifier
                .clickable {
                    robot.onClick(robot.device as Robot)
                }
                .padding(defaultPadding),
            style = listElementName)
        }
    }
}