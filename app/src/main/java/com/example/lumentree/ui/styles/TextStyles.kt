package com.example.lumentree.ui.styles

import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType

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

val headerTextStyle: TextStyle =
    TextStyle(
        textAlign = TextAlign.Start,
        lineHeight = TextUnit(type = TextUnitType.Sp, value = 40f),
        fontSize = TextUnit(type = TextUnitType.Sp, value = 32f)
    )