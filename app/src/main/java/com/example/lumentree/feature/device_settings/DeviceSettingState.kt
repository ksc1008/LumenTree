package com.example.lumentree.feature.device_settings

import com.example.lumentree.core.model.device_config.DeviceTimingOption
import com.example.lumentree.core.model.device_connect.DeviceConnectionInfo
import com.example.lumentree.core.model.device_connect.WiFiAPInfo

sealed class DeviceSettingState {
    data object Loading : DeviceSettingState()

    data class Loaded(
        val deviceTimingOption: DeviceTimingOption,
        val enableWeather: Boolean,
        val connectionState: DeviceConnectionInfo
    ) : DeviceSettingState()
}

sealed class DeviceWifiSelectionDialogState {
    data object Closed : DeviceWifiSelectionDialogState()

    data object Fetching : DeviceWifiSelectionDialogState()

    data class Opened (
        val wifiList: List<WiFiAPInfo>
    ) : DeviceWifiSelectionDialogState()
}
