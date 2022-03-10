package com.livingcode.test.robotdriverplus.ui.controller

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.livingcode.test.robotdriverplus.ui.theme.defaultPadding
import com.livingcode.test.robotdriverplus.ui.theme.defaultPaddingTriple
import com.livingcode.test.robotdriverplus.ui.theme.motorSelectorLabel

@Composable
fun Controller(
    name: String,
    leftStick: JoystickViewModel,
    rightStick: JoystickViewModel,
    selected: ControllerViewModel.ControllerButtons,
    modifier : Modifier = Modifier
) {
    Column(modifier = modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = name, style = motorSelectorLabel)
        Row {
            Column(
                modifier = Modifier.weight(1f),
                horizontalAlignment = Alignment.Start
            ) {
                Text(
                    text = "L1",
                    style = motorSelectorLabel,
                    modifier = Modifier
                        .padding(defaultPadding)
                        .border(width = 2.dp, color = Color.Black)
                        .background(color = if (selected == ControllerViewModel.ControllerButtons.L1) Color.Green else Color.Transparent)
                        .padding(
                            vertical = defaultPadding,
                            horizontal = defaultPaddingTriple
                        )
                )
                Text(
                    text = "L2", style = motorSelectorLabel,
                    modifier = Modifier
                        .padding(defaultPadding)
                        .border(width = 1.dp, color = Color.Black)
                        .background(color = if (selected == ControllerViewModel.ControllerButtons.L2) Color.Green else Color.Transparent)
                        .padding(
                            vertical = defaultPadding,
                            horizontal = defaultPaddingTriple
                        )
                )
                FourDirectionsJoystick(
                    onSelected = { direction -> leftStick.onSelect(direction) },
                    selected = selected.toLeftStick(),
                    label = leftStick.name
                )
            }
            Column(
                modifier = Modifier.weight(1f),
                horizontalAlignment = Alignment.End
            ) {
                Text(
                    text = "R1", style = motorSelectorLabel,
                    modifier = Modifier
                        .padding(defaultPadding)
                        .border(width = 2.dp, color = Color.Black)
                        .background(color = if (selected == ControllerViewModel.ControllerButtons.R1) Color.Green else Color.Transparent)
                        .padding(
                            vertical = defaultPadding,
                            horizontal = defaultPaddingTriple
                        )
                )
                Text(
                    text = "R2", style = motorSelectorLabel,
                    modifier = Modifier
                        .padding(defaultPadding)
                        .border(width = 1.dp, color = Color.Black)
                        .background(color = if (selected == ControllerViewModel.ControllerButtons.R2) Color.Green else Color.Transparent)
                        .padding(
                            vertical = defaultPadding,
                            horizontal = defaultPaddingTriple
                        )
                )
                FourDirectionsJoystick(
                    onSelected = { direction -> rightStick.onSelect(direction) },
                    selected = selected.toRightStick(),
                    label = rightStick.name
                )
            }
        }
    }
}

@Composable
@Preview
fun ControllerPreview() {
    Controller(
        name = "Blue",
        leftStick = JoystickViewModel("LEFT STICK", {}),
        rightStick = JoystickViewModel("RIGHT STICK", {}),
        selected = ControllerViewModel.ControllerButtons.R2
    )
}