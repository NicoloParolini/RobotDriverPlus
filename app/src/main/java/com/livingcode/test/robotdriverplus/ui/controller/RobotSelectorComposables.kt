package com.livingcode.test.robotdriverplus.ui.controller

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.livingcode.test.robotdriverplus.ui.theme.motorSelectorLabel

@Composable
fun RobotMotors(
    modifier: Modifier = Modifier,
    label: String,
    motors: List<MotorSelectorViewModel>
) {
    Box(modifier = modifier.border(width = 2.dp, color = Color.Black)) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text = label, style = motorSelectorLabel)
            MotorSelector(
                motors = motors
            )
        }
    }
}

@Composable
@Preview
fun RobotMotorsPreview() {
    RobotMotors(
        label = "NXT-1", motors = listOf(
            MotorSelectorViewModel("A") { _, _ -> },
            MotorSelectorViewModel("B") { _, _ -> },
            MotorSelectorViewModel("C") { _, _ -> }
        )
    )
}

@Composable
fun RobotSelector(
    robots: List<ControlledRobotViewModel>,
    modifier: Modifier = Modifier
) {
    LazyRow(modifier = modifier) {
        items(robots) { robot ->
            RobotMotors(
                label = robot.label,
                motors = robot.motors.values.toList()
            )
        }
    }
}

@Composable
@Preview
fun RobotSelectorPreview() {
    RobotSelector(robots = listOf(
        ControlledRobotViewModel("NXT-1") { _, _, _ -> },
        ControlledRobotViewModel("NXT-2") { _, _, _ -> }
    ))
}