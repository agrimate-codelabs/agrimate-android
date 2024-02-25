package com.codelabs.core.domain.model

data class PlantDiseasePredictionModel(
    val id: String,
    val probability: Double,
    val name: String
)