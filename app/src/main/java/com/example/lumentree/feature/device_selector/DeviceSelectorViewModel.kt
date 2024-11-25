package com.example.lumentree.feature.device_selector

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lumentree.app.di.IoDispatcher
import com.example.lumentree.core.domain.getdata.ConnectToLastConnectedDeviceUseCase
import com.example.lumentree.core.domain.getdata.GetSelectedDeviceDataUseCase
import com.example.lumentree.core.model.device.DeviceFormalStatus
import com.example.lumentree.core.model.error.NoDeviceFoundError
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DeviceSelectorViewModel @Inject constructor(
    private val getSelectedDeviceDataUseCase: GetSelectedDeviceDataUseCase,
    private val connectDeviceUseCase: ConnectToLastConnectedDeviceUseCase,
    @IoDispatcher
    private val ioDispatcher: CoroutineDispatcher
) : ViewModel() {
    private val _uiState by lazy {
        initializeUiState()
    }

    val uiState = _uiState.asStateFlow()

    private fun initializeUiState(): MutableStateFlow<DeviceSelectorUiState> {
        val state = MutableStateFlow<DeviceSelectorUiState>(DeviceSelectorUiState.Fetching)

        viewModelScope.launch(ioDispatcher) {
            var result = getSelectedDeviceDataUseCase()
            result.onFailure {
                result = reconnectIfDisconnected()
            }

            result.onFailure {
                if (it is NoDeviceFoundError) {
                    state.emit(DeviceSelectorUiState.NoDevice)
                } else {
                    state.emit(
                        DeviceSelectorUiState.Error(
                            it,
                            it.message ?: it::class.toString()
                        )
                    )
                }
            }
                .onSuccess {
                    state.emit(
                        mapDeviceStatus(it)
                    )
                }
        }

        return state
    }

    private suspend fun reconnectIfDisconnected(): Result<DeviceFormalStatus> {
        val result = connectDeviceUseCase()

        result.fold(
            onSuccess = {
                return getSelectedDeviceDataUseCase()
            },
            onFailure = {
                return Result.failure(it)
            }
        )
    }

    private fun mapDeviceStatus(status: DeviceFormalStatus): DeviceSelectorUiState =
        DeviceSelectorUiState.WithDevice(
            deviceName = status.name,
            asmrEnabled = status.playSound,
            wifiEnabled = status.connectedToWiFi,
            autoLightEnabled = status.autoSwitch
        )

    private fun editNameState(newValue: String) {
        (uiState.value as? DeviceSelectorUiState.WithDevice)?.let {
            val newState = it.copy(
                deviceName = newValue
            )
            _uiState.value = newState
        }
    }

    fun onNameEditFinished(value: String) {
        if(value.isEmpty()) {
            return
        }
        editNameState(value)
    }

    fun onDeviceClick() {

    }
}
