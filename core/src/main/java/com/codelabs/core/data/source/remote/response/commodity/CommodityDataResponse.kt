package com.codelabs.core.data.source.remote.response.commodity

import com.google.gson.annotations.SerializedName

data class CommodityDataResponse(

    @field:SerializedName("pagination")
    val pagination: Pagination? = null,

    @field:SerializedName("commodities")
    val commodities: List<CommoditiesItem?>? = null
)

data class CommoditiesItem(

    @field:SerializedName("createdAt")
    val createdAt: String? = null,

    @field:SerializedName("deletedAt")
    val deletedAt: Any? = null,

    @field:SerializedName("name")
    val name: String? = null,

    @field:SerializedName("icon")
    val icon: String? = null,

    @field:SerializedName("id")
    val id: String? = null,

    @field:SerializedName("updatedAt")
    val updatedAt: String? = null
)

data class Pagination(

    @field:SerializedName("total")
    val total: Int? = null,

    @field:SerializedName("limit")
    val limit: Int? = null,

    @field:SerializedName("page")
    val page: Int? = null
)
