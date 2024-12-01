package com.example.lumentree.feature.device_control

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.lumentree.R
import com.example.lumentree.ui.composables.dropShadow
import com.example.lumentree.ui.composables.innerShadow


fun Modifier.addButtonOffEffect() =
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
            blur = 67.dp,
            offsetX = 15.dp,
            offsetY = 15.dp,
            spread = 4.dp
        )

fun Modifier.addButtonOnEffect() =
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
            blur = 22.dp,
            offsetX = 11.dp,
            offsetY = 11.dp,
            spread = (-8).dp,
            color = Color.Black.copy(alpha = 0.1f)
        )


@Composable
fun PowerButton(
    modifier: Modifier = Modifier,
    isOn: Boolean,
    onButtonClick: () -> Unit
) {
    val padding = 20.dp
    val mModifier = (if(isOn) Modifier.addButtonOnEffect() else Modifier.addButtonOffEffect())
        .then(modifier)
    if(isOn){
        Box(
            modifier = mModifier
                .clip(
                    CircleShape
                )
                .innerShadow(
                    shape = CircleShape,
                    color = Color.White,
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
                    color = Color.Black.copy(alpha = 0.4f)
                )
                .background(Color(0xFFEAEAEA))
                .clickable(onClick = onButtonClick)
        ){
            Image(
                modifier = Modifier.align(Alignment.Center)
                    .padding(padding),
                painter = painterResource(R.drawable.round_power_settings_new_24),
                colorFilter = ColorFilter.tint(Color(0xFFFFFFFF)),
                //colorFilter = ColorFilter.tint(Color(0xFF9CEF9F)),
                contentDescription = null
            )
        }
    }
    else{
        Box(
            modifier = mModifier
                .clip(
                    CircleShape
                )
                .background(Color(0xFFEAEAEA))
        ){
            Image(
                modifier = Modifier.align(Alignment.Center)
                    .padding(padding),
                painter = painterResource(R.drawable.round_power_settings_new_24),
                colorFilter = ColorFilter.tint(Color(0xff8A90A6)),
                contentDescription = null
            )
        }
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
        ){

        }
    }
}
