package com.example.lumentree.feature.device_selector

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.lumentree.R
import com.example.lumentree.ui.theme.IconBackground
import com.example.lumentree.ui.theme.LumenTreeTheme

@Preview(showBackground = true)
@Composable
fun DeviceNamePreview() {
    LumenTreeTheme {
        DeviceNameField(
            modifier = Modifier
                .width(120.dp)
                .height(30.dp),
            editable = false,
            name = "KSC Mood Light"
        )
    }
}

@Composable
fun DeviceNameField(modifier: Modifier = Modifier, name: String, editable: Boolean) {
    var text by remember { mutableStateOf(name) }
    if (editable) {
        TextField(
            modifier = modifier, value = text, onValueChange = { text = it }
        )
    } else {
        Row(modifier = modifier,
            horizontalArrangement = Arrangement.spacedBy(0.dp)) {
            Text(
                text = text,
                modifier = Modifier.align(Alignment.CenterVertically)
                    .width(0.dp)
                    .weight(1f),
                overflow = TextOverflow.Ellipsis
            )
            IconButton(modifier = Modifier.align(Alignment.CenterVertically)
                .width(IntrinsicSize.Max),
                onClick = {

                }) {
                Image(
                    painterResource(R.drawable.round_edit_24),
                    contentDescription = "edit icon button",
                    colorFilter = ColorFilter.tint(IconBackground)
                )
            }
        }
    }
}