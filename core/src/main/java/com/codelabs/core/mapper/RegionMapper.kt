package com.codelabs.core.mapper

import com.codelabs.core.data.source.remote.response.region.RegionResponse
import com.codelabs.core.domain.model.RegionModel
import com.codelabs.core.utils.Mapper
import javax.inject.Inject

open class RegionMapper @Inject constructor() : Mapper<RegionResponse, RegionModel> {
    override fun mapFromResponseToModel(type: RegionResponse): RegionModel {
        return RegionModel(
            code = type.code,
            name = type.name
        )
    }
}