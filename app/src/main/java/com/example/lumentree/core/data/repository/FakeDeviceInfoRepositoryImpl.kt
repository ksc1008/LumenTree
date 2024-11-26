package com.example.lumentree.core.data.repository

import com.example.lumentree.core.model.device.ColorState
import com.example.lumentree.core.model.device.DeviceColorInfo
import com.example.lumentree.core.model.device.DeviceFormalStatus
import com.example.lumentree.core.model.device.DeviceInfo
import com.example.lumentree.core.model.error.NoDeviceFoundError
import com.example.lumentree.core.model.light.ColorCode
import com.example.lumentree.core.model.light.LightColor
import javax.inject.Inject

internal class FakeDeviceInfoRepositoryImpl @Inject constructor(): DeviceInfoRepository {
    override suspend fun getDeviceStatus(device: DeviceInfo): Result<DeviceFormalStatus> {
        return Result.success(
            DeviceFormalStatus(
                name = "나의 무드등",
                playSound = true,
                autoSwitch = false,
                connectedToWiFi = true
            )
        )
    }

    override suspend fun getDeviceColorInfoStatus(device: DeviceInfo): Result<Set<DeviceColorInfo>> {
        return Result.success(
            setOf(
                DeviceColorInfo(LightColor("A", color = ColorCode("#ff0000")), ColorState.CLOUDY),
                DeviceColorInfo(LightColor("B", color = ColorCode("#0ff000")), ColorState.NIGHT),
                DeviceColorInfo(LightColor("C", color = ColorCode("#0fff00")), ColorState.RAINY),
                DeviceColorInfo(LightColor("D", color = ColorCode("#000ff0")), ColorState.SNOWY),
                DeviceColorInfo(LightColor("E", color = ColorCode("#000fff")), ColorState.SUNNY),
                DeviceColorInfo(LightColor("F", color = ColorCode("#ff0ff0")), ColorState.COLDER_THAN_YESTERDAY),
                DeviceColorInfo(LightColor("G", color = ColorCode("#ff00ff")), ColorState.HOTTER_THAN_YESTERDAY),
            )
        )
    }


}
