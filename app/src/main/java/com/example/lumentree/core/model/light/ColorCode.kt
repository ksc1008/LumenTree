package com.example.lumentree.core.model.light

import androidx.compose.ui.graphics.Color

class ColorCode(mColorCode: String) {
    val colorCode: String
    val color: Int

    fun toComposeColor(): Color = Color(color)

    init {
        colorCode = try {
            if (!mColorCode.startsWith('#')) {
                Color(android.graphics.Color.parseColor("#$mColorCode"))
                "#$mColorCode"
            } else {
                Color(android.graphics.Color.parseColor(mColorCode))
                mColorCode
            }
        } catch (e: Exception) {
            "#000000"
        }

        color = android.graphics.Color.parseColor(colorCode)
    }
}
