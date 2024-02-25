package com.codelabs.core.domain.model

data class HarvestModel(
    val id: String = "",
    val plantingId: String = "",
    val date: String = "",
    val amount: Double = 0.0,
    val status: String = "",
    val createdAt: String = "",
    val updatedAt: String = ""
)