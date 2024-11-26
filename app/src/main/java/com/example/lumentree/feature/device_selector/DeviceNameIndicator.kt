package com.example.lumentree.feature.device_selector

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
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
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.lumentree.R
import com.example.lumentree.ui.styles.deviceNameEditTextStyle
import com.example.lumentree.ui.styles.deviceNameTextStyle
import com.example.lumentree.ui.theme.LumenTreeTheme

@Preview(showBackground = true)
@Composable
fun DeviceNamePreview() {
    val name = "KSC Mood Light"
    LumenTreeTheme {
        var text by remember { mutableStateOf("name") }
        DeviceNameField(
            modifier = Modifier
                .width(200.dp)
                .wrapContentHeight()
                .padding(0.dp),
            name = name,
            editable = false,
            editingName = name,
            onEditButtonClick = {}
        ) {
            text = it
        }
    }
}

@Composable
fun DeviceNameField(
    modifier: Modifier = Modifier,
    name: String,
    editingName: String,
    editable: Boolean,
    onEditButtonClick: () -> Unit,
    onValueChanged: (String) -> Unit
) {
    Box(modifier) {
        if (editable) {
            OutlinedTextField(
                modifier = Modifier
                    .width(300.dp),
                value = editingName,
                onValueChange = onValueChanged,
                textStyle = deviceNameEditTextStyle,
                singleLine = true,
                maxLines = 1,
            )
        } else {
            Row(
                horizontalArrangement = Arrangement.spacedBy(0.dp)
            ) {
                Text(
                    text = name,
                    style = deviceNameTextStyle,
                    modifier = Modifier
                        .padding(start = 56.dp, end = 8.dp)
                        .align(Alignment.CenterVertically)
                        .wrapContentSize(),
                    overflow = TextOverflow.Ellipsis
                )
                IconButton(modifier = Modifier.align(Alignment.CenterVertically),
                    onClick = {
                        onEditButtonClick()
                    }) {
                    Icon(
                        painter = painterResource(R.drawable.round_edit_24),
                        contentDescription = "edit icon button"
                    )
                }
            }
        }
    }
}

private const val TAG = "DeviceNameIndicator"
