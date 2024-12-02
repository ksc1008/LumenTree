package com.example.lumentree.feature.device_colors

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.lumentree.core.model.device.ColorState
import com.example.lumentree.core.model.device.DeviceColorInfo
import com.example.lumentree.core.model.light.ColorCode
import com.example.lumentree.core.model.light.LightColor

@Composable
fun DeviceColorItemList(
    modifier: Modifier = Modifier,
    colorItemListUpper: List<DeviceColorInfo>,
    colorItemListLower: List<DeviceColorInfo>,
    colorItemClick: (colorState: ColorState) -> Unit
) {
    LazyColumn(
        modifier
            .fillMaxWidth()
            .padding(horizontal = 21.dp)
    ) {
        item {
            Text(
                modifier = Modifier.padding(bottom = 14.dp),
                text = NIGHTTIME_LABEL
            )
        }

        items(colorItemListUpper) { item ->
            DeviceColorItem(modifier = Modifier, item, onClick = colorItemClick)
        }

        item {
            HorizontalDivider(
                modifier = Modifier.padding(vertical = 15.dp),
                thickness = 1.dp
            )
        }

        item {
            Text(
                modifier = Modifier.padding(bottom = 14.dp),
                text = DAYTIME_LABEL
            )
        }

        items(colorItemListLower) { item ->
            DeviceColorItem(
                modifier = Modifier
                    .padding(bottom = 22.dp),
                colorInfo = item,
                onClick = colorItemClick
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DeviceColorItemListPreview() {
    val previewLists = createPreviewList()

    DeviceColorItemList(
        Modifier
            .size(width = 412.dp, height = 600.dp),
        colorItemListUpper = previewLists.first,
        colorItemListLower = previewLists.second
    ) {

    }
}

internal fun createPreviewList(): Pair<List<DeviceColorInfo>, List<DeviceColorInfo>> {
    val upper = listOf(
        DeviceColorInfo(
            state = ColorState.NIGHT,
            color = LightColor(
                "따뜻한 톤",
                color = ColorCode("FFF0D4")
            )
        )
    )

    val lower = listOf(
        DeviceColorInfo(
            state = ColorState.SUNNY,
            color = LightColor(
                "밝은 노랑색",
                color = ColorCode("FFFCA0")
            )
        ),
        DeviceColorInfo(
            state = ColorState.CLOUDY,
            color = LightColor(
                "청록색",
                color = ColorCode("ADFCFF")
            )
        ),
        DeviceColorInfo(
            state = ColorState.RAINY,
            color = LightColor(
                "파랑색",
                color = ColorCode("5FA6E9")
            )
        )
    )

    return Pair(upper, lower)
}
