package com.example.lumentree.core.domain.getdata

import com.example.lumentree.core.data.repository.DeviceListRepository
import com.example.lumentree.core.model.bt.BluetoothDevice
import javax.inject.Inject

class GetPairedBluetoothDataUseCase @Inject constructor(private val repository: DeviceListRepository) {
    suspend operator fun invoke() = repository.fetchPairedDevice()
}