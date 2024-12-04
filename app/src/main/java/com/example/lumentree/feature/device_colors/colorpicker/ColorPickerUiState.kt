package com.example.lumentree.feature.device_colors.colorpicker

import com.example.lumentree.core.model.light.LightColor

data class ColorPickerUiState(
    val colorPresetList: List<LightColor>,
    val selectedColor: LightColor
)
