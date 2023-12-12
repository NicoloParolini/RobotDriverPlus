package com.livingcode.test.robotdriverplus.ui.splash

import androidx.compose.foundation.layout.Box
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionState
import com.google.accompanist.permissions.PermissionStatus
import com.google.accompanist.permissions.rememberPermissionState

@ExperimentalPermissionsApi
@Composable
fun Splash(
    onPermissionGranted: () -> Unit,
    title: String
) {
    val btPermissionState =
        rememberPermissionState(permission = android.Manifest.permission.BLUETOOTH)

    Box(contentAlignment = Alignment.Center) {
        Text(text = title, fontSize = 32.sp)
    }

    when (btPermissionState.status) {
        PermissionStatus.Granted -> onPermissionGranted()
        is PermissionStatus.Denied -> SideEffect {
            btPermissionState.launchPermissionRequest()
        }
    }
}

@ExperimentalPermissionsApi
@Composable
@Preview
fun SplashPreview() {
    Splash(onPermissionGranted = {}, title = "Gaaaah")
}

@ExperimentalPermissionsApi
@Composable
fun SplashRoot(vm: SplashViewModel?) {
    vm?.let {
        val title by vm.permissionRequest.flow.collectAsState()

        Splash(
            onPermissionGranted = { vm.onPermissionGranted() },
            title = title
        )
    }
}