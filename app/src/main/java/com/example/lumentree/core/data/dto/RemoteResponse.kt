package com.example.lumentree.core.data.dto

import com.example.lumentree.core.model.device.ColorState
import com.example.lumentree.core.model.device.DeviceColorInfo
import com.example.lumentree.core.model.devicecontrol.DeviceRemoteControlStatus
import com.example.lumentree.core.model.device_config.DeviceLocationInfo
import com.example.lumentree.core.model.device_config.DeviceSetting
import com.example.lumentree.core.model.device_config.DeviceTimingOption
import com.example.lumentree.core.model.device_connect.DeviceConnectionInfo
import com.example.lumentree.core.model.device_connect.DeviceWiFiConnectionInfo
import com.example.lumentree.core.model.device_connect.WiFiAPInfo
import com.example.lumentree.core.model.device_connect.WiFiConnectingState
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

//     const val GET_WIFI_LIST = "wl"
@Serializable
data class WifiData(
    @SerialName("s")
    val ssid: String,
    @SerialName("q")
    val signalPower: Int,
    @SerialName("e")
    val encrypted: Int
)

@Serializable
data class ResponseWithWifiList(
    @SerialName("data")
    val data: List<WifiData>
)

//     const val GET_CONNECTION_INFO = "ci"
@Serializable
data class ResponseWithConnectionInfo(
    @SerialName("con")
    val connected: Boolean,
    @SerialName("ssid")
    val ssid: String?,
    // IDLE, BUSY, FAIL
    @SerialName("state")
    val wifiState: String
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

fun ResponseWithConnectionInfo.toVO(): DeviceConnectionInfo {
    val state = try{
        WiFiConnectingState.valueOf(this.wifiState)
    } catch (e: IllegalArgumentException) {
        WiFiConnectingState.FAIL
    }
    return DeviceConnectionInfo(
        ssid = this.ssid?:"",
        connected = this.connected,
        connectingState = state
    )
}

fun WifiData.toVO() =
    WiFiAPInfo(
        ssid = this.ssid,
        strength = this.signalPower,
        protected = this.encrypted == 1
    )

fun ResponseWithWifiList.toVO() =
    this.data.map { it.toVO() }
