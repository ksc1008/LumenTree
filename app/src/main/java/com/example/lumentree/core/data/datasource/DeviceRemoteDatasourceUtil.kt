package com.example.lumentree.core.data.datasource

import android.util.Log


internal suspend inline fun <reified T> tryGetAndHandleResult(func: () -> T): Result<T> {
    try {
        val result = func()
        return Result.success(result)
    } catch (e: Exception) {
        Log.e("DeviceRemoteDatasource", "Failed to handle DataSource result: ${e.message}")
        return Result.failure(e)
    }
}
