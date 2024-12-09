package com.example.lumentree.core.data.repositoryimplentation

import com.example.lumentree.core.data.datasource.DeviceControlRemoteDatasource
import com.example.lumentree.core.data.repository.DeviceControlRepository
import com.example.lumentree.core.model.devicecontrol.DeviceRemoteControlStatus
import javax.inject.Inject

class DeviceControlRepositoryImpl @Inject constructor(
    private val deviceControlRemoteDatasource: DeviceControlRemoteDatasource
):DeviceControlRepository {
    override suspend fun updateLightOn(switchOn: Boolean): Result<Unit> =
        deviceControlRemoteDatasource.setDeviceSwitch(switchOn)

    override suspend fun updateLightAutoMode(auto: Boolean): Result<Unit> =
        deviceControlRemoteDatasource.setDeviceAutoMode(auto)

    override suspend fun getLightControlState(): Result<DeviceRemoteControlStatus> =
        deviceControlRemoteDatasource.getDeviceControlState()
}
