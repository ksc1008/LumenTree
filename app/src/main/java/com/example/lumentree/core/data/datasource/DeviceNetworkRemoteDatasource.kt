package com.example.lumentree.core.data.datasource

import com.example.lumentree.core.data.api.LumenTreeBluetoothService
import com.example.lumentree.core.data.dto.CommandBody
import com.example.lumentree.core.data.dto.Commands
import com.example.lumentree.core.data.dto.RemoteCommand
import com.example.lumentree.core.data.dto.ResponseWithConnectionInfo
import com.example.lumentree.core.data.dto.ResponseWithWifiList
import com.example.lumentree.core.data.dto.toVO
import com.example.lumentree.core.model.device_connect.DeviceConnectionInfo
import com.example.lumentree.core.model.device_connect.WiFiAPInfo
import javax.inject.Inject

class DeviceNetworkRemoteDatasource @Inject constructor(
    private val bluetoothService: LumenTreeBluetoothService
) {
    suspend fun getDeviceConnectionInfo(): Result<DeviceConnectionInfo> =
        tryGetAndHandleResult {
            bluetoothService.getCommand<ResponseWithConnectionInfo>(
                RemoteCommand(
                    body = CommandBody.EMPTY,
                    commandType = Commands.GET_CONNECTION_INFO
                )
            ).getOrThrow().toVO()
        }

    suspend fun getDeviceWifiList(): Result<List<WiFiAPInfo>> =
        tryGetAndHandleResult {
            bluetoothService.getCommand<ResponseWithWifiList>(
                RemoteCommand(
                    body = CommandBody.EMPTY,
                    commandType = Commands.GET_WIFI_LIST
                )
            ).getOrThrow().toVO()
        }

    suspend fun connectToWiFi(ssid: String, password: String): Result<Unit> =
        tryGetAndHandleResult {
            bluetoothService.postCommand(
                RemoteCommand(
                    body = CommandBody.ConnectWifi(ssid = ssid, password = password),
                    commandType = Commands.CONNECT_WIFI
                )
            )
        }
}
