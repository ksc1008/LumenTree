package com.example.lumentree.core.domain.getdata

import com.example.lumentree.core.data.repository.DeviceListRepository
import com.example.lumentree.core.model.error.NoDeviceFoundException
import javax.inject.Inject

class ConnectToDeviceUseCase @Inject constructor(private val repository: DeviceListRepository) {
    suspend operator fun invoke(): Result<Unit> {
        val deviceList = repository.fetchPairedDevice()
        deviceList.fold(
            onFailure = {
                return Result.failure(it)
            },
            onSuccess = {
                if (it.isNotEmpty()) {
                    it.find { device ->
                        device.name == "bioit"
                    }?.let { found ->
                        val connectionResult = repository.connectToDevice(found)
                        return connectionResult
                    }?:let {
                        return Result.failure(NoDeviceFoundException())
                    }
                } else {
                    return Result.failure(NoDeviceFoundException())
                }
            }
        )
    }
}
