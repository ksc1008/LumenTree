package com.example.lumentree.feature.device_selector

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class DeviceSelectorViewModel : ViewModel() {
    private val _uiState = MutableStateFlow<DeviceSelectorUiState>(
        DeviceSelectorUiState.NoDevice
    )

    val uiState = _uiState.asStateFlow()
}