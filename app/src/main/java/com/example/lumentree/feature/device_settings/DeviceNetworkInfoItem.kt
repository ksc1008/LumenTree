package com.example.lumentree.feature.device_settings

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.lumentree.R
import com.example.lumentree.core.model.device_connect.DeviceConnectionInfo
import com.example.lumentree.core.model.device_connect.WiFiConnectingState

val headerText = "네트워크 설정"

@Composable
fun NetworkInfoItem(
    modifier: Modifier = Modifier,
    state: DeviceConnectionInfo,
    onClick: ()->Unit
) {
    Row(
        modifier
            .height(100.dp)
            .clickable { onClick() }
    ) {
        Column(
            modifier = Modifier
                .weight(0.8f)
                .align(Alignment.Top)
                .padding(start = 20.dp)
        ) {
            Text(text = headerText, fontSize = 26.sp, modifier = Modifier.padding(top = 10.dp))
            Row(modifier= Modifier.padding(top = 15.dp).height(30.dp)) {
                Text(text = "네트워크 상태: ",
                    modifier = Modifier
                        .align(Alignment.CenterVertically)
                        .fillMaxHeight()
                        .wrapContentHeight(align = Alignment.CenterVertically),
                    textAlign = TextAlign.Center)
                if(state.connected){
                    Text(text = "연결됨",
                        modifier = Modifier.align(Alignment.CenterVertically)
                            .fillMaxHeight()
                            .wrapContentHeight(align = Alignment.CenterVertically),
                        color = Color(0xff28a745),
                        fontWeight = FontWeight.SemiBold)
                    Text(text = "(ssid: ${state.ssid})",
                        modifier = Modifier.align(Alignment.CenterVertically)
                            .fillMaxHeight()
                            .wrapContentHeight(align = Alignment.CenterVertically)
                            .padding(start = 10.dp),
                        color = Color(0xff28a745))
                }
                else{
                    if(state.connectingState == WiFiConnectingState.BUSY) {
                        Text(text = "연결중", color = Color(0xffffc107),
                            modifier = Modifier.align(Alignment.CenterVertically)
                                .fillMaxHeight()
                                .wrapContentHeight(align = Alignment.CenterVertically),
                            fontWeight = FontWeight.SemiBold)
                    }
                    else{
                        Text(text = "연결 끊김", color = Color(0xffdc3545),
                            modifier = Modifier.align(Alignment.CenterVertically)
                                .fillMaxHeight()
                                .wrapContentHeight(align = Alignment.CenterVertically),
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                }
            }

        }
        Image(
            modifier = Modifier.align(Alignment.CenterVertically)
                .padding(end = 10.dp)
                .size(32.dp),
            painter = painterResource(R.drawable.round_open_in_new_24),
            colorFilter = ColorFilter.tint(Color.LightGray),
            contentDescription = null
        )
    }
}

@Preview(showBackground = true)
@Composable
fun NetworkInfoItemPreview() {
    NetworkInfoItem(
        modifier = Modifier.width(400.dp),
        state = DeviceConnectionInfo(
            ssid="Test AP",
            connectingState = WiFiConnectingState.FAIL,
            connected = false
        )
    ){

    }
}
