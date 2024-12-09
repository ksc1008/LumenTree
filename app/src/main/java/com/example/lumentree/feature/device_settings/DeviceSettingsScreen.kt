package com.example.lumentree.feature.device_settings

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.lumentree.core.model.device_config.DeviceTimingOption
import com.example.lumentree.ui.composables.DeviceDetailUpperBody

@Composable
fun DeviceSettingsScreen(
    modifier: Modifier = Modifier,
    viewModel: DeviceSettingViewModel = hiltViewModel()
) {
    val state by viewModel.deviceSettingState.collectAsState()

    DeviceSettingsScreenContent(
        modifier,
        state,
        onAutoOnTimeChanged = viewModel::onAutoOnTimeChanged,
        onAutoOffTimeChanged = viewModel::onAutoOffTimeChanged,
        onWeatherChanged = viewModel::onWeatherModeChanged
    )
}

@Preview(showBackground = true)
@Composable
fun DeviceSettingsScreenPreview() {
    DeviceSettingsScreenContent(
        Modifier.fillMaxSize(),
        DeviceSettingState.Loaded(
            deviceTimingOption = DeviceTimingOption(
                enableAutoSwitch = true,
                autoTurnOnHour = 22,
                autoTurnOnMinute = 30,
                autoTurnOffHour = 7,
                autoTurnOffMinute = 25
            ),
            enableWeather = true
        ),
        onAutoOnTimeChanged = { _, _ -> },
        onAutoOffTimeChanged = { _, _ -> },
        onWeatherChanged = {}
    )
}

@Composable
fun DeviceSettingsScreenContent(
    modifier: Modifier = Modifier,
    state: DeviceSettingState,
    onAutoOnTimeChanged: (hour: Int, minute: Int) -> Unit,
    onAutoOffTimeChanged: (hour: Int, minute: Int) -> Unit,
    onWeatherChanged: (toggle: Boolean) -> Unit
) {
    val scrollState = rememberScrollState()
    Column {
        DeviceDetailUpperBody(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 50.dp)
                .verticalScroll(scrollState),
            text = "환경 설정"
        )

        if (state is DeviceSettingState.Loaded) {
            Column(
                verticalArrangement = Arrangement.spacedBy(10.dp),
                modifier = Modifier.padding(10.dp)
            ) {
                TimeOptionItem(
                    modifier = Modifier.fillMaxWidth(),
                    text = "자동 조명 켜짐 시각",
                    description = "해당 시각에 자동으로 무드등의 전원이 켜집니다",
                    hour = state.deviceTimingOption.autoTurnOnHour,
                    minute = state.deviceTimingOption.autoTurnOnMinute,
                    onTimeSet = onAutoOnTimeChanged
                )

                HorizontalDivider()

                TimeOptionItem(
                    modifier = Modifier.fillMaxWidth(),
                    text = "자동 조명 꺼짐 시각",
                    description = "해당 시각에 자동으로 무드등의 전원이 꺼집니다",
                    hour = state.deviceTimingOption.autoTurnOffHour,
                    minute = state.deviceTimingOption.autoTurnOffMinute,
                    onTimeSet = onAutoOffTimeChanged
                )
                HorizontalDivider()

                ToggleOptionItem(
                    modifier = Modifier.fillMaxWidth(),
                    text = "날씨 연동",
                    description = "자동으로 무드등의 전원이 꺼지고 1시간 동안 날씨에 따라 다른 색상을 밝힙니다",
                    toggleState = state.enableWeather,
                    onOptionClick = onWeatherChanged
                )
            }
        }
    }
}
