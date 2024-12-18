package com.example.lumentree.core.model.device

data class DeviceFormalStatus(
    val connectedToWiFi: Boolean,
    val autoSwitch: Boolean,
    val weatherMode: Boolean,
    val name: String
)
