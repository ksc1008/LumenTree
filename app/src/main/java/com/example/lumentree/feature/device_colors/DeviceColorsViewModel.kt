package com.example.lumentree.feature.device_colors

import android.icu.text.CaseMap.Upper
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lumentree.app.di.IoDispatcher
import com.example.lumentree.core.domain.getdata.GetDeviceColorDataUseCase
import com.example.lumentree.core.model.device.ColorState
import com.example.lumentree.core.model.device.DeviceColorInfo
import com.example.lumentree.core.model.device.DeviceInfo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DeviceColorsViewModel @Inject constructor(
    private val getDeviceColorDataUseCase: GetDeviceColorDataUseCase,
    @IoDispatcher
    private val ioDispatcher: CoroutineDispatcher
) : ViewModel() {
    private val _uiState = MutableStateFlow<DeviceColorUIState>(
        DeviceColorUIState.Fetching
    )

    val uiState = _uiState.asStateFlow()

    fun updateDeviceColorState(deviceInfo: DeviceInfo) {
        viewModelScope.launch(ioDispatcher) {
            val result = getDeviceColorDataUseCase(deviceInfo)
            handleDeviceColorResult(result)
        }
    }

    private fun handleDeviceColorResult(result: Result<Set<DeviceColorInfo>>) {
        result.fold(
            onSuccess = {
                val newDeviceColorInfo = mapDeviceColorResult(it)
                when(uiState.value) {
                    is DeviceColorUIState.FetchFailed -> TODO()
                    is DeviceColorUIState.Fetched -> TODO()
                    DeviceColorUIState.Fetching -> TODO()
                }
            },
            onFailure = {
                _uiState.value = DeviceColorUIState.FetchFailed(
                    it.message ?: "",
                    it
                )
            }
        )
    }

    private fun mapDeviceColorResult(result: Set<DeviceColorInfo>): Pair<List<DeviceColorInfo>, List<DeviceColorInfo>> {
        val upperList = mutableListOf<DeviceColorInfo>()
        val lowerList = mutableListOf<DeviceColorInfo>()
        for (weather in UpperWeatherStates) {
            result.find {
                it.state == weather
            }?.let {
                upperList.add(it)
            }
        }

        for (weather in LowerWeatherStates) {
            result.find {
                it.state == weather
            }?.let {
                lowerList.add(it)
            }
        }

        return Pair(upperList, lowerList)
    }

    companion object {
        private val UpperWeatherStates = setOf<ColorState>(
            ColorState.NIGHT
        )

        private val LowerWeatherStates = setOf(
            ColorState.SUNNY,
            ColorState.CLOUDY,
            ColorState.RAINY,
            ColorState.SNOWY,
            ColorState.HOTTER_THAN_YESTERDAY,
            ColorState.COLDER_THAN_YESTERDAY
        )
    }
}