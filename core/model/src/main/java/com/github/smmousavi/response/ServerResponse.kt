package com.github.smmousavi.response
import kotlinx.serialization.Serializable
@Serializable
data class ServerResponse<T>(
    val data: T,
)