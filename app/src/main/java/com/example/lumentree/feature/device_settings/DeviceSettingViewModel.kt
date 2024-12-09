package com.example.lumentree.feature.device_settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lumentree.app.di.IoDispatcher
import com.example.lumentree.core.data.repository.DeviceInfoRepository
import com.example.lumentree.core.model.device_config.DeviceSetting
import com.example.lumentree.core.model.device_config.DeviceTimingOption
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DeviceSettingViewModel @Inject constructor(
    private val deviceInfoRepository: DeviceInfoRepository,
    @IoDispatcher
    private val ioDispatcher: CoroutineDispatcher
) : ViewModel() {
    private val _deviceSettingState by lazy { initState() }
    val deviceSettingState = _deviceSettingState.asStateFlow()

    private fun initState(): MutableStateFlow<DeviceSettingState> {
        val state = MutableStateFlow<DeviceSettingState>(DeviceSettingState.Loading)

        viewModelScope.launch(ioDispatcher) {
            val result = deviceInfoRepository.getDeviceStatus()
            result.onSuccess {
                _deviceSettingState.emit(
                    mapSettingToState(it)
                )
            }
        }

        return state
    }

    private fun mapSettingToState(deviceSetting: DeviceSetting): DeviceSettingState.Loaded =
        DeviceSettingState.Loaded(
            deviceTimingOption = deviceSetting.deviceTimingOption,
            enableWeather = deviceSetting.weatherEnabled
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
}
