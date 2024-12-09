package com.example.lumentree.feature.device_colors.colorpicker

import com.example.lumentree.core.model.device.ColorState
import com.example.lumentree.core.model.light.LightColor

sealed class ColorPickerDialogState{
    data class DialogOpen(
        val weather: ColorState,
        val selectedColor: LightColor,
        val showingPreview: Boolean,
        val weatherName: String
    ) : ColorPickerDialogState()

    data object DialogClose : ColorPickerDialogState()
}
