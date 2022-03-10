package com.livingcode.test.robotdriverplus.ui.controller

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun ControllerSetup(
    robots: List<ControlledRobotViewModel>
) {
    Box(modifier = Modifier.fillMaxSize()) {
        RobotSelector(
            robots = robots,
            modifier = Modifier.align(Alignment.BottomStart)
        )
    }
}

@Composable
@Preview
fun ControllerSetupPreview() {
    ControllerSetup(
        robots = listOf(
            ControlledRobotViewModel("NXT-1"){ _, _, _ -> },
            ControlledRobotViewModel("NXT-2"){ _, _, _ -> }
        )
    )
}

@Composable
fun ControllerSetupRoot(vm : ControllerSetupViewModel?) {
    vm?.let{
        val robots by it.robots.flow.collectAsState()
        ControllerSetup(robots = robots)
    }
}