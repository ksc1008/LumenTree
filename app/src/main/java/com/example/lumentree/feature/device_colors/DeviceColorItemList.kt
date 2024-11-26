package com.example.lumentree.feature.device_colors

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.lumentree.R
import com.example.lumentree.core.model.device.ColorState
import com.example.lumentree.core.model.device.DeviceColorInfo

@Composable
fun DeviceColorItemList(
    modifier: Modifier = Modifier,
    colorItemListUpper: List<DeviceColorInfo>,
    colorItemListLower: List<DeviceColorInfo>
) {

}

@Composable
fun DeviceColorItem(
    modifier: Modifier,
    colorInfo: DeviceColorInfo
) {

}

private fun mapWeatherStateToResource(colorState: ColorState): Int =
    when(colorState){
        ColorState.NIGHT -> R.drawable.rounded_nightlight_24
        ColorState.SUNNY -> R.drawable.rounded_sunny_24
        ColorState.CLOUDY -> R.drawable.rounded_cloud_24
        ColorState.RAINY -> R.drawable.rounded_rainy_24
        ColorState.SNOWY -> R.drawable.rounded_weather_snowy_24
        ColorState.HOTTER_THAN_YESTERDAY -> R.drawable.rounded_brightness_high_24
        ColorState.COLDER_THAN_YESTERDAY -> R.drawable.rounded_mode_cool_24
    }
