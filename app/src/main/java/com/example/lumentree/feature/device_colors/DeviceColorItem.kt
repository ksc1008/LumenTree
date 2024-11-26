package com.example.lumentree.feature.device_colors

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.lumentree.R
import com.example.lumentree.core.model.device.ColorState
import com.example.lumentree.core.model.device.DeviceColorInfo
import com.example.lumentree.core.model.light.ColorCode
import com.example.lumentree.core.model.light.LightColor

@Composable
fun DeviceColorItem(
    modifier: Modifier,
    colorInfo: DeviceColorInfo
) {
    Row(
        modifier.fillMaxWidth()
            .height(50.dp)
            .padding(horizontal = 21.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            modifier = Modifier
                .size(24.dp)
                .padding(end = 12.dp),
            painter = painterResource(mapWeatherStateToResource(colorInfo.state)),
            contentDescription = null,
            colorFilter = ColorFilter.tint(Color.Black)
        )
        Box(
            modifier = Modifier
                .padding(end=29.dp)
                .clip(CircleShape)
                .border(
                    width = 1.dp,
                    color = Color(0x70000000),
                    CircleShape
                )
                .size(width = 48.dp, height = 48.dp)
                .background(
                    SolidColor(colorInfo.getComposeColor())
                )
        )
        Text(
            text = colorInfo.color.lightName
        )

    }
}

@Preview(showBackground = true)
@Composable
fun DeviceColorItemPreview(){
    DeviceColorItem(
        Modifier.height(50.dp),
        DeviceColorInfo(color = LightColor("밝은 노란색", ColorCode("#fff666")), ColorState.SUNNY)
    )
}

private fun mapWeatherStateToResource(colorState: ColorState): Int =
    when (colorState) {
        ColorState.NIGHT -> R.drawable.rounded_nightlight_24
        ColorState.SUNNY -> R.drawable.rounded_sunny_24
        ColorState.CLOUDY -> R.drawable.rounded_cloud_24
        ColorState.RAINY -> R.drawable.rounded_rainy_24
        ColorState.SNOWY -> R.drawable.rounded_weather_snowy_24
        ColorState.HOTTER_THAN_YESTERDAY -> R.drawable.rounded_brightness_high_24
        ColorState.COLDER_THAN_YESTERDAY -> R.drawable.rounded_mode_cool_24
    }
