package com.example.lumentree.feature.test_page

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lumentree.core.data.repositoryimplentation.DeviceColorRepositoryImpl
import com.example.lumentree.core.data.repositoryimplentation.DeviceControlRepositoryImpl
import com.example.lumentree.core.data.repositoryimplentation.DeviceListRepositoryImpl
import com.example.lumentree.core.model.device.DeviceInfo
import com.example.lumentree.core.model.light.ColorCode
import com.example.lumentree.core.model.light.LightColor
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class DeviceInfoUIItem {
    data class Success(
        val name: String,
        val address: String
    ) : DeviceInfoUIItem()

    data class Error(
        val type: String,
        val stackTrace: String
    ) : DeviceInfoUIItem()
}

@HiltViewModel
class TestViewModel @Inject constructor(
    private val repository: DeviceListRepositoryImpl,
    private val controlRepository: DeviceControlRepositoryImpl,
    private val colorRepository: DeviceColorRepositoryImpl
) : ViewModel() {
    private val _deviceList = MutableStateFlow<List<DeviceInfoUIItem>>(emptyList())
    private val _connectionState = MutableStateFlow("Not Connected")
    private val _colorState = mutableStateOf(Color.White)
    private var lastUpdatedColor = _colorState.value
    val colorState:State<Color> = _colorState

    val deviceListState = _deviceList.asStateFlow()
    val connectionState = _connectionState.asStateFlow()

    fun getDeviceList() {
        viewModelScope.launch(Dispatchers.IO) {
            val result = repository.fetchPairedDevice()

            result.fold(
                onSuccess = {
                    _deviceList.emit(
                        it.map { item ->
                            DeviceInfoUIItem.Success(
                                name = item.name,
                                address = item.macAddress
                            )
                        }
                    )
                },
                onFailure = {
                    _deviceList.emit(
                        listOf(
                            DeviceInfoUIItem.Error(
                                type = it::class.qualifiedName.toString(),
                                stackTrace = it.stackTrace.contentToString()
                            )
                        )
                    )
                }
            )
        }
    }

    fun connect() {
        (deviceListState.value.find {
            it is DeviceInfoUIItem.Success
        } as? DeviceInfoUIItem.Success)?.let { device ->
            viewModelScope.launch(Dispatchers.IO) {
                val result = repository.connectToDevice(
                    DeviceInfo(
                        name = device.name,
                        macAddress = device.address
                    )
                )

                result.fold(
                    onFailure = {
                        _connectionState.emit(
                            "Connection Fail: ${it.stackTraceToString()}"
                        )
                    },
                    onSuccess = {
                        _connectionState.emit(
                            "Connected"
                        )
                    }
                )
            }
        }
    }

    fun turnOn(){
        viewModelScope.launch(Dispatchers.IO) {
            controlRepository.updateLightOn(true)
        }
    }

    fun turnOff(){
        viewModelScope.launch(Dispatchers.IO) {
            controlRepository.updateLightOn(false)
        }
    }

    fun previewOn(){
        viewModelScope.launch(Dispatchers.IO) {
            colorRepository.updateDevicePreviewMode(true)
        }
    }
    fun previewOff(){
        viewModelScope.launch(Dispatchers.IO) {
            colorRepository.updateDevicePreviewMode(false)
        }
    }

    private fun updatePreviewColor(color:Color) {
        viewModelScope.launch(Dispatchers.IO) {
            colorRepository.updateDevicePreviewColor(
                LightColor(
                    color = ColorCode(color.toArgb()),
                    intensity = (color.alpha * 255).toInt()
                )
            )
        }
    }

    fun updateColor(color:Color) {
        _colorState.value = color
    }

    fun tick() {
        if(lastUpdatedColor != colorState.value) {
            lastUpdatedColor = colorState.value
            updatePreviewColor(lastUpdatedColor)
        }
    }

    fun sendTestPacket() {
        viewModelScope.launch(Dispatchers.IO) {
            val result = repository.sendTestMessage()

            result.fold(
                onFailure = {
                    _connectionState.emit(
                        "Connection Fail: ${it.stackTraceToString()}"
                    )
                },
                onSuccess = {
                    _connectionState.emit(
                        it
                    )
                }
            )
        }
    }
}