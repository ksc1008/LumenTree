package com.example.lumentree.core.data.di

import com.example.lumentree.core.data.repository.DeviceInfoRepository
import com.example.lumentree.core.data.repository.DeviceListRepository
import com.example.lumentree.core.data.repository.FakeDeviceInfoRepositoryImpl
import com.example.lumentree.core.data.repository.FakeDeviceListRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class DataModule {
    @Binds
    internal abstract fun bindsDeviceInfoRepository(
        deviceInfoRepository: FakeDeviceInfoRepositoryImpl
    ): DeviceInfoRepository

    @Binds
    internal abstract fun bindsDeviceListRepository(
        deviceListRepository: FakeDeviceListRepositoryImpl
    ): DeviceListRepository
}
