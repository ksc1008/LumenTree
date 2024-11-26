package com.example.lumentree.core.model.light

import androidx.compose.ui.graphics.Color

data class LightColor(
    val lightName: String,
    val color: ColorCode
) {
    fun getComposeColor(): Color = color.toComposeColor()
}
