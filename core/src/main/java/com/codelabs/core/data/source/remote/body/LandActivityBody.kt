package com.codelabs.core.data.source.remote.body

data class LandActivityBody(
    val plantingId: String,
    val date: String,
    val time: String,
    val type: String,
    val activity: LandActivityItem,
    val image: String
)

data class LandActivityItem(
    val nama_pupuk: String,
    val natrium: Double,
    val fosfor: Double,
    val kalium: Double
)