package com.example.lumentree.feature.test_page

import android.Manifest
import android.bluetooth.BluetoothClass.Device
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build.VERSION_CODES
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import com.github.skydoves.colorpicker.compose.AlphaSlider
import com.github.skydoves.colorpicker.compose.BrightnessSlider
import com.github.skydoves.colorpicker.compose.ColorEnvelope
import com.github.skydoves.colorpicker.compose.HsvColorPicker
import com.github.skydoves.colorpicker.compose.rememberColorPickerController
import kotlinx.coroutines.delay

@Composable
fun TestScreenContent(
    modifier: Modifier = Modifier,
    state: DeviceInfoUIItem,
    connectionState: String
) {

}

@Composable
fun TestScreen(
    modifier: Modifier = Modifier,
    viewModel: TestViewModel = hiltViewModel()
) {
    val context = LocalContext.current

    /** 요청할 권한 **/
    val permissions = if(android.os.Build.VERSION.SDK_INT >= VERSION_CODES.S) arrayOf(
        Manifest.permission.BLUETOOTH_SCAN,
        Manifest.permission.BLUETOOTH_CONNECT,
        Manifest.permission.BLUETOOTH
    ) else arrayOf(
        Manifest.permission.BLUETOOTH
    )

    val launcherMultiplePermissions = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissionsMap ->
        val areGranted = permissionsMap.values.reduce { acc, next -> acc && next }
    }

    LaunchedEffect(Unit) {
        while(true) {
            viewModel.tick()
            delay(500)
        }
    }

    val state by viewModel.deviceListState.collectAsState()
    val connectionState by viewModel.connectionState.collectAsState()
    val controller = rememberColorPickerController()
    Column(modifier = modifier) {
        Button(
            onClick = {
                checkAndRequestPermissions(
                    context = context,
                    permissions = permissions,
                    launcher = launcherMultiplePermissions
                )
            }
        ){
            Text("Permission")
        }
        Button(
            onClick = {
                viewModel.getDeviceList()
            }
        ) {
            Text("Get Paired Devices")
        }

        state.forEach {
            when(it) {
                is DeviceInfoUIItem.Success -> {
                    Text(
                        it.name
                    )
                    Text(
                        it.address
                    )
                }

                is DeviceInfoUIItem.Error -> {
                    Text(
                        it.type
                    )
                    Text(
                        it.stackTrace
                    )
                }
            }

        }
        Button( onClick = viewModel::connect ) {
            Text("Connect")
        }
        Row{
            Button( onClick = viewModel::turnOn ) {
                Text("On")
            }
            Button( onClick = viewModel::turnOff ) {
                Text("Off")
            }

        }
        Row{
            Button( onClick = viewModel::previewOn ) {
                Text("Preview On")
            }
            Button( onClick = viewModel::previewOff ) {
                Text("Preview Off")
            }
        }
        Text(
            connectionState
        )
        HsvColorPicker(
            modifier = Modifier
                .size(300.dp, 300.dp)
                .padding(10.dp),
            controller = controller,
            onColorChanged = {
                viewModel.updateColor(controller.selectedColor.value)
            }
        )

        BrightnessSlider(
            modifier = Modifier
                .padding(10.dp)
                .size(300.dp, 35.dp),
            controller = controller
        )

        AlphaSlider(
            modifier = Modifier
                .padding(10.dp)
                .size(300.dp, 35.dp),
            controller = controller,
        )
    }
}

@Preview
@Composable
fun DeviceTest() {
    Scaffold(modifier = Modifier.fillMaxSize(),
    ) { innerPadding ->
        TestScreen(
            modifier = Modifier.padding(
                innerPadding
            )
        )
    }
}
fun checkAndRequestPermissions(
    context: Context,
    permissions: Array<String>,
    launcher: ManagedActivityResultLauncher<Array<String>, Map<String, Boolean>>,
) {

    /** 권한이 이미 있는 경우 **/
    if (permissions.all {
            ContextCompat.checkSelfPermission(
                context,
                it
            ) == PackageManager.PERMISSION_GRANTED
        }) {
    }

    /** 권한이 없는 경우 **/
    else {
        launcher.launch(permissions)
    }
}
