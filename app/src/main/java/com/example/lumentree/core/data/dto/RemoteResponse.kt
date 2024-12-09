package com.example.lumentree.core.data.dto

import com.example.lumentree.core.model.device.ColorState
import com.example.lumentree.core.model.device.DeviceColorInfo
import com.example.lumentree.core.model.devicecontrol.DeviceRemoteControlStatus
import com.example.lumentree.core.model.device_config.DeviceLocationInfo
import com.example.lumentree.core.model.device_config.DeviceSetting
import com.example.lumentree.core.model.device_config.DeviceTimingOption
import com.example.lumentree.core.model.device_connect.DeviceWiFiConnectionInfo
import com.example.lumentree.core.model.light.LightColor
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ResponseWithNoBody(
    @SerialName("ok")
    val success: Boolean
)

@Serializable
data class ColorDTO(
    @SerialName("type")
    val weatherType: Int,
    @SerialName("code")
    val colorCode: String
)

//    const val GET_DEVICE_COLORS = "dc"
@Serializable
data class ResponseWithColor(
    @SerialName("colors")
    val colorList: List<ColorDTO>
)

//    const val GET_DEVICE_CONTROL_STATE = "dcs"
@Serializable
data class ResponseWithDeviceControlState(
    @SerialName("auto")
    val autoEnabled: Boolean,
    @SerialName("man")
    val manualSwitchOn: Boolean
)

//    const val GET_DEVICE_SETTINGS = "ds"
@Serializable
data class ResponseWithDeviceSettings(
    @SerialName("name")
    val deviceName: String,
    @SerialName("auto")
    val autoEnabled: Boolean,
    @SerialName("auto_on")
    val autoLightOnTime: String,
    @SerialName("auto_off")
    val autoLightOffTime: String,
    @SerialName("wth")
    val weatherEnabled: Boolean
)

fun ResponseWithNoBody.toVO(): Boolean =
    this.success

fun ColorDTO.toVO(): DeviceColorInfo =
    DeviceColorInfo(
        LightColor.fromCode(this.colorCode),
        state = ColorState.entries[weatherType]
    )

fun ResponseWithColor.toVO(): List<DeviceColorInfo> =
    this.colorList.map {
        it.toVO()
    }

fun ResponseWithDeviceControlState.toVO(): DeviceRemoteControlStatus {
    return DeviceRemoteControlStatus(
        manualOn = manualSwitchOn,
        autoOn = autoEnabled
    )
}

fun ResponseWithDeviceSettings.toVO(): DeviceSetting {
    fun mapTimeData(time: String): Pair<Int, Int> {
        val times = time.split(":")
        return Pair(times[0].toInt(), times[1].toInt())
    }

    val autoOnTime = mapTimeData(this.autoLightOnTime)
    val autoOffTime = mapTimeData(this.autoLightOffTime)

    return DeviceSetting(
        name = deviceName,
        deviceWiFiConnectionInfo = DeviceWiFiConnectionInfo("", ""),
        deviceLocationInfo = DeviceLocationInfo(0, 0),
        deviceTimingOption = DeviceTimingOption(
            autoTurnOnHour = autoOnTime.first,
            autoTurnOnMinute = autoOnTime.second,
            autoTurnOffHour = autoOffTime.first,
            autoTurnOffMinute = autoOffTime.second,
            enableAutoSwitch = this.autoEnabled
        ),
        weatherEnabled = this.weatherEnabled
    )
}
