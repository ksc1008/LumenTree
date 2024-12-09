package com.example.lumentree.core.domain.devicecontrol

import com.example.lumentree.core.data.repository.DeviceControlRepository
import com.example.lumentree.core.model.devicecontrol.DeviceRemoteControlStatus
import javax.inject.Inject

class UpdateLightControlUseCase @Inject constructor(
    val repository: DeviceControlRepository
) {
    suspend operator fun invoke(switchOn: Boolean, autoSwitch: Boolean): Result<Unit> =
        repository.updateLightOn(switchOn = switchOn)
}
