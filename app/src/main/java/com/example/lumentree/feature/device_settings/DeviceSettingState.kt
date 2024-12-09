package com.example.lumentree.feature.device_settings

import com.example.lumentree.core.model.device_config.DeviceTimingOption

sealed class DeviceSettingState {
    data object Loading : DeviceSettingState()

    data class Loaded(
        val deviceTimingOption: DeviceTimingOption,
        val enableWeather: Boolean
    ) : DeviceSettingState()
}
