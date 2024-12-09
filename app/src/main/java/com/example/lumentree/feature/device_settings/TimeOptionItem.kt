package com.example.lumentree.feature.device_settings

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TimePicker
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private fun formatTime(hours: Int, minutes: Int): String {
    return String.format("%02d:%02d", hours, minutes)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TimeOptionItem(
    modifier: Modifier = Modifier,
    hour: Int,
    minute: Int,
    text: String,
    description: String?,
    onTimeSet: (hour: Int, minute: Int) -> Unit
) {
    var showDialog by remember { mutableStateOf(false) }

    val timePickerState = rememberTimePickerState(
        initialHour = hour,
        initialMinute = minute,
        is24Hour = true,
    )

    if (showDialog) {
        TimePickerDialog(
            onDismiss = {
                showDialog = false
            },
            onConfirm = {
                onTimeSet(timePickerState.hour, timePickerState.minute)
                showDialog = false
            }
        ) {
            TimePicker(
                state = timePickerState,
            )
        }
    }
    Row(
        modifier
            .height(75.dp)
            .clickable {
                showDialog = true
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
        Text(
            text = formatTime(hour, minute),
            fontSize = 26.sp,
            fontWeight = FontWeight.SemiBold,
            letterSpacing = 3.sp,
            color = Color.Black.copy(alpha = 0.8f),
            modifier = Modifier
                .padding(horizontal = 20.dp)
                .align(Alignment.CenterVertically)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun TimeOptionItemPreview() {
    TimeOptionItem(
        modifier = Modifier.width(400.dp),
        hour = 21,
        minute = 30,
        text = "자동 켜짐 시각",
        description = "option description"
    ) { _, _ ->

    }
}
