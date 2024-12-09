package com.example.lumentree.core.model.device_config

import com.example.lumentree.core.model.device_connect.DeviceWiFiConnectionInfo

data class DeviceSetting(
    val name: String,
    val deviceTimingOption: DeviceTimingOption,
    val deviceWiFiConnectionInfo: DeviceWiFiConnectionInfo,
    val deviceLocationInfo: DeviceLocationInfo,
    val weatherEnabled: Boolean
)
