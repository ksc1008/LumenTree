package com.example.lumentree.core.data.repository

import com.example.lumentree.core.model.device_connect.DeviceConnectionInfo
import com.example.lumentree.core.model.device_connect.WiFiAPInfo

interface DeviceNetworkRepository {
    suspend fun getDeviceConnectionInfo(): Result<DeviceConnectionInfo>

    suspend fun getDeviceWifiList(): Result<List<WiFiAPInfo>>

    suspend fun connectToWifi(ssid: String, password: String): Result<Unit>
}
