package com.livingcode.test.robotdriverplus.ui.controller

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.livingcode.test.robotdriverplus.ui.theme.defaultPadding
import com.livingcode.test.robotdriverplus.ui.theme.motorSelectorIconSize
import com.livingcode.test.robotdriverplus.ui.theme.motorSelectorLabel

@Composable
fun FourDirectionsJoystick(
    modifier: Modifier = Modifier,
    onSelected: (JoystickViewModel.Directions) -> Unit,
    selected: JoystickViewModel.Directions,
    label: String
) {
    Box(modifier = modifier) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Row {
                Box(modifier = Modifier.size(motorSelectorIconSize))
                Box(
                    modifier = Modifier
                        .size(motorSelectorIconSize)
                        .background(
                            color = if (selected == JoystickViewModel.Directions.UP) Color.Green else Color.Transparent
                        )
                        .clickable {
                            onSelected(JoystickViewModel.Directions.UP)
                        }
                ) {
                    Image(
                        painter = painterResource(id = JoystickViewModel.Directions.UP.iconResource),
                        contentDescription = null
                    )
                }
                Box(modifier = Modifier.size(motorSelectorIconSize))
            }
            Row {
                Box(modifier = Modifier
                    .size(motorSelectorIconSize)
                    .background(
                        color = if (selected == JoystickViewModel.Directions.LEFT) Color.Green else Color.Transparent
                    )
                    .clickable {
                        onSelected(JoystickViewModel.Directions.LEFT)
                    }) {
                    Image(
                        painter = painterResource(id = JoystickViewModel.Directions.LEFT.iconResource),
                        contentDescription = null
                    )
                }
                Box(modifier = Modifier.size(motorSelectorIconSize))
                Box(modifier = Modifier
                    .size(motorSelectorIconSize)
                    .background(
                        color = if (selected == JoystickViewModel.Directions.RIGHT) Color.Green else Color.Transparent
                    )
                    .clickable {
                        onSelected(JoystickViewModel.Directions.RIGHT)
                    }) {
                    Image(
                        painter = painterResource(id = JoystickViewModel.Directions.RIGHT.iconResource),
                        contentDescription = null
                    )
                }
            }
            Row {
                Box(modifier = Modifier.size(motorSelectorIconSize))
                Box(modifier = Modifier
                    .size(motorSelectorIconSize)
                    .background(
                        color = if (selected == JoystickViewModel.Directions.DOWN) Color.Green else Color.Transparent
                    )
                    .clickable {
                        onSelected(JoystickViewModel.Directions.DOWN)
                    }) {
                    Image(
                        painter = painterResource(id = JoystickViewModel.Directions.DOWN.iconResource),
                        contentDescription = null
                    )
                }
                Box(modifier = Modifier.size(motorSelectorIconSize))
            }
            Text(
                text = label, style = motorSelectorLabel, modifier = Modifier.padding(
                    defaultPadding
                )
            )
        }
    }
}

@Composable
@Preview
fun FourDirectionsJoystickPreview() {
    FourDirectionsJoystick(
        selected = JoystickViewModel.Directions.RIGHT,
        onSelected = {},
        label = "LEFT STICK"
    )
}