package com.example.lumentree.core.data.repository

import com.example.lumentree.core.model.devicecontrol.DeviceSwitchState

class FakeDeviceControlRepositoryImpl: DeviceControlRepository {
    override suspend fun updateLightControlState(
        switchOn: Boolean,
        autoOn: Boolean
    ): Result<DeviceSwitchState> {
        TODO("Not yet implemented")
    }

    override suspend fun getLightControlState(): Result<DeviceSwitchState> {
        TODO("Not yet implemented")
    }
}