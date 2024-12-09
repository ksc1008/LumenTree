package com.example.lumentree.core.data.repositoryimplentation

import com.example.lumentree.core.data.api.BluetoothAPI
import com.example.lumentree.core.data.api.LumenTreeBluetoothService
import com.example.lumentree.core.data.repository.DeviceListRepository
import com.example.lumentree.core.model.device.DeviceInfo
import com.example.lumentree.core.model.error.DeviceNotConnectedException
import com.example.lumentree.core.model.error.NoDeviceFoundException
import kotlinx.coroutines.flow.last
import javax.inject.Inject

class DeviceListRepositoryImpl @Inject constructor(
    private val bluetoothService: LumenTreeBluetoothService,
    private val bluetoothAPI: BluetoothAPI
) : DeviceListRepository {
    override suspend fun fetchConnectedDevice(): Result<DeviceInfo> {
        if(!bluetoothService.connected){
            return Result.failure(NoDeviceFoundException())
        }

        bluetoothService.connectedDevice?.let {
            return Result.success(it)
        }?:let {
            return Result.failure(NoDeviceFoundException())
        }
    }

    override suspend fun fetchPairedDevice(): Result<List<DeviceInfo>> {
        try {
            val list = bluetoothAPI.getPairedDevices().map {
                DeviceInfo(
                    it.name,
                    it.address
                )
            }

            if (list.isEmpty()) {
                return Result.failure(NoDeviceFoundException())
            }
            return Result.success(list)
        } catch (e: SecurityException) {
            return Result.failure(e)
        } catch (e: Exception) {
            return Result.failure(e)
        }
    }

    override suspend fun connectToDevice(deviceInfo: DeviceInfo): Result<Unit> {
        return try {
            bluetoothService.connect(deviceInfo)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun sendTestMessage(): Result<String> {
        if(!bluetoothService.connected) {
            return Result.failure(
                DeviceNotConnectedException()
            )
        }

        return bluetoothService.sendMessage("switch,off").last()?:Result.failure(Exception())
    }
}
