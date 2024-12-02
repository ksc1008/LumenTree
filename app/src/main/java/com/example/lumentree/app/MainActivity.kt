package com.example.lumentree.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.example.lumentree.app.navigation.LumenTreeNavHost
import com.example.lumentree.feature.device.LumenTreeBottomNavigation
import com.example.lumentree.feature.device_selector.navigation.DeviceSelector
import com.example.lumentree.ui.theme.LumenTreeTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()

            LumenTreeTheme {
                Scaffold(modifier = Modifier.fillMaxSize(),
                    bottomBar = {
                        LumenTreeBottomNavigation(
                            modifier = Modifier,
                            navController = navController
                        )
                    }
                ) { innerPadding ->
                    LumenTreeNavHost(
                        Modifier
                            .padding(innerPadding)
                            .fillMaxSize(),
                        navController,
                        DeviceSelector
                    )
                }
            }
        }
    }
}
