package com.example.lumentree.core.data.datasource

import com.example.lumentree.core.data.api.LumenTreeBluetoothService
import com.example.lumentree.core.data.dto.CommandBody
import com.example.lumentree.core.data.dto.Commands
import com.example.lumentree.core.data.dto.RemoteCommand
import com.example.lumentree.core.data.dto.ResponseWithDeviceSettings
import com.example.lumentree.core.data.dto.toVO
import com.example.lumentree.core.model.device_config.DeviceSetting
import javax.inject.Inject

class DeviceInfoRemoteDatasource @Inject constructor(
    private val bluetoothService: LumenTreeBluetoothService
) {
    suspend fun getDeviceStatus(): Result<DeviceSetting> =
        tryGetAndHandleResult {
            bluetoothService.getCommand<ResponseWithDeviceSettings>(
                RemoteCommand(
                    body = CommandBody.EMPTY,
                    commandType = Commands.GET_DEVICE_SETTINGS
                )
            ).getOrThrow().toVO()
        }

    suspend fun setDeviceName(name: String): Result<Unit> =
        tryGetAndHandleResult {
            bluetoothService.postCommand(
                RemoteCommand(
                    body = CommandBody.SetDeviceName(name),
                    commandType = Commands.SET_DEVICE_NAME
                )
            )
        }


    suspend fun setDeviceWeatherMode(auto: Boolean): Result<Unit> =
        tryGetAndHandleResult {
            bluetoothService.postCommand(
                RemoteCommand(
                    body = CommandBody.SetWeatherMode(
                        weatherAuto = auto
                    ),
                    commandType = Commands.SET_WEATHER_MODE
                )
            )
        }

    suspend fun setAutoLightOnTime(time: String): Result<Unit> =
        tryGetAndHandleResult {
            bluetoothService.postCommand(
                RemoteCommand(
                    body = CommandBody.SetAutoStartTime(
                        startTime = time
                    ),
                    commandType = Commands.SET_AUTO_START_TIME
                )
            )
        }

    suspend fun setAutoLightOffTime(time: String): Result<Unit> =
        tryGetAndHandleResult {
            bluetoothService.postCommand(
                RemoteCommand(
                    body = CommandBody.SetAutoEndTime(
                        startTime = time
                    ),
                    commandType = Commands.SET_AUTO_END_TIME
                )
            )
        }
}
