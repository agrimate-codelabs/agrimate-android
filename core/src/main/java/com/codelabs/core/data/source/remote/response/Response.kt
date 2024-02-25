package com.codelabs.core.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class Response<T>(
    @SerializedName("code")
    val code: Int,
    @SerializedName("status")
    val status: String,
    @Transient
    val message: ResponseMessage,
    @SerializedName("data")
    val data: T?
)