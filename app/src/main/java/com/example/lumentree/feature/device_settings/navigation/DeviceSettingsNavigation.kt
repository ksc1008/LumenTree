package com.example.lumentree.feature.device_settings.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.example.lumentree.app.navigation.ScreenRoute
import com.example.lumentree.feature.device_settings.DeviceSettingsScreen
import kotlinx.serialization.Serializable

@Serializable
data object DeviceSettings : ScreenRoute

fun NavController.navigateToDeviceSettings(navOptions: NavOptions?) =
    navigate(route = DeviceSettings, navOptions)

fun NavGraphBuilder.deviceSettingsScreen(

) {
    composable<DeviceSettings> {
        DeviceSettingsScreen(

        )
    }
}
