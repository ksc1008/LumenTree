package com.example.lumentree.core.domain.getdata

import com.example.lumentree.core.model.bt.BluetoothDevice

class GetPairedBluetoothDataUseCase {
    suspend operator fun invoke(): Result<List<BluetoothDevice>> {
        return Result.failure(Exception())
    }
}