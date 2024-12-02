package com.example.lumentree.feature.device_colors.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.example.lumentree.app.navigation.ScreenRoute
import com.example.lumentree.feature.device_colors.DeviceColorsScreen
import com.example.lumentree.feature.device_control.navigation.DeviceControl
import kotlinx.serialization.Serializable

@Serializable
data object DeviceColors: ScreenRoute

fun NavController.navigateToDeviceColors(navOptions: NavOptions) =
    navigate(route = DeviceColors, navOptions)

fun NavGraphBuilder.deviceColorsScreen(

) {
    composable<DeviceControl> {
        DeviceColorsScreen(

        )
    }
}
