package com.example.lumentree.core.data.repository

import com.example.lumentree.core.model.device.DeviceFormalStatus
import com.example.lumentree.core.model.device.DeviceInfo
import com.example.lumentree.core.model.error.NoDeviceFoundError
import javax.inject.Inject

internal class FakeDeviceInfoRepositoryImpl @Inject constructor(): DeviceInfoRepository {
    override suspend fun getDeviceStatus(device: DeviceInfo): Result<DeviceFormalStatus> {
        return Result.failure(NoDeviceFoundError())
        return Result.success(
            DeviceFormalStatus(
                name = "나의 무드등",
                playSound = true,
                autoSwitch = false,
                connectedToWiFi = true
            )
        )
    }
}
