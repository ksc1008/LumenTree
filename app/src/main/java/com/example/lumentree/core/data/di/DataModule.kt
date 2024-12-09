package com.example.lumentree.core.data.di

import com.example.lumentree.core.data.repository.DeviceColorRepository
import com.example.lumentree.core.data.repository.DeviceControlRepository
import com.example.lumentree.core.data.repository.DeviceInfoRepository
import com.example.lumentree.core.data.repository.DeviceListRepository
import com.example.lumentree.core.data.repositoryimplentation.DeviceColorRepositoryImpl
import com.example.lumentree.core.data.repositoryimplentation.DeviceControlRepositoryImpl
import com.example.lumentree.core.data.repositoryimplentation.DeviceInfoRepositoryImpl
import com.example.lumentree.core.data.repositoryimplentation.DeviceListRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class DataModule {
    @Binds
    internal abstract fun bindsDeviceInfoRepository(
        deviceInfoRepository: DeviceInfoRepositoryImpl
    ): DeviceInfoRepository

    @Binds
    internal abstract fun bindsDeviceListRepository(
        deviceListRepository: DeviceListRepositoryImpl
    ): DeviceListRepository

    @Binds
    internal abstract fun bindsDeviceControlRepository(
        deviceControlRepository: DeviceControlRepositoryImpl
    ): DeviceControlRepository

    @Binds
    internal abstract fun bindsDeviceColorRepository(
        deviceColorRepository: DeviceColorRepositoryImpl
    ): DeviceColorRepository
}
