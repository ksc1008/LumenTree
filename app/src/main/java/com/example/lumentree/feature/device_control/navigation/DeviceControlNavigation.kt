package com.example.lumentree.feature.device_control.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.example.lumentree.app.navigation.ScreenRoute
import com.example.lumentree.feature.device_control.DeviceControlScreen
import kotlinx.serialization.Serializable

@Serializable
data object DeviceControl : ScreenRoute

fun NavController.navigateToDeviceControl(navOptions: NavOptions?) =
    navigate(route = DeviceControl, navOptions)

fun NavGraphBuilder.deviceControlScreen(

) {
    composable<DeviceControl> {
        DeviceControlScreen(

        )
    }
}
