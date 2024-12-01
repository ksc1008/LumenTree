package com.example.lumentree.core.data.repository

import com.example.lumentree.core.model.devicecontrol.DeviceSwitchState

interface DeviceControlRepository {
    suspend fun updateLightControlState(switchOn: Boolean, autoOn: Boolean): Result<DeviceSwitchState>

    suspend fun getLightControlState(): Result<DeviceSwitchState>
}
