package com.example.lumentree.feature.device_colors

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.lumentree.core.model.device.ColorState
import com.example.lumentree.feature.device_colors.colorpicker.ColorPickerDialog
import com.example.lumentree.feature.device_colors.colorpicker.ColorPickerDialogState
import com.example.lumentree.ui.composables.DeviceDetailUpperBody

@Composable
fun DeviceColorsScreen(
    modifier: Modifier = Modifier,
    viewModel: DeviceColorsViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val dialogState by viewModel.dialogState.collectAsState()

    (dialogState as? ColorPickerDialogState.DialogOpen)?.let {
        ColorPickerDialog(
            state = it,
            onDismiss = viewModel::clickDismissButton,
            onTick = viewModel::tick,
            onApply = viewModel::clickApplyButton,
            onPreviewButtonClick = { on -> viewModel.setLightPreviewButton(on) },
            onSelectedColorChanged = viewModel::updateSelectedColor
        )
    }
    DeviceColorsScreenContent(
        modifier = modifier,
        uiState,
        colorItemClick = viewModel::clickColorItem
    )
}

@Preview(showBackground = true)
@Composable
private fun DeviceColorsScreenPreview() {
    val previewColors = createPreviewList()
    DeviceColorsScreenContent(
        Modifier.fillMaxSize(),
        state = DeviceColorUIState.Fetched(
            deviceName = "123",
            deviceColorsUpper = previewColors.first,
            deviceColorsLower = previewColors.second
        ),
        colorItemClick = {}
    )
}

@Composable
private fun DeviceColorsScreenContent(
    modifier: Modifier = Modifier,
    state: DeviceColorUIState,
    colorItemClick: (colorState: ColorState) -> Unit
) {
    Column(modifier = modifier) {
        DeviceDetailUpperBody(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 50.dp),
            text = "테마 설정"
        )

        (state as? DeviceColorUIState.Fetched)?.let {
            DeviceColorItemList(
                modifier = Modifier.weight(1f),
                colorItemListUpper = it.deviceColorsUpper,
                colorItemListLower = it.deviceColorsLower,
                colorItemClick = colorItemClick
            )
        }
    }
}
