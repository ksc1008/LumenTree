package com.example.lumentree.core.data.repository

import com.example.lumentree.core.model.device.DeviceInfo
import javax.inject.Inject

internal class FakeDeviceListRepositoryImpl @Inject constructor(): DeviceListRepository {
    override suspend fun fetchConnectedDevice(): Result<DeviceInfo> {
        return Result.success(DeviceInfo(""))
    }

    override suspend fun fetchPairedDevice(): Result<List<DeviceInfo>> {
        TODO("Not yet implemented")
    }

    override suspend fun connectToDevice(deviceInfo: DeviceInfo): Result<Unit> {
        TODO("Not yet implemented")
    }
}
