package com.example.lumentree.core.model.light

import android.util.Log
import androidx.compose.ui.graphics.Color

class ColorCode {
    val colorCode: String
    val color: Int

    constructor(mColorCode: Int) {
        color = (mColorCode and 0xffffff)
        var cd = Integer.toHexString(color)
        while(cd.length<6){
            cd = "0$cd"
        }
        colorCode = "#${cd}"
        Log.d("KSC", Integer.toHexString(color))
    }

    constructor(mColorCode: String) {
        val code = try {
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
        colorCode = code
        color = android.graphics.Color.parseColor(colorCode)
    }

    fun toComposeColor(intensity: Int = 255): Color = Color((color and 0xffffff).toLong() + (intensity.toLong() shl 24))
}
