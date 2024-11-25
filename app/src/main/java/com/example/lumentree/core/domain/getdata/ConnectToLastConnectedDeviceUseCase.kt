package com.example.lumentree.core.domain.getdata

import com.example.lumentree.core.data.repository.DeviceListRepository
import com.example.lumentree.core.model.error.NoDeviceFoundError
import javax.inject.Inject

class ConnectToLastConnectedDeviceUseCase @Inject constructor(private val repository: DeviceListRepository) {
    suspend operator fun invoke(): Result<Unit> {
        val deviceList = repository.fetchPairedDevice()
        deviceList.fold(
            onFailure = {
                return Result.failure(it)
            },
            onSuccess = {
                if (it.isNotEmpty()) {
                    val connectionResult = repository.connectToDevice(it.first())
                    return connectionResult
                } else {
                    return Result.failure(NoDeviceFoundError())
                }
            }
        )

    }
}
