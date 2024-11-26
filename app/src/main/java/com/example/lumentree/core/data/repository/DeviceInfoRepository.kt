package com.example.lumentree.core.data.repository

import com.example.lumentree.core.model.device.DeviceColorInfo
import com.example.lumentree.core.model.device.DeviceFormalStatus
import com.example.lumentree.core.model.device.DeviceInfo
import kotlinx.coroutines.flow.Flow

interface DeviceInfoRepository {
    suspend fun getDeviceStatus(device: DeviceInfo): Result<DeviceFormalStatus>

    suspend fun getDeviceColorInfoStatus(device: DeviceInfo): Result<Set<DeviceColorInfo>>
}