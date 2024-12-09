package com.example.lumentree.feature.device_selector

import android.util.Log
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import kotlinx.coroutines.delay

@Preview(showBackground = true)
@Composable
fun DeviceSelectorScreenPreview() {
    val device = DeviceSelectorUiState.WithDevice(
        deviceName = "Preview Name",
        autoLightEnabled = true,
        weatherEnabled = false,
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
    viewModel: DeviceSelectorViewModel = hiltViewModel(),
    deviceClick: () -> Unit = {},
    settingButtonClick: () -> Unit = {}
) {
    val uiState = viewModel.uiState.collectAsState()

    LaunchedEffect(uiState.value is DeviceSelectorUiState.WithDevice){
        while(true) {
            delay(1000)
            viewModel.refreshDeviceInfo()
        }
    }

    DeviceSelectorScreenContent(
        modifier = modifier,
        uiState = uiState.value,
        onDeviceNameEditEnd = viewModel::onNameEditFinished,
        onDeviceClick = deviceClick,
        onSettingButtonClick = settingButtonClick
    )
}

@Composable
private fun DeviceSelectorScreenContent(
    modifier: Modifier = Modifier,
    uiState: DeviceSelectorUiState,
    onDeviceClick: () -> Unit = {},
    onDeviceNameEditEnd: (String) -> Unit = {},
    onSettingButtonClick: () -> Unit = {},
) {
    DeviceInfoScreen(
        modifier = modifier,
        state = uiState,
        onDeviceClick = onDeviceClick,
        onDeviceNameEditEnd = onDeviceNameEditEnd,
        onDeviceSettingClick = onSettingButtonClick
    )
}

@Composable
private fun DeviceInfoScreen(
    modifier: Modifier = Modifier,
    state: DeviceSelectorUiState,
    onDeviceClick: () -> Unit,
    onDeviceSettingClick: () -> Unit,
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
                Modifier.size(width = 287.dp, height = 287.dp)
                    .align(Alignment.CenterHorizontally),
                hasDevice = true,
                offsetY = 60.dp,
                onClick = onDeviceClick
            )
            when (state) {
                DeviceSelectorUiState.Connecting ->
                    Text(
                        text = CONNECTING_DESCRIPTION,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .align(Alignment.CenterHorizontally)
                            .padding(top = 20.dp)
                    )

                is DeviceSelectorUiState.Error -> {
                    Text(
                        text = ERROR_DESCRIPTION,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .align(Alignment.CenterHorizontally)
                            .padding(top = 20.dp)
                    )
                    Column(
                        modifier = Modifier
                            .height(400.dp)
                            .align(Alignment.CenterHorizontally)
                            .padding(30.dp)
                            .fillMaxWidth()
                            .verticalScroll(rememberScrollState())
                    ) {
                        Text(
                            text = state.cause.stackTraceToString(),
                            textAlign = TextAlign.Center,
                            modifier = Modifier
                                .align(Alignment.CenterHorizontally)
                                .padding(top = 20.dp)
                        )
                    }
                }

                DeviceSelectorUiState.Fetching ->
                    Text(
                        text = "",
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .align(Alignment.CenterHorizontally)
                            .padding(top = 20.dp)
                    )

                DeviceSelectorUiState.NoDevice -> {
                    Text(
                        text = ADD_DEVICE_DESCRIPTION,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .align(Alignment.CenterHorizontally)
                            .padding(top = 20.dp)
                    )
                }

                is DeviceSelectorUiState.WithDevice ->
                    DeviceInfo(
                        modifier = Modifier.align(Alignment.CenterHorizontally),
                        state = state,
                        editingState = editingState,
                        editable = editable,
                        onEditButtonClick = {
                            editable = true
                            editingState = state.deviceName
                        },
                        onTextEdit = { newValue ->
                            editingState = newValue
                        },
                        onSettingButtonClick = onDeviceSettingClick
                    )
            }
        }
    }

}

@Composable
private fun DeviceInfo(
    modifier: Modifier = Modifier,
    state: DeviceSelectorUiState.WithDevice,
    editingState: String,
    editable: Boolean,
    onEditButtonClick: () -> Unit,
    onTextEdit: (String) -> Unit,
    onSettingButtonClick: () -> Unit
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
            autoLightEnabled = state.autoLightEnabled,
            weatherEnabled = state.weatherEnabled,
            wifiEnabled = state.wifiEnabled,
            iconSize = 16.dp
        )

        Button(
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(top = 30.dp)
                .alpha(if (state is DeviceSelectorUiState.WithDevice) 1f else 0f),
            onClick = onSettingButtonClick
        ) {
            Text(SETTING_BUTTON_TEXT)
        }

    }
}
