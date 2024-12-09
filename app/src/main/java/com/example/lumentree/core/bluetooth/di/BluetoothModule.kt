package com.example.lumentree.core.bluetooth.di

import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothManager
import android.content.Context
import com.example.lumentree.core.data.api.BluetoothAPI
import com.example.lumentree.core.data.api.LumenTreeBluetoothService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object BluetoothModule {

    @Provides
    @Singleton
    fun providesBluetoothAdapter(
        @ApplicationContext application: Context
    ): BluetoothAdapter {
        val manager = application.getSystemService(BluetoothManager::class.java)
        return manager.adapter
    }

    @Provides
    @Singleton
    fun providesBluetoothService(
        bluetoothAPI: BluetoothAPI
    ) =
        LumenTreeBluetoothService(
            bluetoothAPI
        )
}
