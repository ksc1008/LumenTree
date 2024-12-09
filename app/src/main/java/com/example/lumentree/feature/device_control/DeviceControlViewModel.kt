package com.example.lumentree.feature.device_control

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lumentree.app.di.IoDispatcher
import com.example.lumentree.core.data.repository.DeviceControlRepository
import com.example.lumentree.core.data.repository.DeviceInfoRepository
import com.example.lumentree.core.model.devicecontrol.DeviceRemoteControlStatus
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DeviceControlViewModel @Inject constructor(
    private val deviceControlRepository: DeviceControlRepository,
    private val deviceInfoRepository: DeviceInfoRepository,
    @IoDispatcher
    val ioDispatcher: CoroutineDispatcher
) : ViewModel() {
    private var deviceName: String = ""
    private val _uiState by lazy { initiateUiState() }
    val uiState = _uiState.asStateFlow()

    private fun initiateUiState(): MutableStateFlow<DeviceControlUiState> {
        val uiState = MutableStateFlow<DeviceControlUiState>(DeviceControlUiState.Fetching)

        viewModelScope.launch(ioDispatcher) {
            deviceName = deviceInfoRepository.getDeviceStatus().getOrElse {
                return@launch
            }.name

            val result = deviceControlRepository.getLightControlState()
            processDeviceSwitchUpdateResult(result)
        }

        return uiState
    }

    private fun processDeviceSwitchUpdateResult(result: Result<DeviceRemoteControlStatus>) {
        result.fold(
            onSuccess = {
                _uiState.value = mapDeviceSwitchToUiState(it)
            },
            onFailure = {
                _uiState.value = DeviceControlUiState.UpdateFailed(
                    cause = it,
                    message = it.message ?: ""
                )
            }
        )
    }

    private fun mapDeviceSwitchToUiState(switchState: DeviceRemoteControlStatus): DeviceControlUiState.Ready =
        DeviceControlUiState.Ready(
            isOn = switchState.manualOn,
            isAuto = switchState.autoOn,
            deviceName = deviceName
        )

    private fun deviceReady(): Boolean = uiState.value is DeviceControlUiState.Ready

    private fun tryGetIsLightOn(): Boolean? =
        (uiState.value as? HasDeviceControlState)?.isOn
    private fun tryGetIsAuto(): Boolean? =
        (uiState.value as? HasDeviceControlState)?.isAuto


    private fun updateDeviceAutoMode(autoSwitch: Boolean) {
        _uiState.value = DeviceControlUiState.Updating(
            isOn = tryGetIsLightOn() ?: false,
            isAuto = autoSwitch,
            deviceName = deviceName
        )

        viewModelScope.launch(ioDispatcher) {
            val result = deviceControlRepository.updateLightAutoMode(
                autoSwitch
            )

            result.onSuccess {
                _uiState.value = DeviceControlUiState.Ready(
                    isOn = tryGetIsLightOn() ?: false,
                    isAuto = autoSwitch,
                    deviceName = deviceName
                )
            }
        }
    }

    private fun updateSwitchOnOff(switchOn: Boolean) {
        _uiState.value = DeviceControlUiState.Updating(
            isOn = switchOn,
            isAuto = tryGetIsAuto() ?: false,
            deviceName = deviceName
        )

        viewModelScope.launch(ioDispatcher) {
            val result = deviceControlRepository.updateLightOn(
                switchOn
            )

            result.onSuccess {
                _uiState.value = DeviceControlUiState.Ready(
                    isOn = switchOn,
                    isAuto = tryGetIsAuto() ?: false,
                    deviceName = deviceName
                )
            }
        }
    }

    fun clickPowerButton() {
        if (!deviceReady()) {
            return
        }

        (uiState.value as? DeviceControlUiState.Ready)?.let {
            updateSwitchOnOff(!it.isOn)
        }
    }

    fun clickAutoSwitch() {
        if (!deviceReady()) {
            return
        }

        (uiState.value as? DeviceControlUiState.Ready)?.let {
            updateDeviceAutoMode(!it.isAuto)
        }
    }
}
