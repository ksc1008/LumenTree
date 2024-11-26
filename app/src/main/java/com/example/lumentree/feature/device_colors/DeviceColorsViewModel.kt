package com.example.lumentree.feature.device_colors

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lumentree.app.di.IoDispatcher
import com.example.lumentree.core.domain.getdata.GetDeviceColorDataUseCase
import com.example.lumentree.core.domain.getdata.GetSelectedDeviceDataUseCase
import com.example.lumentree.core.model.device.ColorState
import com.example.lumentree.core.model.device.DeviceColorInfo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DeviceColorsViewModel @Inject constructor(
    private val getDeviceColorDataUseCase: GetDeviceColorDataUseCase,
    private val getDeviceFormalStatusUseCase: GetSelectedDeviceDataUseCase,
    @IoDispatcher
    private val ioDispatcher: CoroutineDispatcher
) : ViewModel() {
    private val _uiState by lazy { initiateDeviceColorState() }

    val uiState = _uiState.asStateFlow()

    fun updateDeviceColorState() {
        viewModelScope.launch(ioDispatcher) {
            val result = getDeviceColorDataUseCase()
            handleDeviceColorResult(result)
        }
    }

    private fun initiateDeviceColorState(): MutableStateFlow<DeviceColorUIState> {
        val state = MutableStateFlow<DeviceColorUIState>(
            DeviceColorUIState.Fetching
        )

        viewModelScope.launch(ioDispatcher) {
            val name: String
            val upperColor: List<DeviceColorInfo>
            val lowerColor: List<DeviceColorInfo>
            val formalDataFetchResult = getDeviceFormalStatusUseCase().getOrElse {
                handleFailure(it)
                return@launch
            }
            val colorDataFetchResult = getDeviceColorDataUseCase().getOrElse {
                handleFailure(it)
                return@launch
            }

            name = formalDataFetchResult.name

            val mapped = mapDeviceColorResult(colorDataFetchResult)
            upperColor = mapped.first
            lowerColor = mapped.second

            state.value = DeviceColorUIState.Fetched(
                deviceName = name,
                deviceColorsUpper = upperColor,
                deviceColorsLower = lowerColor
            )
        }

        return state
    }

    private fun handleFailure(throwable: Throwable) {
        _uiState.value = DeviceColorUIState.FetchFailed(
            cause = throwable,
            message = throwable.message ?: ""
        )
    }

    private fun handleDeviceColorResult(result: Result<Set<DeviceColorInfo>>) {
        result.fold(
            onSuccess = {
                val newDeviceColorInfo = mapDeviceColorResult(it)
                (uiState.value as? DeviceColorUIState.Fetched)?.let {
                    _uiState.value = it.copy(
                        deviceColorsUpper = newDeviceColorInfo.first,
                        deviceColorsLower = newDeviceColorInfo.second
                    )
                }
            },
            onFailure = {
                _uiState.value = DeviceColorUIState.FetchFailed(
                    message = it.message ?: "",
                    cause = it
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
        private val UpperWeatherStates = setOf(
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