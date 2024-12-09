package com.example.lumentree.feature.device_settings

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ToggleOptionItem(
    modifier: Modifier = Modifier,
    toggleState: Boolean,
    text: String,
    description: String?,
    onOptionClick: (option: Boolean) -> Unit
) {
    Row(
        modifier
            .height(75.dp)
            .clickable {
                onOptionClick(!toggleState)
            }
    ) {
        Column(
            modifier = Modifier
                .weight(1f)
                .align(Alignment.CenterVertically)
                .padding(start = 20.dp)
        ) {
            Text(text = text, fontSize = 26.sp, modifier = Modifier.padding(bottom = 2.dp))
            if (description != null) {
                Text(text = description)
            }
        }

        Switch(
            modifier = Modifier
                .align(Alignment.CenterVertically)
                .padding(end = 20.dp),
            onCheckedChange = onOptionClick,
            checked = toggleState
        )
    }
}

@Preview(showBackground = true)
@Composable
fun OptionItemPreview() {
    ToggleOptionItem(
        modifier = Modifier.width(400.dp),
        toggleState = true,
        text = "My Option",
        description = "option description"
    ) { }
}
