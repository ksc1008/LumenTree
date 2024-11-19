package com.example.lumentree.core.domain.getdata

import com.example.lumentree.core.model.device.DeviceFormalStatus

class GetSelectedDeviceDataUseCase {
    suspend operator fun invoke(macAddress: String): Result<DeviceFormalStatus> {
        return Result.failure(Exception())
    }
}