package com.example.lumentree.core.domain.getdata

import com.example.lumentree.core.model.device.DeviceColorInfo

class GetDeviceColorDataUseCase {
    suspend operator fun invoke(): Result<List<DeviceColorInfo>> {
        return Result.failure(Exception())
    }
}