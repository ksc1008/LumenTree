package com.example.lumentree.core.data.api

import android.annotation.SuppressLint
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothSocket
import android.util.Log
import com.example.lumentree.core.data.dto.RemoteCommand
import com.example.lumentree.core.data.dto.ResponseWithNoBody
import com.example.lumentree.core.model.device.DeviceInfo
import com.example.lumentree.core.model.error.DeviceNotConnectedException
import com.example.lumentree.core.model.error.NoDeviceFoundException
import kotlinx.coroutines.flow.flow
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.io.IOException
import java.io.InputStream
import java.io.OutputStream
import java.util.UUID
import java.util.concurrent.ArrayBlockingQueue
import java.util.concurrent.TimeoutException
import javax.inject.Inject

class BluetoothJob(
    val data: ByteArray,
    val onResponse: (String) -> Unit
)

class LumenTreeBluetoothService @Inject constructor(
    private val bluetoothAPI: BluetoothAPI
) {
    private inner class ConnectedThread(
        private val connectSocket: BluetoothSocket
    ) :
        Thread() {

        private val jobQueue = ArrayBlockingQueue<BluetoothJob>(100)

        private lateinit var inputStream: InputStream
        private lateinit var outputStream: OutputStream

        private var shouldCloseThread = false

        init {
            try {
                inputStream = connectSocket.inputStream
                outputStream = connectSocket.outputStream
            } catch (e: IOException) {
                Log.e(TAG, e.message ?: "")
            }
        }

        override fun run() {
            val buffer = ByteArray(1024)
            var bytes: Int

            while (!shouldCloseThread) {
                try {
                    // 데이터 받기(읽기)
                    jobQueue.poll()?.let {
                        outputStream.write(it.data)
                        bytes = inputStream.read(buffer)
                        it.onResponse(buffer.copyOfRange(0,bytes).toString(Charsets.UTF_8))
                    }
                } catch (e: Exception) { // 기기와의 연결이 끊기면 호출
                    Log.e(TAG, "기기와의 연결이 끊겼습니다.")
                    break
                }
            }
        }

        fun sendAndReadMessage(bytes: ByteArray, onResponse: (String) -> Unit) {
            try {
                jobQueue.add(
                    BluetoothJob(
                        bytes,
                        onResponse
                    )
                )
            } catch (e: IOException) {
                Log.e(TAG, e.message ?: "")
            }
        }

        fun cancel() {
            try {
                connectSocket.close()
                shouldCloseThread = true
            } catch (e: IOException) {
                Log.e(TAG, e.message ?: "")
            }
        }
    }

    private var connection: ConnectedThread? = null
    var connectedDevice: DeviceInfo? = null
        private set

    val connected: Boolean
        get() {
            connection?.let {
                return it.isAlive
            } ?: let {
                return false
            }
        }

    @SuppressLint("MissingPermission")
    fun connect(deviceInfo: DeviceInfo): Boolean {
        if (connected) {
            return false
        }

        val device = getDeviceFromDeviceInfo(deviceInfo)
        val uuid = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB")
        val connectSocket = device.createRfcommSocketToServiceRecord(uuid)

        try {
            connectSocket?.connect()
            connection = ConnectedThread(
                connectSocket
            )

            connection?.setUncaughtExceptionHandler { _, e ->
                Log.e(TAG, e.message ?: "")
                connection = null
            }
            connection?.start()
        } catch (e: IOException) {
            connectSocket?.close()
            throw e
        }

        connectedDevice = deviceInfo
        return true
    }

    fun disconnect() {
        if (!connected) {
            throw IllegalStateException("Device not connected")
        }

        connection?.cancel()
        connectedDevice = null
    }

    fun sendMessage(message: String) = flow {
        var result: Result<String>? = null
        val convertedByteArray = convertToFixedLengthByteArray(message)
        connection?.sendAndReadMessage(
            convertedByteArray
        ) {
            result = Result.success(it)
        }
        while (result == null) {
            kotlinx.coroutines.delay(10)
        }
        emit(result)
    }

    fun convertCommandToByteArray(command: RemoteCommand): ByteArray {
        val json = Json.encodeToString(command)
        return convertToFixedLengthByteArray(json)
    }

    suspend fun sendDataAndWait(data: ByteArray): Result<String>? {
        var result: Result<String>? = null

        connection?.sendAndReadMessage(
            data
        ) {
            result = Result.success(it)
        } ?: let {
            return Result.failure(DeviceNotConnectedException())
        }

        var timeoutCounter = 0
        while (timeoutCounter++ < 500 && result == null) {
            kotlinx.coroutines.delay(10)
        }

        Log.d(TAG, "Got result: $result")
        return result
    }

    suspend fun postCommand(command: RemoteCommand): Result<Unit> {
        val convertedByteArray = convertCommandToByteArray(command)
        val result = sendDataAndWait(convertedByteArray)
        result?.let { data ->
            data.fold(
                onSuccess = {
                    try {
                        val decoded = Json.decodeFromString<ResponseWithNoBody>(it)
                        if (decoded.success) {
                            return Result.success(Unit)
                        } else {
                            return Result.failure(Exception())
                        }
                    } catch (e: Exception) {
                        return Result.failure(e)
                    }
                },
                onFailure = {
                    return Result.failure(it)
                }
            )
        }
        return Result.failure(TimeoutException())
    }

    suspend inline fun <reified T>getCommand(command: RemoteCommand): Result<T> {
        val convertedByteArray = convertCommandToByteArray(command)
        val result = sendDataAndWait(convertedByteArray)

        result?.let { data ->
            data.fold(
                onSuccess = {
                    try {
                        val decoded = Json.decodeFromString<T>(it)
                        return Result.success(decoded)
                    } catch (e: Exception) {
                        Log.d(TAG, "Failed to decode or parse data: ${e.message}")
                        return Result.failure(e)
                    }
                },
                onFailure = {
                    return Result.failure(it)
                }
            )
        }
        return Result.failure(TimeoutException())
    }

    private fun convertToFixedLengthByteArray(input: String): ByteArray {
        val charset = Charsets.UTF_8
        val bytes = input.toByteArray(charset) // Convert string to byte array
        val fixedLength = 1024

        return if (bytes.size > fixedLength) {
            throw IllegalArgumentException("Input length too long.")
        } else {
            bytes
        }
    }

    private fun getDeviceFromDeviceInfo(deviceInfo: DeviceInfo): BluetoothDevice {
        val pairedList = bluetoothAPI.getPairedDevices()

        if (deviceInfo.macAddress.isNotEmpty()) {
            pairedList.find {
                it.address == deviceInfo.macAddress
            }?.let {
                return it
            }
        }

        try {
            pairedList.find {
                it.name == deviceInfo.name
            }?.let {
                return it
            }
        } catch (e: SecurityException) {
            throw e
        }

        throw NoDeviceFoundException()
    }

    companion object {
        const val TAG = "BluetoothService"
    }
}
