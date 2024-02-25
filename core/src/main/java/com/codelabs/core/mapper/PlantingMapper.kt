package com.codelabs.core.mapper

import com.codelabs.core.data.source.remote.response.Response
import com.codelabs.core.data.source.remote.response.planting.PlantingDataResponse
import com.codelabs.core.domain.model.PlantingModel
import com.codelabs.core.utils.Mapper
import javax.inject.Inject

class PlantingMapper @Inject constructor() : Mapper<Response<PlantingDataResponse>, PlantingModel> {
    override fun mapFromResponseToModel(type: Response<PlantingDataResponse>): PlantingModel {
        return type.data.let { data ->
            PlantingModel(
                id = data?.id.orEmpty(),
                farmLandId = data?.farmlandId.orEmpty(),
                commodityId = data?.commodityId.orEmpty(),
                unit = data?.unit.orEmpty(),
                plantingSize = data?.plantingSize ?: 0.0,
                plantingQuantity = data?.plantingQuantity ?: 0.0,
                productionCost = data?.productionCost ?: 0,
                plantingType = data?.plantingType.orEmpty(),
                longitude = data?.longitude.orEmpty(),
                latitude = data?.latitude.orEmpty(),
                createdAt = data?.createdAt.orEmpty(),
                updatedAt = data?.updatedAt.orEmpty(),
                date = data?.date.orEmpty(),
                status = data?.status.orEmpty(),
                code = type.code,
                message = type.message,
            )
        }
    }

}