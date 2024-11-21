package com.example.lumentree.feature.device_selector

import java.lang.Exception

sealed class DeviceSelectorUiState {
    data object Fetching : DeviceSelectorUiState()

    data object NoDevice : DeviceSelectorUiState()

    data class WithDevice(
        val deviceName: String,
        val wifiEnabled: Boolean,
        val asmrEnabled: Boolean,
        val autoLightEnabled: Boolean
    ) : DeviceSelectorUiState()

    data class Error(
        val cause: Exception,
        val message: String
    ) : DeviceSelectorUiState()
}
