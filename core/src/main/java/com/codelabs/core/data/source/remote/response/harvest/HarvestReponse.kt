package com.codelabs.core.data.source.remote.response.harvest

import com.google.gson.annotations.SerializedName

data class HarvestResponse(
    @SerializedName("id")
    val id: String? = null,

    @SerializedName("plantingId")
    val plantingId: String? = null,

    @SerializedName("date")
    val date: String? = null,

    @SerializedName("amount")
    val amount: Double? = null,

    @SerializedName("status")
    val status: String? = null,

    @SerializedName("createdAt")
    val createdAt: String? = null,

    @SerializedName("updatedAt")
    val updatedAt: String? = null
)

