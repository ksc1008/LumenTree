package com.example.lumentree.feature.device_control

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lumentree.app.di.IoDispatcher
import com.example.lumentree.core.domain.devicecontrol.GetDeviceControlStateUseCase
import com.example.lumentree.core.domain.devicecontrol.UpdateLightControlUseCase
import com.example.lumentree.core.domain.getdata.GetSelectedDeviceDataUseCase
import com.example.lumentree.core.model.devicecontrol.DeviceSwitchState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DeviceControlViewModel @Inject constructor(
    val getDeviceControlStateUseCase: GetDeviceControlStateUseCase,
    val updateLightControlUseCase: UpdateLightControlUseCase,
    val getDeviceFormalStatusUseCase: GetSelectedDeviceDataUseCase,
    @IoDispatcher
    val ioDispatcher: CoroutineDispatcher
) : ViewModel() {
    private var deviceName: String = ""
    private val _uiState by lazy { initiateUiState() }
    val uiState = _uiState.asStateFlow()

    private fun initiateUiState(): MutableStateFlow<DeviceControlUiState> {
        val uiState = MutableStateFlow<DeviceControlUiState>(DeviceControlUiState.Fetching)

        viewModelScope.launch(ioDispatcher) {
            deviceName = getDeviceFormalStatusUseCase().getOrElse {
                return@launch
            }.name

            val result = getDeviceControlStateUseCase()
            processDeviceSwitchUpdateResult(result)
        }

        return uiState
    }

    private fun processDeviceSwitchUpdateResult(result: Result<DeviceSwitchState>) {
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

    private fun mapDeviceSwitchToUiState(switchState: DeviceSwitchState): DeviceControlUiState.Ready =
        DeviceControlUiState.Ready(
            isOn = switchState.lightSwitchOn,
            isAuto = switchState.lightAutoSwitch,
            deviceName = deviceName
        )

    private fun deviceReady(): Boolean = uiState.value is DeviceControlUiState.Ready

    private fun updateDeviceControl(switchOn: Boolean, autoSwitch: Boolean) {
        _uiState.value = DeviceControlUiState.Updating(
            isOn = switchOn,
            isAuto = autoSwitch,
            deviceName = deviceName
        )

        viewModelScope.launch(ioDispatcher) {
            val result = updateLightControlUseCase(
                switchOn = switchOn,
                autoSwitch = autoSwitch
            )
            processDeviceSwitchUpdateResult(result)
        }

    }

    fun clickPowerButton() {
        if (!deviceReady()) {
            return
        }

        (uiState.value as? DeviceControlUiState.Ready)?.let {
            updateDeviceControl(!it.isOn, it.isAuto)
        }
    }

    fun clickAutoSwitch() {
        if (!deviceReady()) {
            return
        }

        (uiState.value as? DeviceControlUiState.Ready)?.let {
            updateDeviceControl(it.isOn, !it.isAuto)
        }
    }
}
