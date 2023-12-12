package com.livingcode.test.robotdriverplus.ui.controller

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.livingcode.test.robotdriverplus.ui.theme.defaultPadding
import com.livingcode.test.robotdriverplus.ui.theme.motorSelectorIconSize
import com.livingcode.test.robotdriverplus.ui.theme.motorSelectorLabel

@Composable
fun MotorFunctionSelector(
    label: String,
    onSelect: (MotorSelectorViewModel.MotorDirection) -> Unit,
    position: MotorSelectorViewModel.MotorDirection,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.border(width = 1.dp, color = Color.Black),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = label, style = motorSelectorLabel)
        MotorSelectorViewModel.MotorDirection.values().forEach { direction ->
            Box(
                modifier = Modifier
                    .background(if (position == direction) Color.Green else Color.Transparent)
                    .clickable {
                        onSelect(direction)
                    }
            ) {
                Image(
                    painter = painterResource(id = direction.iconRes),
                    contentDescription = null,
                    modifier = Modifier
                        .size(
                            motorSelectorIconSize
                        )
                        .padding(defaultPadding)
                )
            }
        }
    }
}

@Composable
@Preview
fun MotorFunctionSelectorPreview() {
    MotorFunctionSelector(
        label = "A",
        onSelect = {},
        position = MotorSelectorViewModel.MotorDirection.MOTOR_BRAKE
    )
}

@Composable
fun MotorSelector(
    motors: List<MotorSelectorViewModel>,
    modifier: Modifier = Modifier
) {
    Row(modifier = modifier) {
        motors.forEach { motor ->
            val position by motor.position.flow.collectAsState()
            MotorFunctionSelector(label = motor.label, onSelect = { direction ->
                motor.onSelect(direction)
            }, position = position)
        }
    }
}

@Composable
@Preview
fun MotorSelectorPreview() {
    MotorSelector(motors = listOf(
        MotorSelectorViewModel("A") {  },
        MotorSelectorViewModel("B") {  },
        MotorSelectorViewModel("C") {  }
    ))
}