package com.codelabs.core.mapper

import com.codelabs.core.data.source.remote.response.Response
import com.codelabs.core.domain.model.CreateFarmerLandModel
import com.codelabs.core.utils.Mapper
import javax.inject.Inject

class CreateFarmerLandMapper @Inject constructor() :
    Mapper<Response<Nothing>, CreateFarmerLandModel> {
    override fun mapFromResponseToModel(type: Response<Nothing>): CreateFarmerLandModel =
        CreateFarmerLandModel(
            code = type.code,
            message = type.message
        )

}