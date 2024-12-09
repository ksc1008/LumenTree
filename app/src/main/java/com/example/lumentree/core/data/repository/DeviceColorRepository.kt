package com.example.lumentree.core.data.repository

import com.example.lumentree.core.model.device.DeviceColorInfo
import com.example.lumentree.core.model.light.LightColor

interface DeviceColorRepository {
    suspend fun getDeviceColorInfoStatus(): Result<List<DeviceColorInfo>>

    suspend fun updateDeviceColor(color: DeviceColorInfo): Result<Unit>

    suspend fun updateDevicePreviewMode(on: Boolean): Result<Unit>

    suspend fun updateDevicePreviewColor(color: LightColor): Result<Unit>
}
