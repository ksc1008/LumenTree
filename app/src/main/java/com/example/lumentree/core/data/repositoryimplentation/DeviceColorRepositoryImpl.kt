package com.example.lumentree.core.data.repositoryimplentation

import com.example.lumentree.core.data.datasource.DeviceColorRemoteDatasource
import com.example.lumentree.core.data.repository.DeviceColorRepository
import com.example.lumentree.core.model.device.DeviceColorInfo
import com.example.lumentree.core.model.light.LightColor
import javax.inject.Inject

class DeviceColorRepositoryImpl@Inject constructor(
    private val deviceColorRemoteDatasource: DeviceColorRemoteDatasource
):DeviceColorRepository {
    override suspend fun getDeviceColorInfoStatus() =
        deviceColorRemoteDatasource.getDeviceColorList()

    override suspend fun updateDeviceColor(color: DeviceColorInfo): Result<Unit> =
        deviceColorRemoteDatasource.setLightColor(
            code = color.color.toCode(),
            weatherCode = color.state.ordinal
        )

    override suspend fun updateDevicePreviewMode(on: Boolean): Result<Unit> =
        deviceColorRemoteDatasource.setDevicePreviewMode(
            mode =  on
        )

    override suspend fun updateDevicePreviewColor(color: LightColor): Result<Unit> =
        deviceColorRemoteDatasource.setLightPreviewColor(
            code = color.toCode()
        )
}
