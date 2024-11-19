package com.example.lumentree.core.model.device

data class DeviceFormalStatus(
    val connectedToWiFi: Boolean,
    val autoSwitch: Boolean,
    val playSound: Boolean,
    val name: String
)
