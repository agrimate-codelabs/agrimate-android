package com.codelabs.core.mapper

import com.codelabs.core.data.source.remote.response.Response
import com.codelabs.core.domain.model.UploadImageModel
import com.codelabs.core.utils.Mapper
import javax.inject.Inject

class UploadImageMapper @Inject constructor() : Mapper<Response<String>, UploadImageModel> {
    override fun mapFromResponseToModel(type: Response<String>): UploadImageModel =
        UploadImageModel(type.data.toString())
}