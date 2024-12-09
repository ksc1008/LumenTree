package com.example.lumentree.feature.device_colors.colorpicker

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.lumentree.R
import com.example.lumentree.core.model.device.ColorState
import com.example.lumentree.core.model.light.LightColor
import com.example.lumentree.feature.device_colors.createPreviewList
import com.github.skydoves.colorpicker.compose.AlphaSlider
import com.github.skydoves.colorpicker.compose.HsvColorPicker
import com.github.skydoves.colorpicker.compose.rememberColorPickerController
import kotlinx.coroutines.delay

@Composable
fun ColorPickerDialog(
    state: ColorPickerDialogState.DialogOpen,
    onSelectedColorChanged: (color: Color) -> Unit,
    onApply: () -> Unit,
    onDismiss: () -> Unit,
    onPreviewButtonClick: (on: Boolean) -> Unit,
    onTick: () -> Unit
) {

    val controller = rememberColorPickerController()

    Dialog(onDismissRequest = onDismiss) {
        LaunchedEffect(Unit) {
            controller.selectByColor(state.selectedColor.getComposeColor(), true)
            while (true) {
                onTick()
                delay(300)
            }
        }

        Box(
            modifier = Modifier
                .size(
                    width = 400.dp,
                    height = 450.dp
                )
                .background(
                    Color.White,
                    shape = RoundedCornerShape(10.dp)
                )

        ) {
            Column(
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .fillMaxWidth()
            ) {
                Box(
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .fillMaxWidth()
                ) {
                    Text(
                        text = "색상 선택 (${state.weatherName})",
                        modifier = Modifier
                            .padding(8.dp)
                            .align(Alignment.Center),
                        fontSize = 22.sp
                    )

                    IconButton(
                        onClick = {
                            onPreviewButtonClick(!state.showingPreview)
                        },
                        Modifier
                            .padding(end = 5.dp)
                            .align(Alignment.CenterEnd)
                            .size(50.dp)
                    ) {
                        Icon(
                            painter = if (state.showingPreview)
                                painterResource(R.drawable.baseline_lightbulb_24)
                            else
                                painterResource(R.drawable.baseline_lightbulb_outline_24),
                            tint = if (state.showingPreview) Color(0xffffffee) else Color.Black,
                            contentDescription = "show preview",
                            modifier = Modifier
                                .background(
                                    if (state.showingPreview)
                                        Color(0xaa0022aa)
                                    else
                                        Color(0x20000000),
                                    RoundedCornerShape(8.dp)
                                )
                                .padding(3.dp)
                                .size(30.dp)
                        )
                    }

                }
                Spacer(Modifier.padding(10.dp))
                HsvColorPicker(
                    modifier = Modifier
                        .size(240.dp)
                        .align(Alignment.CenterHorizontally)
                        .padding(10.dp),
                    controller = controller,
                    onColorChanged = { onSelectedColorChanged(it.color) }
                )

                Spacer(Modifier.padding(10.dp))

                AlphaSlider(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 15.dp, vertical = 10.dp)
                        .height(20.dp)
                        .align(Alignment.CenterHorizontally),
                    controller = controller,
                    tileEvenColor = Color.Black,
                    tileOddColor = Color.Black,
                    borderColor = Color.Black
                )
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
                    .align(Alignment.BottomCenter)
            ) {
                TextButton(
                    modifier = Modifier
                        .weight(1f)
                        .padding(4.dp),
                    onClick = onDismiss
                ) {
                    Text("취소")
                }
                TextButton(
                    modifier = Modifier
                        .weight(1f)
                        .padding(4.dp),
                    onClick = onApply
                ) {
                    Text("적용")
                }
            }
        }
    }
}

@Preview
@Composable
fun ColorPickerPreview() {
    val colorList = createPreviewList().second
        .map { it.color }
    ColorPickerDialog(
        state = ColorPickerDialogState.DialogOpen(
            weather = ColorState.NIGHT,
            selectedColor = LightColor.fromCode("ffbbbb"),
            showingPreview = true,
            weatherName = "맑음"
        ),
        onPreviewButtonClick = {},
        onSelectedColorChanged = {},
        onDismiss = {},
        onTick = {},
        onApply = {}
    )
}
