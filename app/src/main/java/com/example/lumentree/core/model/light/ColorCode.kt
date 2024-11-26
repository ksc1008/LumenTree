package com.example.lumentree.core.model.light

import androidx.compose.ui.graphics.Color

class ColorCode(mColorCode: String) {
    val colorCode: String

    fun toComposeColor(): Color {
        return try {
            if (!colorCode.startsWith('#')) {
                Color(android.graphics.Color.parseColor("#$colorCode"))
            } else {
                Color(android.graphics.Color.parseColor(colorCode))
            }
        } catch (e:Exception) {
            Color(0x00000000)
        }

    }

    init {
        colorCode = mColorCode
    }
}
