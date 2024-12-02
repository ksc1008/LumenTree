package com.example.lumentree.feature.device_selector.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.example.lumentree.app.navigation.ScreenRoute
import com.example.lumentree.feature.device_selector.DeviceSelectorScreen
import kotlinx.serialization.Serializable

@Serializable
data object DeviceSelector : ScreenRoute

fun NavController.navigateToDeviceSelector(navOptions: NavOptions?) =
    navigate(route = DeviceSelector, navOptions)

fun NavGraphBuilder.deviceSelectorScreen(

) {
    composable<DeviceSelector> {
        DeviceSelectorScreen(

        )
    }
}
