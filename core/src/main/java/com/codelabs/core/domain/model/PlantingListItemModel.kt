package com.codelabs.core.domain.model

data class PlantingListItemModel(
    val id: String,
    val plantingDate: String,
    val harvestingDate: String,
    val plantingSize: Double,
    val commodity: String,
    val lastActivity: String,
    val status: String
)