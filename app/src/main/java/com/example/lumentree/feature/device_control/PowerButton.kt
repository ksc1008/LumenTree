package com.example.lumentree.feature.device_control

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.lumentree.R
import com.example.lumentree.ui.composables.dropShadow
import com.example.lumentree.ui.composables.innerShadow


fun Modifier.addButtonEffect(t: Float) =
    this
        .dropShadow(
            shape = CircleShape,
            blur = 30.dp,
            offsetX = (-30).dp,
            offsetY = (-30).dp,
            spread = 4.dp,
            color = Color.White
        )
        .dropShadow(
            shape = CircleShape,
            blur = (67 - 45 * t).dp,
            offsetX = (15 - 4 * t).dp,
            offsetY = (15 - 4 * t).dp,
            spread = (4 - 12 * t).dp,
            color = Color.Black.copy(alpha = (0.25f - 0.15f * t))
        )

@Composable
fun PowerButton(
    modifier: Modifier = Modifier,
    isOn: Boolean,
    onButtonClick: () -> Unit
) {
    val t by animateFloatAsState(
        targetValue = if (isOn) 1f else 0f,
        label = "Interpolate Value"
    )
    val powerColor by animateColorAsState(
        targetValue = if (isOn) Color(0xFFFFFFFF) else Color(0xff8A90A6),
        label = "Power Icon Color"
    )

    val padding = 20.dp
    val mModifier = Modifier
        .addButtonEffect(t)
        .then(modifier)

    Box(
        modifier = mModifier
            .clip(
                CircleShape
            )
            .innerShadow(
                shape = CircleShape,
                color = Color.White.copy(alpha = t),
                spread = 0.dp,
                offsetX = (-10).dp,
                offsetY = (-10).dp,
                blur = 20.dp
            )
            .innerShadow(
                shape = CircleShape,
                spread = (-25).dp,
                offsetX = 30.dp,
                offsetY = 30.dp,
                blur = 10.dp,
                color = Color.Black.copy(alpha = 0.4f * t)
            )
            .background(Color(0xFFEAEAEA))
            .pointerInput(null) {
                detectTapGestures {
                    onButtonClick()
                }
            }
    ) {
        Image(
            modifier = Modifier
                .align(Alignment.Center)
                .padding(padding),
            painter = painterResource(R.drawable.round_power_settings_new_24),
            colorFilter = ColorFilter.tint(powerColor),
            contentDescription = null
        )
    }
}

@Preview(
    showBackground = true,
    backgroundColor = 0xffededed
)
@Composable
fun PowerButtonPreview() {
    val isOn = true

    Box(modifier = Modifier.size(240.dp, 400.dp)) {
        PowerButton(
            isOn = isOn,
            modifier = Modifier
                .size(160.dp, 160.dp)
                .align(Alignment.Center)
        ) {

        }
    }
}
