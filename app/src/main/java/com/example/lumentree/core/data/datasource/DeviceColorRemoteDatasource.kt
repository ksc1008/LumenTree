package com.example.lumentree.core.data.datasource

import com.example.lumentree.core.data.api.LumenTreeBluetoothService
import com.example.lumentree.core.data.dto.CommandBody
import com.example.lumentree.core.data.dto.Commands
import com.example.lumentree.core.data.dto.RemoteCommand
import com.example.lumentree.core.data.dto.ResponseWithColor
import com.example.lumentree.core.data.dto.toVO
import com.example.lumentree.core.model.device.DeviceColorInfo
import javax.inject.Inject

class DeviceColorRemoteDatasource @Inject constructor(
    private val bluetoothService: LumenTreeBluetoothService
){

    suspend fun setDevicePreviewMode(mode: Boolean): Result<Unit> =
        tryGetAndHandleResult {
            bluetoothService.postCommand(
                RemoteCommand(
                    body = CommandBody.SetLightPreviewMode(mode),
                    commandType = Commands.SET_LIGHT_PREVIEW_MODE
                )
            )
        }

    suspend fun setLightPreviewColor(code: String): Result<Unit> =
        tryGetAndHandleResult {
            bluetoothService.postCommand(
                RemoteCommand(
                    body = CommandBody.SetPreviewLightColor(code),
                    commandType = Commands.SET_PREVIEW_LIGHT_COLOR
                )
            )
        }

    suspend fun getDeviceColorList(): Result<List<DeviceColorInfo>> =
        tryGetAndHandleResult {
            bluetoothService.getCommand<ResponseWithColor>(
                RemoteCommand(
                    body = CommandBody.EMPTY,
                    commandType = Commands.GET_DEVICE_COLORS
                )
            ).getOrThrow().toVO()
        }

    suspend fun setLightColor(code: String, weatherCode: Int): Result<Unit> =
        tryGetAndHandleResult {
            bluetoothService.postCommand(
                RemoteCommand(
                    body = CommandBody.SetLightColor(
                        weatherCode = weatherCode,
                        colorCode = code
                    ),
                    commandType = Commands.SET_LIGHT_COLOR
                )
            )
        }
}
