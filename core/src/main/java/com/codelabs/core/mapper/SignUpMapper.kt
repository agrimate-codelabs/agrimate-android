package com.codelabs.core.mapper

import com.codelabs.core.data.source.remote.response.Response
import com.codelabs.core.data.source.remote.response.auth.SignUpResponse
import com.codelabs.core.domain.model.SignUpModel
import com.codelabs.core.utils.Mapper
import javax.inject.Inject

open class SignUpMapper @Inject constructor() : Mapper<Response<SignUpResponse>, SignUpModel> {
    override fun mapFromResponseToModel(type: Response<SignUpResponse>): SignUpModel {
        return SignUpModel(
            code = type.code,
            message = type.message
        )
    }

}