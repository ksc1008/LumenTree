package com.example.lumentree.feature.device_colors

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.lumentree.core.model.device.DeviceColorInfo

@Composable
fun DeviceColorItemList(
    modifier: Modifier = Modifier,
    colorItemListUpper: List<DeviceColorInfo>,
    colorItemListLower: List<DeviceColorInfo>
) {
    LazyColumn(modifier.fillMaxWidth()) {

    }
}

