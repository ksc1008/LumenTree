package com.example.lumentree.feature.device_colors

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
    colorInfo: DeviceColorInfo,
    onClick: (colorState: ColorState) -> Unit
    ) {
    Row(
        modifier
            .fillMaxWidth()
            .height(50.dp)
            .clickable {
                onClick(colorInfo.state)
            },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            modifier = Modifier
                .padding(end = 12.dp)
                .size(20.dp),
            painter = painterResource(mapWeatherStateToResource(colorInfo.state)),
            contentDescription = null,
            colorFilter = ColorFilter.tint(Color.Black)
        )
        Box(
            modifier = Modifier
                .padding(end = 29.dp)
                .clip(CircleShape)
                .border(
                    width = 1.dp,
                    color = Color(0x33000000),
                    CircleShape
                )
                .size(width = 48.dp, height = 48.dp)
                .padding(1.dp)
                .background(
                    SolidColor(colorInfo.getComposeColor())
                )
        )
        Text(
            text = WeatherStateToNameDict[colorInfo.state]?:"알 수 없음"
        )

    }
}

@Preview(showBackground = true)
@Composable
fun DeviceColorItemPreview() {
    DeviceColorItem(
        Modifier.height(50.dp),
        DeviceColorInfo(color = LightColor(ColorCode("#fff666"), 255), ColorState.SUNNY)
    ){

    }
}

private fun mapWeatherStateToResource(colorState: ColorState): Int =
    when (colorState) {
        ColorState.NIGHT -> R.drawable.rounded_nightlight_24
        ColorState.SUNNY -> R.drawable.rounded_sunny_24
        ColorState.CLOUDY -> R.drawable.rounded_cloud_24
        ColorState.RAINY -> R.drawable.rounded_rainy_24
        ColorState.SNOWY -> R.drawable.rounded_weather_snowy_24
    }

private val WeatherStateToNameDict = mapOf(
    Pair(ColorState.NIGHT, "야간"),
    Pair(ColorState.SUNNY, "맑은 날"),
    Pair(ColorState.CLOUDY, "흐린 날"),
    Pair(ColorState.RAINY, "비 오는 날"),
    Pair(ColorState.SNOWY, "눈 오는 날"),
)
