package com.example.lumentree.core.data.repository

import com.example.lumentree.core.model.device.DeviceInfo

interface DeviceListRepository {
    suspend fun fetchConnectedDevice(): Result<DeviceInfo>

    suspend fun fetchPairedDevice(): Result<List<DeviceInfo>>

    suspend fun connectToDevice(deviceInfo: DeviceInfo): Result<Unit>

    suspend fun sendTestMessage(): Result<String>
}
