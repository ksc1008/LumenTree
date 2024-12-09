package com.example.lumentree.core.model.device_connect

enum class WiFiConnectingState {
    IDLE, BUSY, FAIL
}

data class DeviceConnectionInfo (
    val ssid: String,
    val connected: Boolean,
    val connectingState: WiFiConnectingState
)
