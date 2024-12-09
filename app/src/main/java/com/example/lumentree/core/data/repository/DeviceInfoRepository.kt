package com.example.lumentree.core.data.repository

import com.example.lumentree.core.model.device_config.DeviceSetting

interface DeviceInfoRepository {
    suspend fun getDeviceStatus(): Result<DeviceSetting>

    suspend fun updateDeviceName(name: String): Result<Unit>

    suspend fun updateDeviceWeatherMode(on: Boolean): Result<Unit>

    suspend fun updateDeviceLightOnTime(hour: Int, minute: Int): Result<Unit>

    suspend fun updateDeviceLightOffTime(hour: Int, minute: Int): Result<Unit>
}
