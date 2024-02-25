package com.codelabs.core.data.source.remote.response

sealed class ResponseMessage {
    data class StringMessage(val message: String) : ResponseMessage()
    data class ArrayMessage(val message: List<PathMessage>) : ResponseMessage()
}

data class PathMessage(
    val path: String,
    val message: String
)