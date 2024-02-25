package com.codelabs.core.data.source.remote.response.diseasedetection

import com.google.gson.annotations.SerializedName

data class DiseaseDetectionResponse(

    @field:SerializedName("image")
    val image: String? = null,

    @field:SerializedName("createdAt")
    val createdAt: String? = null,

    @field:SerializedName("howTo")
    val howTo: String? = null,

    @field:SerializedName("name")
    val name: String? = null,

    @field:SerializedName("id")
    val id: String? = null,

    @field:SerializedName("symtomps")
    val symtomps: String? = null,

    @field:SerializedName("updatedAt")
    val updatedAt: String? = null
)
