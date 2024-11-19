package com.example.lumentree.core.model.device

data class DeviceTimingOption(
    val enableAutoSwitch: Boolean,
    val autoTurnOnHour: Int,
    val autoTurnOffHour: Int,
    val autoTurnOnMinute: Int,
    val autoTurnOffMinute: Int
)
