package com.example.lumentree.core.domain.getdata

import com.example.lumentree.core.data.repository.DeviceInfoRepository
import com.example.lumentree.core.data.repository.DeviceListRepository
import com.example.lumentree.core.model.device.DeviceColorInfo
import com.example.lumentree.core.model.device.DeviceInfo
import javax.inject.Inject

class GetDeviceColorDataUseCase @Inject constructor(
    deviceListRepository: DeviceListRepository,
    val repository: DeviceInfoRepository
) : DeviceUseCase<Set<DeviceColorInfo>>(deviceListRepository) {
    override suspend fun processWithResult(deviceInfo: DeviceInfo): Result<Set<DeviceColorInfo>> {
        return repository.getDeviceColorInfoStatus(deviceInfo)
    }
}