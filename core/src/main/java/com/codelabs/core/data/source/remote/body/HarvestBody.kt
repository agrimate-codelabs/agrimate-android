package com.codelabs.core.data.source.remote.body

data class CreateHarvestBody(
    val plantingId: String,
    val date: String? = null,
    val amount: Double? = null
)
