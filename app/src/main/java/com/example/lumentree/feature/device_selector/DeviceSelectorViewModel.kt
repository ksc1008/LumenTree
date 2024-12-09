package com.example.lumentree.feature.device_selector

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lumentree.app.di.IoDispatcher
import com.example.lumentree.core.data.repository.DeviceInfoRepository
import com.example.lumentree.core.data.repository.DeviceNetworkRepository
import com.example.lumentree.core.domain.getdata.ConnectToDeviceUseCase
import com.example.lumentree.core.domain.getdata.GetDeviceInfoUseCase
import com.example.lumentree.core.model.error.NoDeviceFoundException
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DeviceSelectorViewModel @Inject constructor(
    private val connectDeviceUseCase: ConnectToDeviceUseCase,
    private val deviceInfoRepository: DeviceInfoRepository,
    private val getDeviceInfoUseCase: GetDeviceInfoUseCase,
    @IoDispatcher
    private val ioDispatcher: CoroutineDispatcher
) : ViewModel() {
    private val _uiState by lazy {
        initializeUiState()
    }

    val uiState = _uiState.asStateFlow()

    private fun initializeUiState(): MutableStateFlow<DeviceSelectorUiState> {
        val state = MutableStateFlow<DeviceSelectorUiState>(DeviceSelectorUiState.PermissionChecking)
        return state
    }

    fun bluetoothPermissionCheckPass() {
        _uiState.value = DeviceSelectorUiState.Connecting
        viewModelScope.launch(ioDispatcher) {
            val result = connectDeviceUseCase()
            result.onFailure {
                if (it is NoDeviceFoundException) {
                    _uiState.emit(DeviceSelectorUiState.NoDevice)
                } else {
                    _uiState.emit(
                        DeviceSelectorUiState.Error(
                            it,
                            it.message ?: it::class.toString()
                        )
                    )
                }
            }.onSuccess {
                _uiState.emit(DeviceSelectorUiState.Fetching)
                fetchDeviceInfoAndUpdateState()
            }
        }

    }

    private suspend fun fetchDeviceInfoAndUpdateState() {
        val newState = fetchDeviceInfo()
        _uiState.value = newState
    }

    private suspend fun fetchDeviceInfo(): DeviceSelectorUiState {
        val result = getDeviceInfoUseCase()
        result.onSuccess {
            return DeviceSelectorUiState.WithDevice(
                deviceName = it.name,
                wifiEnabled = it.connectedToWiFi,
                weatherEnabled = it.weatherMode,
                autoLightEnabled = it.autoSwitch
            )
        }.onFailure {
            return DeviceSelectorUiState.Error(
                message = it.message ?: "",
                cause = it
            )
        }

        return DeviceSelectorUiState.Error(
            message = "Unknown Error",
            cause = Exception()
        )
    }

    private fun editNameState(newValue: String) {
        val oldValue = _uiState.value
        (uiState.value as? DeviceSelectorUiState.WithDevice)?.let {
            val newState = it.copy(
                deviceName = newValue
            )
            _uiState.value = newState
        }

        viewModelScope.launch(ioDispatcher) {
            val result = deviceInfoRepository.updateDeviceName(newValue)
            result.onFailure {
                _uiState.value = DeviceSelectorUiState.Error(
                    message = it.message ?: "",
                    cause = it
                )
            }
        }
    }

    fun onNameEditFinished(value: String) {
        if (value.isEmpty()) {
            return
        }
        editNameState(value)
    }

    fun refreshDeviceInfo() {
        if (uiState.value is DeviceSelectorUiState.WithDevice) {
            viewModelScope.launch(ioDispatcher) {
                fetchDeviceInfoAndUpdateState()
            }
        }
    }
}
