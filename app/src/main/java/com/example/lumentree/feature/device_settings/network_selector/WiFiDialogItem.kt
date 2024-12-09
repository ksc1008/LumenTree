package com.example.lumentree.feature.device_settings.network_selector

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.lumentree.R
import com.example.lumentree.core.model.device_connect.WiFiAPInfo

private fun convertWiFiStrengthToIconResource(strength: Int): Int {
    if (strength < -80)
        return R.drawable.baseline_signal_wifi_0_bar_24
    if (strength < -70)
        return R.drawable.baseline_network_wifi_1_bar_24
    if (strength < -67)
        return R.drawable.baseline_network_wifi_2_bar_24
    if (strength < -50)
        return R.drawable.baseline_network_wifi_3_bar_24

    return R.drawable.baseline_signal_wifi_4_bar_24
}

@Composable
fun PasswordField(
    modifier: Modifier = Modifier,
    passwordState: String,
    onTextChanged: (text: String) -> Unit
) {
    Column(
        modifier = modifier
            .padding(15.dp)
    ) {
        Text("비밀번호", fontSize = 16.sp,
            modifier = Modifier.padding(start = 5.dp))
        OutlinedTextField(
            value = passwordState,
            onValueChange = onTextChanged,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 10.dp)
        )
    }
}

@Composable
fun WiFiDialogItem(
    modifier: Modifier = Modifier,
    state: WiFiAPInfo,
    selected: Boolean,
    passwordState: String,
    onPasswordChanged: (newPassword: String) -> Unit,
    onClick: ()->Unit,
) {

    val size = if (state.protected && selected) 180.dp else 60.dp
    val bg = if(selected) Color(0xfff3f3f3) else Color.White
    val alpha by animateFloatAsState(if(state.protected && selected) 1f else 0f, label = "alpha")

    Column (
        modifier = modifier
            .animateContentSize()
            .height(size)
            .background(Color(0xfff3f3f3), shape = RoundedCornerShape(bottomStart = 15.dp, bottomEnd = 15.dp))
    ) {
        Row (
            modifier = Modifier
                .height(60.dp)
                .fillMaxWidth()
                .background(bg)
                .clickable { onClick() }
        ){
            Image(
                modifier = Modifier
                    .padding(start = 12.dp)
                    .align(Alignment.CenterVertically),
                painter = painterResource(
                    convertWiFiStrengthToIconResource(state.strength)
                ),
                colorFilter = ColorFilter.tint(Color.Black.copy(0.9f)),
                contentDescription = null
            )
            Text(
                text = state.ssid,
                fontSize = 26.sp,
                modifier = Modifier
                    .padding(start = 12.dp)
                    .align(Alignment.CenterVertically)
                    .weight(1f)
            )
            if(state.protected) {
                Image(
                    modifier = Modifier
                        .padding(end = 15.dp)
                        .align(Alignment.CenterVertically),
                    painter = painterResource(
                        R.drawable.baseline_lock_outline_24
                    ),
                    colorFilter = ColorFilter.tint(Color.Black.copy(0.9f)),
                    contentDescription = null
                )
            }
        }

        if(state.protected && selected) {
            PasswordField(
                Modifier.weight(1f)
                    .fillMaxWidth()
                    .alpha(alpha),
                passwordState = passwordState,
                onTextChanged = onPasswordChanged
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun WiFiDialogItemPreview() {
    WiFiDialogItem(
        modifier = Modifier.fillMaxWidth(),
        state = WiFiAPInfo(
            ssid = "Test WiFi Info",
            strength = -60,
            protected = true
        ),
        selected = true,
        passwordState = "abcd",
        onClick = {},
        onPasswordChanged = {}
    )
}
