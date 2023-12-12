package com.livingcode.test.robotdriverplus.ui.shared

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.livingcode.test.robotdriverplus.R
import com.livingcode.test.robotdriverplus.ui.theme.*

@Composable
fun SingleLineListElement(name: String, connected: Boolean, onClick : (String) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                if (connected) colorConnected else colorDisconnected,
                shape = RoundedCornerShape(percent = defaultRoundPercent)
            ).clickable { onClick(name) }
    ) {
        Text(
            text = name,
            modifier = Modifier.padding(all = defaultPadding),
            style = listElementName
        )
    }
}

@Composable
@Preview
fun SingleLineListElementPreview() {
    SingleLineListElement(name = "NXT-1", connected = true, onClick = {})
}