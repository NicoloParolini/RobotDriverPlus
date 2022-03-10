package com.livingcode.test.robotdriverplus.ui.controller

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import com.livingcode.test.robotdriverplus.ui.theme.motorSelectorIconSize

@Composable
fun FourDirectionsJoystick(modifier: Modifier = Modifier, icons : List<Int>) {
    Box(modifier = modifier) {
        Column {
            Row {
                Box(modifier = Modifier.size(motorSelectorIconSize))
                Box(modifier = Modifier.size(motorSelectorIconSize)){
                    Image(painter = painterResource(id = icons[0]), contentDescription = null)
                }
                Box(modifier = Modifier.size(motorSelectorIconSize))
            }
            Row {
                Box(modifier = Modifier.size(motorSelectorIconSize))
                Box(modifier = Modifier.size(motorSelectorIconSize))
                Box(modifier = Modifier.size(motorSelectorIconSize))
            }
            Row {
                Box(modifier = Modifier.size(motorSelectorIconSize))
                Box(modifier = Modifier.size(motorSelectorIconSize))
                Box(modifier = Modifier.size(motorSelectorIconSize))
            }
        }
    }
}