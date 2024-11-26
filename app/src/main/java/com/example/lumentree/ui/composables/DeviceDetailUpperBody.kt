package com.example.lumentree.ui.composables

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.lumentree.ui.styles.headerTextStyle

@Composable
fun DeviceDetailUpperBody(
    modifier: Modifier = Modifier,
    text: String
) {
    Column(
        modifier.fillMaxWidth()
    ) {
        Text(
            modifier = Modifier.padding(36.dp),
            text = text,
            style = headerTextStyle
        )
        DeviceImage(
            Modifier
                .width(250.dp)
                .align(Alignment.CenterHorizontally)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun DeviceDetailPreview() {
    Box(Modifier.size(width = 412.dp, height = 917.dp)) {
        DeviceDetailUpperBody(
            modifier = Modifier.align(Alignment.TopCenter),
            text = "헤더 프리뷰"
        )
    }
}

