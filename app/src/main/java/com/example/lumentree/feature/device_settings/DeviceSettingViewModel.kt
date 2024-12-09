package com.example.lumentree.feature.device_settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lumentree.app.di.IoDispatcher
import com.example.lumentree.core.data.repository.DeviceInfoRepository
import com.example.lumentree.core.data.repository.DeviceNetworkRepository
import com.example.lumentree.core.model.device_config.DeviceSetting
import com.example.lumentree.core.model.device_connect.DeviceConnectionInfo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DeviceSettingViewModel @Inject constructor(
    private val deviceInfoRepository: DeviceInfoRepository,
    private val deviceNetworkRepository: DeviceNetworkRepository,
    @IoDispatcher
    private val ioDispatcher: CoroutineDispatcher
) : ViewModel() {
    private val _deviceSettingState by lazy { initState() }
    private val _wifiDialogState = MutableStateFlow<DeviceWifiSelectionDialogState>(DeviceWifiSelectionDialogState.Closed)
    val deviceSettingState = _deviceSettingState.asStateFlow()
    val wifiDialogState = _wifiDialogState.asStateFlow()

    private fun initState(): MutableStateFlow<DeviceSettingState> {
        val state = MutableStateFlow<DeviceSettingState>(DeviceSettingState.Loading)

        viewModelScope.launch(ioDispatcher) {
            val settingResult = deviceInfoRepository.getDeviceStatus()
            val networkResult = deviceNetworkRepository.getDeviceConnectionInfo()
            settingResult.onSuccess { setting ->

                networkResult.onSuccess { network ->
                    _deviceSettingState.emit(
                        mapSettingToState(setting, network)
                    )
                }
            }
        }

        return state
    }

    private fun updateNetworkInfo(newInfo: DeviceConnectionInfo) {
        (deviceSettingState.value as? DeviceSettingState.Loaded)?.let {
            _deviceSettingState.value = it.copy(
                connectionState = newInfo
            )
        }
    }

    fun refreshNetworkInfo() {
        viewModelScope.launch(ioDispatcher) {
            val result = deviceNetworkRepository.getDeviceConnectionInfo()
            result.onSuccess {
                updateNetworkInfo(it)
            }
        }
    }

    private fun mapSettingToState(
        deviceSetting: DeviceSetting,
        connectionInfo: DeviceConnectionInfo
    ): DeviceSettingState.Loaded =
        DeviceSettingState.Loaded(
            deviceTimingOption = deviceSetting.deviceTimingOption,
            enableWeather = deviceSetting.weatherEnabled,
            connectionInfo
        )

    private fun updateDeviceTime(isOn: Boolean, hour: Int, minute: Int) {
        (deviceSettingState.value as? DeviceSettingState.Loaded)?.let {
            _deviceSettingState.value =
                if (isOn)
                    it.copy(
                        deviceTimingOption = it.deviceTimingOption.copy(
                            autoTurnOnHour = hour,
                            autoTurnOnMinute = minute
                        )
                    )
                else
                    it.copy(
                        deviceTimingOption = it.deviceTimingOption.copy(
                            autoTurnOffHour = hour,
                            autoTurnOffMinute = minute
                        )
                    )
        }
    }

    private fun updateWeatherMode(on: Boolean) {
        (deviceSettingState.value as? DeviceSettingState.Loaded)?.let {
            _deviceSettingState.value =
                it.copy(enableWeather = on)
        }
    }

    private fun getWifiListAndOpenDialog() {
        if(_wifiDialogState.value !is DeviceWifiSelectionDialogState.Closed){
            return
        }

        _wifiDialogState.value = DeviceWifiSelectionDialogState.Fetching
        viewModelScope.launch(ioDispatcher) {
            val result = deviceNetworkRepository.getDeviceWifiList()
            result.onSuccess {
                _wifiDialogState.emit(
                    DeviceWifiSelectionDialogState.Opened(it)
                )
            }
        }
    }

    fun onAutoOnTimeChanged(hour: Int, minute: Int) {
        viewModelScope.launch(ioDispatcher) {
            val result = deviceInfoRepository.updateDeviceLightOnTime(hour, minute)
            result.onSuccess {
                updateDeviceTime(true, hour, minute)
            }
        }
    }

    fun onAutoOffTimeChanged(hour: Int, minute: Int) {
        viewModelScope.launch(ioDispatcher) {
            val result = deviceInfoRepository.updateDeviceLightOffTime(hour, minute)
            result.onSuccess {
                updateDeviceTime(false, hour, minute)
            }
        }
    }

    fun onWeatherModeChanged(on: Boolean) {
        viewModelScope.launch(ioDispatcher) {
            val result = deviceInfoRepository.updateDeviceWeatherMode(on)
            result.onSuccess {
                updateWeatherMode(on)
            }
        }
    }

    fun onWifiDialogApply(ssid: String, password: String) {
        viewModelScope.launch(ioDispatcher) {
            deviceNetworkRepository.connectToWifi(
                ssid, password
            )
        }
        _wifiDialogState.value = DeviceWifiSelectionDialogState.Closed
    }

    fun onWifiDialogDismiss() {
        _wifiDialogState.value = DeviceWifiSelectionDialogState.Closed
    }

    fun onNetworkSettingClick() {
        getWifiListAndOpenDialog()
    }
}
