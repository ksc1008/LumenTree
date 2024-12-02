package com.example.lumentree.feature.device

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.example.lumentree.R
import com.example.lumentree.app.navigation.ScreenRoute
import com.example.lumentree.feature.device_colors.navigation.DeviceColors
import com.example.lumentree.feature.device_control.navigation.DeviceControl
import com.example.lumentree.feature.device_settings.navigation.DeviceSettings

sealed class BottomNavItem(
    @StringRes val title: Int,
    @DrawableRes val icon: Int,
    val screenRoute: ScreenRoute
) {
    object Control : BottomNavItem(
        title = R.string.light_control,
        icon = R.drawable.sharp_power_settings_new_24,
        DeviceControl
    )

    object Colors : BottomNavItem(
        title = R.string.light_theme,
        icon = R.drawable.sharp_inbox_24,
        DeviceColors
    )

    object Settings : BottomNavItem(
        title = R.string.light_config,
        icon = R.drawable.sharp_settings_24,
        DeviceSettings
    )
}
