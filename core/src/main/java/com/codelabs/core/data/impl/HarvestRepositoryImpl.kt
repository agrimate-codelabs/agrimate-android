package com.codelabs.core.data.impl

import com.codelabs.core.data.source.remote.HarvestRemoteDataSource
import com.codelabs.core.data.source.remote.body.CreateHarvestBody
import com.codelabs.core.domain.repository.HarvestRepository
import com.codelabs.core.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import javax.inject.Inject

class HarvestRepositoryImpl @Inject constructor(private val harvestRemoteDataSource: HarvestRemoteDataSource) :
    HarvestRepository {
    override fun addSuccess(body: CreateHarvestBody): Flow<Resource<String>> = flow {
        emit(Resource.Loading())
        try {
            harvestRemoteDataSource.addSuccess(body)
            emit(Resource.Success("Berhasil menambahkan data!"))
        } catch (e: HttpException) {
            emit(Resource.Error(e.message()))
        } catch (e: Exception) {
            emit(Resource.Error(e.message ?: "Terjadi Kesalahan"))
        }
    }

    override fun addFailure(body: CreateHarvestBody): Flow<Resource<String>> = flow {
        emit(Resource.Loading())
        try {
            val response = harvestRemoteDataSource.addFailure(body)
            emit(Resource.Success("Berhasil menambahkan data!"))
        } catch (e: HttpException) {
            emit(Resource.Error(e.message()))
        } catch (e: Exception) {
            emit(Resource.Error(e.message ?: "Terjadi Kesalahan"))
        }
    }
}