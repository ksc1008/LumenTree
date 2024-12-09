package com.example.lumentree.feature.device_settings.network_selector

import android.graphics.Color
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.example.lumentree.core.model.device_connect.WiFiAPInfo
import com.example.lumentree.ui.styles.headerTextStyle

private fun validSelection(
    selection: Int,
    wifiList: List<WiFiAPInfo>,
    password: String
): Boolean {
    if (selection < 0 || selection >= wifiList.size) {
        return false
    }

    return !(wifiList[selection].protected && password.isEmpty())
}

@Composable
fun NetworkSelectorDialog(
    modifier: Modifier = Modifier,
    wifiList: List<WiFiAPInfo>,
    onDismiss: () -> Unit,
    onApply: (ssid: String, password: String) -> Unit
) {
    var selectedIndex by remember { mutableIntStateOf(-1) }
    var passwordState by remember { mutableStateOf("") }


    Dialog(
        onDismissRequest = onDismiss
    ) {
        Column(
            modifier = modifier.size(
                width = 500.dp,
                height = 650.dp
            )
                .background(
                    androidx.compose.ui.graphics.Color.White,
                    shape = RoundedCornerShape(10.dp)
                )
                .padding(8.dp)
        ) {
            Text(
                modifier = Modifier.padding(12.dp),
                text = "WiFi 목록",
                style = headerTextStyle
            )
            LazyColumn(modifier = Modifier
                .weight(1f)
                .padding(horizontal = 12.dp),
                verticalArrangement = Arrangement.spacedBy(5.dp)) {
                itemsIndexed(wifiList) { idx, item ->
                    WiFiDialogItem(
                        modifier = Modifier.fillMaxWidth(),
                        state = item,
                        selected = selectedIndex == idx,
                        onClick = {
                            if (selectedIndex != idx) {
                                selectedIndex = idx
                                passwordState = ""
                            }
                        },
                        passwordState = passwordState,
                        onPasswordChanged = { newPassword ->
                            passwordState = newPassword
                        }
                    )
                    if (idx < wifiList.lastIndex && !(wifiList[idx].protected && selectedIndex == idx))
                        HorizontalDivider()
                }
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
                    .align(Alignment.CenterHorizontally)
                    .padding(horizontal = 30.dp)
            ) {
                TextButton(
                    modifier = Modifier
                        .weight(1f)
                        .padding(4.dp),
                    onClick = onDismiss
                ) {
                    Text("취소")
                }
                TextButton(
                    modifier = Modifier
                        .weight(1f)
                        .padding(4.dp),
                    onClick = { onApply(wifiList[selectedIndex].ssid, passwordState) },
                    enabled = validSelection(selectedIndex, wifiList, passwordState)
                ) {
                    Text("적용")
                }
            }
        }
    }
}

@Preview (showBackground = true, backgroundColor = Color.WHITE.toLong())
@Composable
fun NetworkSelectorDialogPreview(){
    NetworkSelectorDialog(
        wifiList = listOf(
            WiFiAPInfo("TestAP", -50, false),
            WiFiAPInfo("TestAP", -60, true),
            WiFiAPInfo("TestAP", -50, true),
            WiFiAPInfo("TestAP", -70, false)
        ),
        onDismiss = {},
        onApply = {_,_ ->}
    )
}
