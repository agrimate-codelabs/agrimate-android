package com.codelabs.core.data.source.remote

import com.codelabs.core.data.source.remote.network.ApiResponse
import com.codelabs.core.data.source.remote.network.RegionService
import com.codelabs.core.data.source.remote.response.Response
import com.codelabs.core.data.source.remote.response.region.RegionResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class RegionRemoteDataSource @Inject constructor(
    private val regionService: RegionService
) {
    suspend fun getListRegion(
        code: String? = null,
        type: String
    ): Flow<ApiResponse<Response<List<RegionResponse>>>> = flow {
        try {
            val response = regionService.getListRegion(code, type)
            emit(ApiResponse.Success(response))
        } catch (e: Exception) {
            emit(ApiResponse.Error(e.message.toString()))
        }
    }.flowOn(Dispatchers.IO)

    suspend fun getRegions(code: String? = null, type: String) =
        regionService.getListRegion(code, type)

    suspend fun getRegionName(code: String?) = regionService.getRegionName(code = code)
}