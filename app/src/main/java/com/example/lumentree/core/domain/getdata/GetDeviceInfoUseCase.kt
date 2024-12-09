package com.example.lumentree.core.domain.getdata

import com.example.lumentree.core.data.repository.DeviceInfoRepository
import com.example.lumentree.core.data.repository.DeviceListRepository
import com.example.lumentree.core.data.repository.DeviceNetworkRepository
import com.example.lumentree.core.model.device.DeviceFormalStatus
import javax.inject.Inject

class GetDeviceInfoUseCase @Inject constructor(
    private val deviceNetworkRepository: DeviceNetworkRepository,
    private val deviceInfoRepository: DeviceInfoRepository
) {
    suspend operator fun invoke(): Result<DeviceFormalStatus> {
        val networkInfo = deviceNetworkRepository.getDeviceConnectionInfo()
        val deviceInfo = deviceInfoRepository.getDeviceStatus()

        deviceInfo.onSuccess { setting ->
            networkInfo.onSuccess { network ->
                return Result.success(
                    DeviceFormalStatus(
                        name = setting.name,
                        autoSwitch = setting.deviceTimingOption.enableAutoSwitch,
                        weatherMode = setting.weatherEnabled,
                        connectedToWiFi = network.connected
                    )
                )
            }
                .onFailure {
                    return Result.failure(it)
                }
        }.onFailure {
            return Result.failure(it)
        }

        return Result.failure(Exception())
    }
}
