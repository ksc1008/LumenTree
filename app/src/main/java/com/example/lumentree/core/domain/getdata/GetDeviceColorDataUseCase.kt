package com.example.lumentree.core.domain.getdata

import com.example.lumentree.core.data.repository.DeviceInfoRepository
import com.example.lumentree.core.model.device.DeviceColorInfo
import com.example.lumentree.core.model.device.DeviceInfo
import javax.inject.Inject

class GetDeviceColorDataUseCase @Inject constructor(val repository: DeviceInfoRepository) {
    suspend operator fun invoke(deviceInfo: DeviceInfo): Result<Set<DeviceColorInfo>> {
        return repository.getDeviceColorInfoStatus(deviceInfo)
    }
}