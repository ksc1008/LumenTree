package com.example.lumentree.feature.device_selector

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.lumentree.R


@Preview
@Composable
private fun DeviceImagePreview() {
    DeviceImageIndicator(
        modifier = Modifier
            .width(400.dp)
            .height(400.dp),
        75.dp,
        false
    ) {

    }
}


@Composable
fun DeviceImageIndicator(modifier: Modifier, offsetY: Dp, hasDevice: Boolean, onClick: () -> Unit) {
    if(hasDevice) {
        Image(
            painter = painterResource(R.drawable.img_mood_light),
            modifier = modifier
                .clip(
                    CircleShape
                )
                .clickable {
                    onClick()
                }
                .background(Color(0xFFFFE5D8))
                .offset(y = offsetY),
            contentDescription = "Light Image Button"
        )
    }
    else{
        Image(
            painter = painterResource(R.drawable.baseline_add_24),
            modifier = modifier
                .clip(
                    CircleShape
                )
                .clickable {
                    onClick()
                }
                .background(Color.LightGray)
                .padding(30.dp),
            contentDescription = "Add Light Button"
        )
    }
}