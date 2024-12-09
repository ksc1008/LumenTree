package com.example.lumentree.core.data.repositoryimplentation

import com.example.lumentree.core.data.datasource.DeviceInfoRemoteDatasource
import com.example.lumentree.core.data.repository.DeviceInfoRepository
import com.example.lumentree.core.model.device_config.DeviceSetting
import javax.inject.Inject

class DeviceInfoRepositoryImpl @Inject constructor(
    private val deviceInfoRemoteDatasource: DeviceInfoRemoteDatasource
) : DeviceInfoRepository {
    private fun parseToTimeString(hour: Int, minute: Int) =
        "$hour:$minute"

    override suspend fun getDeviceStatus(): Result<DeviceSetting> =
        deviceInfoRemoteDatasource.getDeviceStatus()

    override suspend fun updateDeviceName(name: String): Result<Unit> =
        deviceInfoRemoteDatasource.setDeviceName(name)

    override suspend fun updateDeviceWeatherMode(on: Boolean): Result<Unit> =
        deviceInfoRemoteDatasource.setDeviceWeatherMode(on)

    override suspend fun updateDeviceLightOnTime(hour: Int, minute: Int): Result<Unit> =
        deviceInfoRemoteDatasource.setAutoLightOnTime(parseToTimeString(hour, minute))


    override suspend fun updateDeviceLightOffTime(hour: Int, minute: Int): Result<Unit> =
        deviceInfoRemoteDatasource.setAutoLightOffTime(parseToTimeString(hour, minute))
}
