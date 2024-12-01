package com.example.lumentree.core.model.devicecontrol

import java.sql.Time

data class DeviceSwitchState (
    val lightSwitchOn: Boolean,
    val lightAutoSwitch: Boolean,
    val autoSwitchOnTime: Time,
    val autoSwitchOffTime: Time
)
