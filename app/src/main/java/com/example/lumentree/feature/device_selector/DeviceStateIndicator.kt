package com.example.lumentree.feature.device_selector

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.lumentree.R
import com.example.lumentree.ui.theme.IconBackground
import com.example.lumentree.ui.theme.IconColorDisabled
import com.example.lumentree.ui.theme.IconColorEnabled
import com.example.lumentree.ui.theme.LumenTreeTheme

@Preview(showBackground = true)
@Composable
fun DeviceIndicatorPreview() {
    LumenTreeTheme {
        DeviceStateIndicator(
            wifiEnabled = true,
            autoLightEnabled = true,
            asmrEnabled = true,
            iconSize = 22.dp
        )
    }
}

@Composable
fun DeviceStateIndicator(
    modifier: Modifier = Modifier,
    wifiEnabled: Boolean,
    autoLightEnabled: Boolean,
    asmrEnabled: Boolean,
    iconSize: Dp = 0.dp
) {
    Row(
        modifier = modifier
            .padding(
                horizontal = 7.dp, vertical = 5.dp
            ),
        horizontalArrangement = Arrangement.spacedBy(20.dp),
    ) {
        IndicatorIcon(
            painter = painterResource(R.drawable.round_wifi_24),
            enabled = wifiEnabled,
            contentDescription = "WiFi",
            size = iconSize
        )
        IndicatorIcon(
            painter = painterResource(R.drawable.round_nightlight_24),
            enabled = autoLightEnabled,
            contentDescription = "Auto Switch",
            size = iconSize
        )
        IndicatorIcon(
            painter = painterResource(R.drawable.round_volume_up_24),
            enabled = asmrEnabled,
            contentDescription = "Sound",
            size = iconSize
        )
    }
}

@Composable
fun IndicatorIcon(
    painter: Painter,
    enabled: Boolean,
    contentDescription: String,
    size: Dp = 0.dp
) {
    Box(
        modifier = Modifier
            .background(
                IconBackground,
                shape = CircleShape
            )
            .padding(5.dp)
    ) {
        val color = if (enabled) IconColorEnabled else IconColorDisabled

        if (size == 0.dp) {
            Image(
                painter,
                contentDescription = contentDescription,
                colorFilter = ColorFilter.tint(color),
                modifier = Modifier
                    .padding(2.dp)
                    .width(IntrinsicSize.Max)
                    .height(IntrinsicSize.Max)
            )
        } else {
            Image(
                painter,
                contentDescription = contentDescription,
                colorFilter = ColorFilter.tint(color),
                modifier = Modifier
                    .padding(2.dp)
                    .width(size)
                    .height(size)
            )
        }
    }
}