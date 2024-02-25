package com.codelabs.core.data.impl

import android.util.Log
import com.codelabs.core.data.source.remote.RegionRemoteDataSource
import com.codelabs.core.data.source.remote.network.ApiResponse
import com.codelabs.core.domain.model.RegionModel
import com.codelabs.core.domain.repository.RegionRepository
import com.codelabs.core.mapper.RegionMapper
import com.codelabs.core.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class RegionRepositoryImpl @Inject constructor(
    private val regionRemoteDataSource: RegionRemoteDataSource,
    private val regionMapper: RegionMapper
) : RegionRepository {
    override fun getListRegion(
        code: String?,
        type: String
    ): Flow<Resource<List<RegionModel>>> = flow {
        emit(Resource.Loading())
        when (val response = regionRemoteDataSource.getListRegion(code, type).first()) {
            is ApiResponse.Success -> {
                val regionList = response.data.data!!.map {
                    regionMapper.mapFromResponseToModel(it)
                }
                emit(Resource.Success(regionList))
                Log.d("TEST REGION", "$response")
            }

            is ApiResponse.Empty -> {
                emit(Resource.Success(emptyList()))
                Log.d("TEST REGION", "$response")
            }

            is ApiResponse.Error -> {
                emit(Resource.Error(response.message))
                Log.d("TEST REGION", "$response")
            }
        }
    }

}