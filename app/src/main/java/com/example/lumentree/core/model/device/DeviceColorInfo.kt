package com.example.lumentree.core.model.device

import com.example.lumentree.core.model.light.LightColor

enum class ColorState { NIGHT, SUNNY, CLOUDY, RAINY, SNOWY, HOTTER_THAN_YESTERDAY, COLDER_THAN_YESTERDAY }

data class DeviceColorInfo(
    val color: LightColor,
    val state: ColorState
) {
    fun getComposeColor() = color.getComposeColor()
}
