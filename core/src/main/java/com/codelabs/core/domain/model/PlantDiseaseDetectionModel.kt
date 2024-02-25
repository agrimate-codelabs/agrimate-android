package com.codelabs.core.domain.model

data class PlantDiseaseDetectionModel(
    val id: String,
    val image: String,
    val name: String,
    val symtomps: String,
    val howTo: String,
    val createdAt: String,
    val updatedAt: String
)
