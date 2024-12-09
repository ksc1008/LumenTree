package com.example.lumentree.core.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
sealed class CommandBody {
    //    const val MANUAL_LIGHT_SWITCH = "ls"
    @Serializable
    data class ManualLightSwitch(
        @SerialName("on")
        val on: Boolean
    ) : CommandBody()

    //    const val SET_LIGHT_PREVIEW_MODE = "lpm"
    @Serializable
    data class SetLightPreviewMode(
        @SerialName("on")
        val on: Boolean
    ) : CommandBody()

    //    const val SET_PREVIEW_LIGHT_COLOR = "plc"
    @Serializable
    data class SetPreviewLightColor(
        @SerialName("code")
        val colorCode: String
    ) : CommandBody()

    //    const val SET_AUTO_MODE = "am"
    @Serializable
    data class SetAutoMode(
        @SerialName("on")
        val on: Boolean
    ) : CommandBody()

    //    const val SET_WEATHER_MODE = "wm"
    @Serializable
    data class SetWeatherMode(
        @SerialName("on")
        val weatherAuto: Boolean
    ) : CommandBody()

    //    const val SET_DEVICE_NAME = "dn"
    @Serializable
    data class SetDeviceName(
        @SerialName("name")
        val newName: String
    ) : CommandBody()

    //    const val SET_LIGHT_COLOR = "lc"
    @Serializable
    data class SetLightColor(
        @SerialName("wth")
        val weatherCode: Int,
        @SerialName("code")
        val colorCode: String
    ) : CommandBody()

    //    const val SET_AUTO_START_TIME = "ast"
    @Serializable
    data class SetAutoStartTime(
        @SerialName("time")
        val startTime: String
    ) : CommandBody()

    //    const val SET_AUTO_END_TIME = "aet"
    @Serializable
    data class SetAutoEndTime(
        @SerialName("time")
        val startTime: String
    ) : CommandBody()

    //    const val CONNECT_WIFI = "cw"
    @Serializable
    data class ConnectWifi(
        @SerialName("ssid")
        val ssid: String,
        @SerialName("pw")
        val password: String
    ) : CommandBody()

    @Serializable
    data object EMPTY : CommandBody()
}

object Commands {
    const val MANUAL_LIGHT_SWITCH = "ls"
    const val SET_LIGHT_PREVIEW_MODE = "lpm"
    const val SET_PREVIEW_LIGHT_COLOR = "plc"
    const val SET_AUTO_MODE = "am"
    const val SET_WEATHER_MODE = "wm"
    const val SET_LIGHT_COLOR = "lc"
    const val SET_AUTO_START_TIME = "ast"
    const val SET_AUTO_END_TIME = "aet"

    const val SET_DEVICE_NAME = "dn"

    const val GET_DEVICE_CONTROL_STATE = "dcs"
    const val GET_DEVICE_COLORS = "dc"
    const val GET_DEVICE_SETTINGS = "ds"

    const val GET_WIFI_LIST = "wl"
    const val GET_CONNECTION_INFO = "ci"
    const val CONNECT_WIFI = "cw"
}
