package com.example.lumentree.core.data.api

import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import com.example.lumentree.core.model.error.BluetoothUnavailableException
import javax.inject.Inject

class BluetoothAPI @Inject constructor(
    private val bluetoothAdapter: BluetoothAdapter?
) {
    val isEnabled
        get() = bluetoothAdapter?.isEnabled == true

    fun getPairedDevices(): List<BluetoothDevice>{
        if(bluetoothAdapter == null || !bluetoothAdapter.isEnabled) {
            throw BluetoothUnavailableException()
        }

        try {
            return bluetoothAdapter.bondedDevices.toList()
        }
        catch (e: SecurityException) {
            throw SecurityException()
        }
    }
}
