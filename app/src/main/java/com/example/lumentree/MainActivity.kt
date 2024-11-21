package com.example.lumentree

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.lumentree.feature.device_selector.DeviceNameField
import com.example.lumentree.feature.device_selector.DeviceStateIndicator
import com.example.lumentree.ui.theme.LumenTreeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            var nameValue by remember { mutableStateOf("hello") }
            LumenTreeTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Column(Modifier.padding(innerPadding)) {
                        DeviceNameField(
                            Modifier
                                .width(300.dp)
                                .height(50.dp),
                            nameValue,
                            true
                        ) {
                        }

                        DeviceStateIndicator(
                            modifier = Modifier.width(300.dp),
                            autoLightEnabled = true,
                            asmrEnabled = true,
                            wifiEnabled = true
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    var nameValue by remember { mutableStateOf("hello") }
    Column {
        Text(
            text = "Hello $name!",
            modifier = modifier
        )

        DeviceNameField(
            Modifier
                .width(300.dp)
                .height(50.dp),
            nameValue,
            false
        ) {
            nameValue = it
        }

        DeviceStateIndicator(
            modifier = Modifier.width(300.dp),
            autoLightEnabled = true,
            asmrEnabled = true,
            wifiEnabled = true
        )
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    LumenTreeTheme {
        Greeting("KSC")
    }
}
