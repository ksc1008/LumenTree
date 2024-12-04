package com.example.lumentree.feature.device_colors.colorpicker

import android.graphics.drawable.GradientDrawable
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.painter.BrushPainter
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.lumentree.feature.device_colors.createPreviewList

@Composable
fun ColorPicker(modifier: Modifier = Modifier, state: ColorPickerUiState) {
    Column(
        modifier = modifier
            .background(
                Color.White,
                shape = RoundedCornerShape(10.dp)
            )
    ) {
        Row(
            modifier = Modifier.fillMaxWidth()
                .padding(top = 5.dp),
            horizontalArrangement = Arrangement.spacedBy(10.dp, Alignment.CenterHorizontally)
        ) {
            state.colorPresetList.forEach {
                Box(
                    modifier = Modifier
                        .clip(CircleShape)
                        .border(
                            width = if (it == state.selectedColor) 2.dp else 1.dp,
                            color = Color.Black.copy(
                                alpha = if (it == state.selectedColor) 0.8f else 0.2f
                            ),
                            CircleShape
                        )
                        .size(width = 24.dp, height = 24.dp)
                        .padding(1.dp)
                        .background(
                            SolidColor(it.getComposeColor())
                        )
                )
            }
            Box(
                modifier = Modifier
                    .clip(CircleShape)
                    .size(width = 24.dp, height = 24.dp)
                    .padding(1.dp)
                    .background(
                        Brush.radialGradient(
                            colors = (0..100).map {
                                Color.hsv(0.01f * it,1f,1f,1f)
                            }
                        )
                    )
            )
        }

    }
}

@Preview
@Composable
fun ColorPickerPreview() {
    val colorList = createPreviewList().second
        .map { it.color }
    ColorPicker(
        modifier = Modifier.size(
            width = 200.dp,
            height = 120.dp
        ),
        state = ColorPickerUiState(
            colorPresetList = colorList,
            selectedColor = colorList[1]
        )
    )
}