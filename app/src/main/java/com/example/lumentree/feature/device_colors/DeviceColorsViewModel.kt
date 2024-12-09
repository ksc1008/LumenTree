package com.example.lumentree.feature.device_colors

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lumentree.app.di.IoDispatcher
import com.example.lumentree.core.data.repository.DeviceColorRepository
import com.example.lumentree.core.data.repository.DeviceInfoRepository
import com.example.lumentree.core.model.device.ColorState
import com.example.lumentree.core.model.device.DeviceColorInfo
import com.example.lumentree.core.model.light.ColorCode
import com.example.lumentree.core.model.light.LightColor
import com.example.lumentree.feature.device_colors.colorpicker.ColorPickerDialogState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DeviceColorsViewModel @Inject constructor(
    private val deviceInfoRepository: DeviceInfoRepository,
    private val deviceColorRepository: DeviceColorRepository,
    @IoDispatcher
    private val ioDispatcher: CoroutineDispatcher
) : ViewModel() {
    private val _uiState by lazy { initiateDeviceColorState() }

    private val _dialogState =
        MutableStateFlow<ColorPickerDialogState>(ColorPickerDialogState.DialogClose)
    private var lastChangedColor: LightColor = LightColor(ColorCode(0x000000), 0)

    val uiState = _uiState.asStateFlow()
    val dialogState = _dialogState.asStateFlow()

    fun clickColorItem(weather: ColorState) {
        if (dialogState.value !is ColorPickerDialogState.DialogClose) {
            return
        }

        _dialogState.value = ColorPickerDialogState.DialogOpen(
            weather = weather,
            showingPreview = false,
            weatherName = WeatherStateToShortNameDict[weather]?:"",
            selectedColor = getWeatherColor(weather)
        )
    }

    fun updateSelectedColor(color: Color) {
        (dialogState.value as? ColorPickerDialogState.DialogOpen)?.let {
            _dialogState.value = it.copy(
                selectedColor = LightColor(
                    color = ColorCode(color.toArgb()),
                    intensity = (color.alpha * 255).toInt()
                )
            )
        }
    }

    fun setLightPreviewButton(on: Boolean) {
        viewModelScope.launch(ioDispatcher) {
            val result = deviceColorRepository.updateDevicePreviewMode(on)
            result.onSuccess {
                (_dialogState.value as? ColorPickerDialogState.DialogOpen)?.let {
                    _dialogState.emit(
                        it.copy(
                            showingPreview = on
                        )
                    )
                }
            }
        }
    }

    fun tick() {
        (_dialogState.value as? ColorPickerDialogState.DialogOpen)?.let {
            if (it.selectedColor != lastChangedColor) {
                viewModelScope.launch(ioDispatcher) {
                    deviceColorRepository.updateDevicePreviewColor(
                        it.selectedColor
                    )
                }
            }

            lastChangedColor = it.selectedColor
        }
    }

    fun clickDismissButton() {
        (_dialogState.value as? ColorPickerDialogState.DialogOpen)?.let {
            viewModelScope.launch(ioDispatcher) {
                deviceColorRepository.updateDevicePreviewMode(false)
            }
            _dialogState.value = ColorPickerDialogState.DialogClose
        }
    }

    private suspend fun updateWeatherToDevice(weather: ColorState, newColor: LightColor) {
        val result = deviceColorRepository.updateDeviceColor(
            DeviceColorInfo(
                color = newColor,
                state = weather
            )
        )

        result.onSuccess {
            updateWeatherItem(weather, newColor)
        }
    }

    private fun updateWeatherItem(weather: ColorState, newColor: LightColor) {
        (uiState.value as? DeviceColorUIState.Fetched)?.let {
            val newUpper = it.deviceColorsUpper.map { item ->
                if (item.state == weather) DeviceColorInfo(state = weather, color = newColor)
                else item
            }
            val newLower = it.deviceColorsLower.map { item ->
                if (item.state == weather) DeviceColorInfo(state = weather, color = newColor)
                else item
            }

            _uiState.value = it.copy(
                deviceColorsUpper = newUpper,
                deviceColorsLower = newLower
            )
        }
    }

    private fun getWeatherColor(weather: ColorState): LightColor {
        (uiState.value as? DeviceColorUIState.Fetched)?.let { state ->
            state.deviceColorsLower.find { item ->
                item.state == weather
            }?.let { return it.color }

            state.deviceColorsUpper.find { item ->
                item.state == weather
            }?.let { return it.color }
        }

        return LightColor.fromComposeColor(Color.Black)
    }

    fun clickApplyButton() {
        (_dialogState.value as? ColorPickerDialogState.DialogOpen)?.let {
            viewModelScope.launch(ioDispatcher) {
                deviceColorRepository.updateDevicePreviewMode(false)
                updateWeatherToDevice(it.weather, it.selectedColor)
            }

            _dialogState.value = ColorPickerDialogState.DialogClose
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
            val formalDataFetchResult = deviceInfoRepository.getDeviceStatus().getOrElse {
                handleFailure(it)
                return@launch
            }
            val colorDataFetchResult = deviceColorRepository.getDeviceColorInfoStatus().getOrElse {
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

    private fun mapDeviceColorResult(result: List<DeviceColorInfo>): Pair<List<DeviceColorInfo>, List<DeviceColorInfo>> {
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
            ColorState.SNOWY
        )

        private val WeatherStateToShortNameDict = mapOf(
            Pair(ColorState.NIGHT, "야간"),
            Pair(ColorState.SUNNY, "맑음"),
            Pair(ColorState.CLOUDY, "흐림"),
            Pair(ColorState.RAINY, "비"),
            Pair(ColorState.SNOWY, "눈"),
        )
    }
}
