package com.codelabs.core.data.impl

import android.util.Log
import com.codelabs.core.data.source.remote.CommodityRemoteDataSource
import com.codelabs.core.data.source.remote.PlantingRemoteDataSource
import com.codelabs.core.data.source.remote.body.CreatePlantingBody
import com.codelabs.core.domain.model.HarvestModel
import com.codelabs.core.domain.model.PlantingListItemModel
import com.codelabs.core.domain.model.PlantingModel
import com.codelabs.core.domain.repository.PlantingRepository
import com.codelabs.core.mapper.PlantingMapper
import com.codelabs.core.utils.Resource
import com.codelabs.core.utils.apiRequestFlow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import javax.inject.Inject

class PlantingRepositoryImpl @Inject constructor(
    private val plantingRemoteDataSource: PlantingRemoteDataSource,
    private val commodityRemoteDataSource: CommodityRemoteDataSource,
    private val plantingMapper: PlantingMapper
) : PlantingRepository {
    override fun create(body: CreatePlantingBody): Flow<Resource<PlantingModel>> =
        apiRequestFlow(call = { plantingRemoteDataSource.create(body) }, mapper = plantingMapper)

    override fun getAll(farmlandId: String): Flow<Resource<List<PlantingListItemModel>>> = flow {
        try {
            emit(Resource.Loading())
            val response = plantingRemoteDataSource.getAll(farmlandId)
            val listOfPlanting = response.data?.plantings?.map {
                PlantingListItemModel(
                    it?.id.orEmpty(),
                    it?.plantingDate.orEmpty(),
                    it?.harvestingDate.orEmpty(),
                    it?.plantingSize ?: 0.0,
                    it?.commodity.orEmpty(),
                    it?.lastActivity.orEmpty(),
                    it?.status.orEmpty()
                )
            }.orEmpty()
            emit(Resource.Success(listOfPlanting))
        } catch (e: Exception) {
            emit(Resource.Error(e.message.orEmpty()))
        }
    }

    override fun get(plantingId: String): Flow<Resource<PlantingModel>> = flow {
        emit(Resource.Loading())
        try {
            val response = plantingRemoteDataSource.get(plantingId)
            val commodityResponse =
                commodityRemoteDataSource.get(response.data?.commodityId.orEmpty())

            Log.d("TEST Harvting Response", "harvesting data ${response.data?.harvesting}")

            val harvestingData = response.data?.harvesting?.sortedBy { it.date }.orEmpty()

            Log.d("TEST", "harvesting data $harvestingData")

            val harvestModel =
                if (harvestingData.isNotEmpty())
                    harvestingData[0].let {
                        HarvestModel(
                            id = it.id.orEmpty(),
                            plantingId = it.plantingId.orEmpty(),
                            date = it.date.orEmpty(),
                            amount = it.amount ?: 0.0,
                            createdAt = it.createdAt.orEmpty(),
                            updatedAt = it.updatedAt.orEmpty()
                        )
                    } else null


            val model = plantingMapper.mapFromResponseToModel(response)
                .copy(
                    commodity = commodityResponse.data?.name.orEmpty(),
                    harvesting = harvestModel
                )
            emit(Resource.Success(model))
        } catch (e: HttpException) {
            emit(Resource.Error(e.message()))
        } catch (e: Exception) {
            emit(Resource.Error(e.message.orEmpty()))
        }
    }
}