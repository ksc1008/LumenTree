package com.example.lumentree.feature.device_control

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.lumentree.ui.composables.DeviceDetailUpperBody

@Composable
fun DeviceControlScreen(
    modifier: Modifier = Modifier,
    viewModel: DeviceControlViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    DeviceControlScreenContent(
        modifier = modifier,
        state = uiState,
        powerButtonClick = viewModel::clickPowerButton,
        autoSwitchClick = viewModel::clickAutoSwitch
    )
}

@Preview(showBackground = true)
@Composable
fun DeviceControlScreenPreview() {
    var deviceOn by remember { mutableStateOf(true) }
    var deviceAuto by remember { mutableStateOf(true) }

    DeviceControlScreenContent(
        Modifier.fillMaxSize(),
        state = DeviceControlUiState.Ready(
            deviceName = "Preview Device",
            isOn = deviceOn,
            isAuto = deviceAuto
        ),
        powerButtonClick = { deviceOn = !deviceOn },
        autoSwitchClick = { deviceAuto = !deviceAuto }
    )
}

@Composable
private fun DeviceControlScreenContent(
    modifier: Modifier = Modifier,
    state: DeviceControlUiState,
    powerButtonClick: () -> Unit,
    autoSwitchClick: () -> Unit
) {
    Column(modifier = modifier) {
        val headerText = if (state is HasDeviceControlState) state.deviceName else ""

        DeviceDetailUpperBody(
            modifier = Modifier
                .fillMaxWidth(),
            text = headerText
        )

        Spacer(modifier = Modifier.padding(top = 135.dp))
        if (state is HasDeviceControlState) {
            PowerButton(
                modifier = Modifier
                    .size(160.dp, 160.dp)
                    .align(Alignment.CenterHorizontally),
                isOn = state.isOn,
                onButtonClick = powerButtonClick
            )
            Spacer(modifier = Modifier.padding(top = 53.dp))

            Row(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
            ) {
                Text(
                    text = "Auto Switch",
                    textAlign = TextAlign.End,
                    modifier = Modifier.align(Alignment.CenterVertically)
                )
                Spacer(modifier = Modifier.padding(start = 8.dp))
                Switch(
                    checked = state.isAuto,
                    onCheckedChange = { autoSwitchClick() },
                    modifier = Modifier.align(Alignment.CenterVertically)
                )
            }
        }
    }

}
