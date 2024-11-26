package com.example.lumentree.core.domain.getdata

import com.example.lumentree.core.data.repository.DeviceListRepository
import com.example.lumentree.core.model.device.DeviceInfo

abstract class DeviceUseCase<T> (
    private val deviceListRepository: DeviceListRepository
) {
    suspend operator fun invoke(): Result<T> {
        val result = deviceListRepository.fetchConnectedDevice()

        result.fold(
            onSuccess = {
                return processWithResult(it)
            },
            onFailure = {
                return Result.failure(it)
            }
        )
    }

    protected abstract suspend fun processWithResult(deviceInfo: DeviceInfo):Result<T>
}