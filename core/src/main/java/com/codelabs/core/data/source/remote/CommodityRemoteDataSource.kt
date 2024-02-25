package com.codelabs.core.data.source.remote

import com.codelabs.core.data.source.remote.network.CommodityService
import javax.inject.Inject

class CommodityRemoteDataSource @Inject constructor(private val commodityService: CommodityService) {
    suspend fun getAll() = commodityService.getAll()
    suspend fun get(commodityId: String) = commodityService.get(commodityId)
}