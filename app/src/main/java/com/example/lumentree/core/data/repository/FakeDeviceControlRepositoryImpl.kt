package com.example.lumentree.core.data.repository

import android.util.Log
import com.example.lumentree.core.model.devicecontrol.DeviceSwitchState
import java.time.LocalTime
import javax.inject.Inject

class FakeDeviceControlRepositoryImpl @Inject constructor() : DeviceControlRepository {
    private var switchOn = false
    private var autoOn = false

    private fun getDeviceSwitchState() =
        DeviceSwitchState(
            lightSwitchOn = switchOn,
            lightAutoSwitch = autoOn,
            autoSwitchOnTime = LocalTime.now(),
            autoSwitchOffTime = LocalTime.now()

        )

    override suspend fun updateLightControlState(
        switchOn: Boolean,
        autoOn: Boolean
    ): Result<DeviceSwitchState> {
        this.switchOn = switchOn
        this.autoOn = autoOn
        Log.d("KSC", "On: ${this.switchOn}, Auto: ${this.autoOn}")
        return Result.success(
            getDeviceSwitchState()
        )
    }

    override suspend fun getLightControlState(): Result<DeviceSwitchState> {
        return Result.success(
            getDeviceSwitchState()
        )
    }
}
