package com.example.lumentree.core.model.devicecontrol

import java.time.LocalTime

data class DeviceSwitchState(
    val lightSwitchOn: Boolean,
    val lightAutoSwitch: Boolean,
    val autoSwitchOnTime: LocalTime,
    val autoSwitchOffTime: LocalTime
)
