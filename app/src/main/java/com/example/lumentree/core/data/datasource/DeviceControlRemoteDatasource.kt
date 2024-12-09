package com.example.lumentree.core.data.datasource

import com.example.lumentree.core.data.api.LumenTreeBluetoothService
import com.example.lumentree.core.data.dto.CommandBody
import com.example.lumentree.core.data.dto.Commands
import com.example.lumentree.core.data.dto.RemoteCommand
import com.example.lumentree.core.data.dto.ResponseWithDeviceControlState
import com.example.lumentree.core.data.dto.toVO
import com.example.lumentree.core.model.devicecontrol.DeviceRemoteControlStatus
import javax.inject.Inject

class DeviceControlRemoteDatasource @Inject constructor(
    private val bluetoothService: LumenTreeBluetoothService
) {
    suspend fun getDeviceControlState(): Result<DeviceRemoteControlStatus> =
        tryGetAndHandleResult {
            bluetoothService.getCommand<ResponseWithDeviceControlState>(
                RemoteCommand(
                    body = CommandBody.EMPTY,
                    commandType = Commands.GET_DEVICE_CONTROL_STATE
                )
            ).getOrThrow().toVO()
        }

    suspend fun setDeviceSwitch(on: Boolean): Result<Unit> =
        tryGetAndHandleResult {
            bluetoothService.postCommand(
                RemoteCommand(
                    body = CommandBody.ManualLightSwitch(on),
                    commandType = Commands.MANUAL_LIGHT_SWITCH
                )
            )
        }

    suspend fun setDeviceAutoMode(auto: Boolean): Result<Unit> =
        tryGetAndHandleResult {
            bluetoothService.postCommand(
                RemoteCommand(
                    body = CommandBody.SetAutoMode(auto),
                    commandType = Commands.SET_AUTO_MODE
                )
            )
        }
}
