package com.example.lumentree.feature.device_selector

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.lumentree.R
import com.example.lumentree.ui.theme.LumenTreeTheme

@Preview(showBackground = true)
@Composable
fun DeviceNamePreview() {
    LumenTreeTheme {
        var text by remember { mutableStateOf("name") }
        DeviceNameField(
            modifier = Modifier
                .width(120.dp)
                .height(45.dp)
                .padding(0.dp),
            editable = true,
            name = "KSC Mood Light"
        ) {
            text = it
        }
    }
}

@Composable
fun DeviceNameField(
    modifier: Modifier = Modifier,
    name: String,
    editable: Boolean,
    onValueChanged: (String) -> Unit
) {
    if (editable) {
        OutlinedTextField(
            modifier = modifier,
            value = name,
            onValueChange = onValueChanged,
            textStyle = TextStyle(textAlign = TextAlign.Justify),
            singleLine = true,
            maxLines = 1,
            minLines = 1
        )
    } else {
        Row(
            modifier = modifier,
            horizontalArrangement = Arrangement.spacedBy(0.dp)
        ) {
            Text(
                text = name,
                modifier = Modifier
                    .align(Alignment.CenterVertically)
                    .width(0.dp)
                    .weight(1f),
                overflow = TextOverflow.Ellipsis
            )
            IconButton(modifier = Modifier.align(Alignment.CenterVertically),
                onClick = {

                }) {
                Icon(
                    modifier = Modifier.padding(0.dp),
                    painter = painterResource(R.drawable.round_edit_24),
                    contentDescription = "edit icon button"
                )
            }
        }
    }
}
