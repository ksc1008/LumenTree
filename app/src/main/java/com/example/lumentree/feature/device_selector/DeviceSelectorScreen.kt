package com.example.lumentree.feature.device_selector

import android.util.Log
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel

@Preview(showBackground = true)
@Composable
fun DeviceSelectorScreenPreview() {
    val device = DeviceSelectorUiState.WithDevice(
        deviceName = "Preview Name",
        autoLightEnabled = true,
        asmrEnabled = false,
        wifiEnabled = true
    )
    val noDevice = DeviceSelectorUiState.NoDevice
    DeviceSelectorScreenContent(
        Modifier.fillMaxSize(),
        device
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
            DeviceInfoScreen(
                modifier = modifier,
                state = uiState,
                onDeviceClick = onDeviceClick,
                onDeviceNameEditEnd = onDeviceNameEditEnd
            )

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

@Composable
private fun DeviceInfoScreen(
    modifier: Modifier = Modifier,
    state: DeviceSelectorUiState,
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
                    if (editable) {
                        editable = false
                        onDeviceNameEditEnd(editingState)
                        Log.d("KSC", "Edit End!")
                    }
                }
            },
        contentAlignment = Alignment.Center,
    ) {
        Column(verticalArrangement = Arrangement.Center) {
            DeviceImageIndicator(
                Modifier.size(width = 287.dp, height = 287.dp),
                hasDevice = state is DeviceSelectorUiState.WithDevice,
                offsetY = 60.dp,
                onClick = onDeviceClick
            )
            (state as? DeviceSelectorUiState.WithDevice)?.let {
                DeviceInfo(
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    state = it,
                    editingState = editingState,
                    editable = editable,
                    onEditButtonClick = {
                        editable = true
                        editingState = it.deviceName
                    }
                ) { newValue ->
                    editingState = newValue
                }
            } ?: Text(
                text = ADD_DEVICE_DESCRIPTION,
                textAlign = TextAlign.Center,
                modifier = Modifier.align(Alignment.CenterHorizontally)
                    .padding(top = 20.dp)
            )
        }
    }

}

@Composable
private fun DeviceInfo(
    modifier: Modifier = Modifier,
    state: DeviceSelectorUiState,
    editingState: String,
    editable: Boolean,
    onEditButtonClick: () -> Unit,
    onTextEdit: (String) -> Unit
) {
    Column(verticalArrangement = Arrangement.Top, modifier = modifier) {
        DeviceNameField(
            Modifier
                .align(Alignment.CenterHorizontally)
                .wrapContentSize()
                .padding(bottom = 15.dp, top = 30.dp)
                .wrapContentHeight(),
            name = (state as? DeviceSelectorUiState.WithDevice)?.deviceName ?: "",
            editingName = editingState,
            editable = editable,
            onEditButtonClick = {
                onEditButtonClick()
            },
            onValueChanged = onTextEdit
        )

        DeviceStateIndicator(
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(bottom = 50.dp),
            autoLightEnabled = true,
            asmrEnabled = true,
            wifiEnabled = true,
            iconSize = 16.dp
        )

        Button(
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(top = 30.dp)
                .alpha(if (state is DeviceSelectorUiState.WithDevice) 1f else 0f),
            onClick = {}
        ) {
            Text(SETTING_BUTTON_TEXT)
        }

    }
}

@Composable
private fun AddDeviceScreen(
    modifier: Modifier = Modifier,
    onAddButtonClick: () -> Unit
) {

}