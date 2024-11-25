package com.example.lumentree.feature.device_selector

import android.util.Log
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel

@Preview(showBackground = true)
@Composable
fun DeviceSelectorScreenPreview() {
    DeviceSelectorScreenContent(
        Modifier.fillMaxSize(),
        DeviceSelectorUiState.WithDevice(
            deviceName = "Preview Name",
            autoLightEnabled = true,
            asmrEnabled = false,
            wifiEnabled = true
        )
    )
}

@Composable
fun DeviceSelectorScreen(
    modifier: Modifier = Modifier,
    viewModel: DeviceSelectorViewModel = hiltViewModel()
) {
    val uiState = viewModel.uiState.collectAsState()

    DeviceSelectorScreenContent(
        modifier = modifier,
        uiState = uiState.value,
        onDeviceNameEditEnd = viewModel::onNameEditFinished,
        onDeviceClick = viewModel::onDeviceClick
    )
}

@Composable
private fun DeviceSelectorScreenContent(
    modifier: Modifier = Modifier,
    uiState: DeviceSelectorUiState,
    onDeviceClick: () -> Unit = {},
    onDeviceNameEditEnd: (String) -> Unit = {}
) {
    when (uiState) {
        is DeviceSelectorUiState.Fetching -> {

        }

        is DeviceSelectorUiState.NoDevice -> {

        }

        is DeviceSelectorUiState.Error -> {

        }

        is DeviceSelectorUiState.WithDevice -> {
            DeviceInfoScreen(
                modifier = modifier,
                state = uiState,
                onDeviceClick = onDeviceClick,
                onDeviceNameEditEnd = onDeviceNameEditEnd
            )
        }

    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
private fun DeviceInfoScreen(
    modifier: Modifier = Modifier,
    state: DeviceSelectorUiState.WithDevice,
    onDeviceClick: () -> Unit,
    onDeviceNameEditEnd: (String) -> Unit
) {
    var editable by remember { mutableStateOf(false) }
    var editingState by remember { mutableStateOf("") }

    Box(
        modifier
            .fillMaxSize()
            .pointerInput(Unit) {
                detectTapGestures {
                    if(editable) {
                        editable = false
                        onDeviceNameEditEnd(editingState)
                        Log.d("KSC", "Edit End!")
                    }
                }
            },
        contentAlignment = Alignment.Center,
    ) {
        Column(verticalArrangement = Arrangement.Center) {
            DeviceNameField(
                Modifier
                    .align(Alignment.CenterHorizontally)
                    .wrapContentSize()
                    .padding(bottom = 15.dp)
                    .wrapContentHeight(),
                name = state.deviceName,
                editingName = editingState,
                editable = editable,
                onEditButtonClick = {
                    editingState = state.deviceName
                    editable = true
                },
                onValueChanged = { editingState = it }
            )

            DeviceStateIndicator(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally),
                autoLightEnabled = true,
                asmrEnabled = true,
                wifiEnabled = true,
                iconSize = 16.dp
            )
        }
    }

}
