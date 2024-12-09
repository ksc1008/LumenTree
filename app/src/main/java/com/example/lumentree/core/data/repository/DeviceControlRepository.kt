package com.example.lumentree.core.data.repository

import com.example.lumentree.core.model.devicecontrol.DeviceRemoteControlStatus

interface DeviceControlRepository {
    suspend fun updateLightOn(switchOn: Boolean): Result<Unit>

    suspend fun updateLightAutoMode(auto: Boolean): Result<Unit>

    suspend fun getLightControlState(): Result<DeviceRemoteControlStatus>
}
