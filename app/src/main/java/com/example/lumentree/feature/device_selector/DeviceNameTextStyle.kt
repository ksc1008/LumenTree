package com.example.lumentree.feature.device_selector

import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp

val deviceNameTextStyle: TextStyle =
    TextStyle(
        textAlign = TextAlign.Center,
        lineHeight = TextUnit(type = TextUnitType.Sp, value = 36f),
        fontSize = TextUnit(type = TextUnitType.Sp, value = 28f)
    )

val deviceNameEditTextStyle: TextStyle =
    TextStyle(
        textAlign = TextAlign.Center,
        lineHeight = TextUnit(type = TextUnitType.Sp, value = 20f),
        fontSize = TextUnit(type = TextUnitType.Sp, value = 24f)
    )
