package com.example.lumentree.core.model.bt

data class BluetoothDevice(
    val name: String,
    val address: String,
    val paired: Boolean
)