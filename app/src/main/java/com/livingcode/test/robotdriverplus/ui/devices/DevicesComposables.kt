package com.livingcode.test.robotdriverplus.ui.devices

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.livingcode.test.robotdriverplus.R
import com.livingcode.test.robotdriverplus.models.Controller
import com.livingcode.test.robotdriverplus.models.Robot
import com.livingcode.test.robotdriverplus.ui.shared.SingleLineListElement
import com.livingcode.test.robotdriverplus.ui.theme.defaultPadding
import com.livingcode.test.robotdriverplus.ui.theme.listElementName
import org.koin.androidx.compose.get

@Composable
fun Devices(
    devices: List<DeviceViewModel>,
    modifier: Modifier = Modifier,
    onSelected: (String) -> Unit
) {
    LazyColumn(modifier = modifier, verticalArrangement = Arrangement.spacedBy(2.dp)) {
        items(devices) { vm ->
            Box(modifier = Modifier.fillMaxWidth()) {
                SingleLineListElement(
                    name = vm.displayName,
                    connected = vm.device.connected,
                    onClick = onSelected
                )
                when (vm) {
                    is ControllerViewModel -> {
                        Row(modifier = Modifier.align(Alignment.CenterEnd)) {
                            vm.robots.forEach { robot ->
                                Text(
                                    text = stringResource(id = R.string.robotName, robot.name),
                                    modifier = Modifier.padding(all = defaultPadding),
                                    style = listElementName
                                )
                            }
                        }
                    }
                    is RobotViewModel -> {
                        vm.controller?.let { controller ->
                            Text(
                                text = stringResource(
                                    id = R.string.controllerName,
                                    controller.name
                                ),
                                modifier = Modifier
                                    .padding(all = defaultPadding)
                                    .align(Alignment.CenterEnd),
                                style = listElementName
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun DevicesRoot(vm: DevicesViewModel?) {
    vm?.let {
        val devices by it.devices.flow.collectAsState()
        Devices(devices = devices, onSelected = { name -> it.onDeviceSelected(name) })
    }
}

@Composable
@Preview
fun ControllersPreview() {
    Devices(devices = listOf(
        ControllerViewModel(
            device = Controller(name = "Blue", connected = true),
            onClick = {},
            resources = LocalContext.current.resources,
            robots = listOf(Robot("NXT-3", true))
        ),
        ControllerViewModel(
            device = Controller(name = "Red", connected = false),
            onClick = {},
            resources = LocalContext.current.resources,
            robots = listOf()
        ),
        ControllerViewModel(
            device = Controller(name = "Gold", connected = true),
            onClick = {},
            resources = LocalContext.current.resources,
            robots = listOf(
                Robot("NXT-1", false),
                Robot("NXT-2", true)
            )
        ),
        ControllerViewModel(
            device = Controller(name = "Silver", connected = true),
            onClick = {},
            resources = LocalContext.current.resources,
            robots = listOf()
        ),
        RobotViewModel(
            device = Robot(name = "NXT-1", connected = true),
            onClick = {},
            resources = LocalContext.current.resources
        ),
        RobotViewModel(
            device = Robot(name = "NXT-2", connected = false),
            onClick = {},
            resources = LocalContext.current.resources,
            controller = Controller("Gold", false)
        ),
    ), onSelected = {})
}