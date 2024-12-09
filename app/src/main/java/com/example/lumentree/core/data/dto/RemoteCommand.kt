package com.example.lumentree.core.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RemoteCommand(
    @SerialName("type")
    val commandType: String,

    @SerialName("data")
    val body: CommandBody
)
