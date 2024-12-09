package com.example.lumentree.core.model.device

import com.example.lumentree.core.model.light.LightColor

enum class ColorState { NIGHT, SUNNY, RAINY, CLOUDY, SNOWY }

data class DeviceColorInfo(
    val color: LightColor,
    val state: ColorState
) {
    fun getComposeColor() = color.getComposeColor()
}
