package com.codelabs.core.mapper

import com.codelabs.core.data.source.remote.response.Response
import com.codelabs.core.data.source.remote.response.farmerland.RemainingLandAreaResponse
import com.codelabs.core.domain.model.RemainingLandAreaModel
import com.codelabs.core.utils.Mapper
import javax.inject.Inject

class RemainingLandAreaMapper @Inject constructor() :
    Mapper<Response<RemainingLandAreaResponse>, RemainingLandAreaModel> {
    override fun mapFromResponseToModel(type: Response<RemainingLandAreaResponse>): RemainingLandAreaModel {
        return RemainingLandAreaModel(
            usedArea = type.data?.usedArea,
            totalArea = type.data?.totalArea
        )
    }
}