package com.codelabs.core.data.source.remote.body

data class CreateFarmerLandBody(
    val name: String,
    val province: String,
    val city: String,
    val district: String,
    val village: String,
    val address: String,
    val landArea: Double,
    val polygon: String,
    val photo: String
)