package com.example.lumentree.core.data.repository

import com.example.lumentree.core.model.device.ColorState
import com.example.lumentree.core.model.device.DeviceColorInfo
import com.example.lumentree.core.model.device.DeviceFormalStatus
import com.example.lumentree.core.model.device.DeviceInfo
import com.example.lumentree.core.model.light.ColorCode
import com.example.lumentree.core.model.light.LightColor
import javax.inject.Inject

internal fun createPreviewSet(): Set<DeviceColorInfo> {
    val set = setOf(
        DeviceColorInfo(
            state = ColorState.NIGHT,
            color = LightColor(
                "따뜻한 톤",
                color = ColorCode("FFF0D4")
            )
        ),

        DeviceColorInfo(
            state = ColorState.SUNNY,
            color = LightColor(
                "밝은 노랑색",
                color = ColorCode("FFFCA0")
            )
        ),
        DeviceColorInfo(
            state = ColorState.CLOUDY,
            color = LightColor(
                "하늘색",
                color = ColorCode("ADFCFF")
            )
        ),
        DeviceColorInfo(
            state = ColorState.RAINY,
            color = LightColor(
                "자주색",
                color = ColorCode("B2A3BF")
            )
        ),
        DeviceColorInfo(
            state = ColorState.SNOWY,
            color = LightColor(
                "회색",
                color = ColorCode("C4C4C4")
            )
        ),
        DeviceColorInfo(
            state = ColorState.COLDER_THAN_YESTERDAY,
            color = LightColor(
                "파랑색",
                color = ColorCode("1A7EF5")
            )
        ),
        DeviceColorInfo(
            state = ColorState.HOTTER_THAN_YESTERDAY,
            color = LightColor(
                "주황색",
                color = ColorCode("FF932B")
            )
        )
    )

    return set
}

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
            createPreviewSet()
        )
    }
}
