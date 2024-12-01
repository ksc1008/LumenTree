package com.example.lumentree.feature.device_control

interface HasDeviceControlState {
    val isOn: Boolean
    val isAuto: Boolean
    val deviceName: String
}

sealed class DeviceControlUiState {
    data object Fetching : DeviceControlUiState()

    data class Ready(
        override val isOn: Boolean,
        override val isAuto: Boolean,
        override val deviceName: String
    ) : DeviceControlUiState(), HasDeviceControlState

    data class Updating(
        override val isOn: Boolean,
        override val isAuto: Boolean,
        override val deviceName: String
    ) : DeviceControlUiState(), HasDeviceControlState

    data class UpdateFailed(
        val cause: Throwable,
        val message: String
    ) : DeviceControlUiState()

}
