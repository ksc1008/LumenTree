package com.example.lumentree.core.data.repositoryimplentation

import com.example.lumentree.core.data.datasource.DeviceNetworkRemoteDatasource
import com.example.lumentree.core.data.repository.DeviceNetworkRepository
import com.example.lumentree.core.model.device_connect.DeviceConnectionInfo
import com.example.lumentree.core.model.device_connect.WiFiAPInfo
import javax.inject.Inject

class DeviceNetworkRepositoryImpl @Inject constructor(
    private val deviceNetworkRemoteDatasource: DeviceNetworkRemoteDatasource
) : DeviceNetworkRepository {
    override suspend fun getDeviceConnectionInfo(): Result<DeviceConnectionInfo> =
        deviceNetworkRemoteDatasource.getDeviceConnectionInfo()

    override suspend fun getDeviceWifiList(): Result<List<WiFiAPInfo>> =
        deviceNetworkRemoteDatasource.getDeviceWifiList()

    override suspend fun connectToWifi(ssid: String, password: String): Result<Unit> =
        deviceNetworkRemoteDatasource.connectToWiFi(ssid, password)
}
