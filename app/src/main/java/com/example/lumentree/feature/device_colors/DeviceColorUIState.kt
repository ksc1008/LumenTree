package com.example.lumentree.feature.device_colors

import com.example.lumentree.core.model.device.DeviceColorInfo
import com.example.lumentree.core.model.device.DeviceInfo

sealed class DeviceColorUIState {
    data object Fetching: DeviceColorUIState()

    data object Idle : DeviceColorUIState()

    data class Fetched(
        val deviceName: String,
        val deviceColorsUpper: List<DeviceColorInfo>,
        val deviceColorsLower: List<DeviceColorInfo>
    ) : DeviceColorUIState()

    data class FetchFailed(
        val message: String,
        val cause: Throwable
    ) : DeviceColorUIState()
}
