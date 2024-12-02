package com.example.lumentree.app.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.lumentree.feature.device_colors.DeviceColorsScreen
import com.example.lumentree.feature.device_colors.navigation.DeviceColors
import com.example.lumentree.feature.device_control.DeviceControlScreen
import com.example.lumentree.feature.device_control.navigation.DeviceControl
import com.example.lumentree.feature.device_control.navigation.navigateToDeviceControl
import com.example.lumentree.feature.device_selector.DeviceSelectorScreen
import com.example.lumentree.feature.device_selector.navigation.DeviceSelector
import com.example.lumentree.feature.device_settings.DeviceSettingsScreen
import com.example.lumentree.feature.device_settings.navigation.DeviceSettings
import com.example.lumentree.feature.device_settings.navigation.navigateToDeviceSettings

@Composable
fun LumenTreeNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    startDestination: ScreenRoute
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = startDestination
    ) {
        composable<DeviceControl> {
            DeviceControlScreen()
        }

        composable<DeviceColors> {
            DeviceColorsScreen()
        }

        composable<DeviceSettings> {
            DeviceSettingsScreen()
        }

        composable<DeviceSelector> {
            DeviceSelectorScreen(
                deviceClick = {
                    navController.navigateToDeviceControl(null)
                },
                settingButtonClick = {
                    navController.navigateToDeviceSettings(null)
                }
            )
        }
    }
}
