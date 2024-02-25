package com.codelabs.core.data.source.remote

import com.codelabs.core.data.source.remote.body.CreateHarvestBody
import com.codelabs.core.data.source.remote.network.HarvestService
import javax.inject.Inject

class HarvestRemoteDataSource @Inject constructor(private val harvestService: HarvestService) {
    suspend fun addSuccess(body: CreateHarvestBody) = harvestService.addSuccess(body)

    suspend fun addFailure(body: CreateHarvestBody) = harvestService.addFailure(body)
}