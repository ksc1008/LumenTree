package com.example.lumentree.core.domain.getdata

import com.example.lumentree.core.data.repository.DeviceInfoRepository
import com.example.lumentree.core.data.repository.DeviceListRepository
import com.example.lumentree.core.model.device.DeviceFormalStatus
import javax.inject.Inject

class GetSelectedDeviceDataUseCase @Inject constructor(
    private val deviceListRepository: DeviceListRepository,
    private val deviceInfoRepository: DeviceInfoRepository
) {
    suspend operator fun invoke(): Result<DeviceFormalStatus> {
        val result = deviceListRepository.fetchConnectedDevice()

        return Result.failure(Exception())
    }
}
