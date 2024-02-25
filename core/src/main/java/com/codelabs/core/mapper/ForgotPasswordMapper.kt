package com.codelabs.core.mapper

import com.codelabs.core.data.source.remote.response.Response
import com.codelabs.core.domain.model.ForgotPasswordModel
import com.codelabs.core.utils.Mapper
import javax.inject.Inject

class ForgotPasswordMapper @Inject constructor() : Mapper<Response<Nothing>, ForgotPasswordModel> {
    override fun mapFromResponseToModel(type: Response<Nothing>): ForgotPasswordModel {
        return ForgotPasswordModel(
            code = type.code,
            message = type.message,
        )
    }

}