package com.example.lumentree.core.model.device

import kotlinx.serialization.Serializable


@Serializable
data class DeviceInfo(
    val macAddress: String
)
