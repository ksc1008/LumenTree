package com.example.lumentree.feature.device_selector

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel

@Preview
@Composable
fun DeviceSelectorScreenPreview() {
    DeviceSelectorScreen(

    )
}

@Composable
fun DeviceInfo(
    state: DeviceSelectorUiState.WithDevice,
    onDeviceClick: () -> Unit
) {
    DeviceNameField(
        Modifier
            .width(300.dp)
            .height(50.dp),
        state.deviceName,
        true
    ) {
    }

    DeviceStateIndicator(
        modifier = Modifier.width(300.dp),
        autoLightEnabled = true,
        asmrEnabled = true,
        wifiEnabled = true
    )
}

@Composable
fun DeviceSelectorScreen(
    modifier: Modifier = Modifier,
    viewModel: DeviceSelectorViewModel = viewModel()
) {
    val uiState = viewModel.uiState.collectAsState()
    Column(modifier = modifier) {
        when (val state = uiState.value) {
            is DeviceSelectorUiState.Fetching -> {

            }

            is DeviceSelectorUiState.NoDevice -> {

            }

            is DeviceSelectorUiState.Error -> {

            }

            is DeviceSelectorUiState.WithDevice -> {
                DeviceInfo(state) {

                }
            }

        }
    }
}
