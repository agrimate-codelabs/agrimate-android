package com.codelabs.core.data.impl

import com.codelabs.core.data.source.remote.CommodityRemoteDataSource
import com.codelabs.core.domain.model.CommodityModel
import com.codelabs.core.domain.repository.CommodityRepository
import com.codelabs.core.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class CommodityRepositoryImpl @Inject constructor(
    private val commodityRemoteDataSource: CommodityRemoteDataSource
) : CommodityRepository {
    override fun getAll(): Flow<Resource<List<CommodityModel>>> = flow {
        try {
            emit(Resource.Loading())
            val response = commodityRemoteDataSource.getAll()
            val listCommodity = response.data?.commodities?.map {
                CommodityModel(it?.id.orEmpty(), it?.name.orEmpty(), it?.icon.orEmpty())
            } ?: emptyList()
            emit(Resource.Success(listCommodity))
        } catch (e: Exception) {
            emit(Resource.Error(e.message.orEmpty()))
        }
    }

}