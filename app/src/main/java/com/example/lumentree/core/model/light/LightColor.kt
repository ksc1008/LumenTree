package com.example.lumentree.core.model.light

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb

data class LightColor(
    val color: ColorCode,
    val intensity: Int
) {
    fun getComposeColor(): Color = color.toComposeColor(intensity)
    fun toCode(): String {
        var intensityHex = Integer.toHexString(intensity)
        while (intensityHex.length < 2) {
            intensityHex = "0$intensityHex"
        }
        return "$intensityHex${color.colorCode.trimStart('#')}"
    }

    companion object {
        fun fromCode(code: String): LightColor {
            val full = code.toUInt(16)
            val intensity = (full shr 6).toInt()
            val color = (full and 0x00ffffffu).toInt()

            return LightColor(ColorCode(color), intensity)
        }

        fun fromComposeColor(color: Color) = LightColor(
            color = ColorCode(color.toArgb()),
            intensity = (color.alpha * 255).toInt()
        )
    }
}
