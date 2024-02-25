package com.codelabs.core.mapper

import com.codelabs.core.data.source.remote.response.Response
import com.codelabs.core.data.source.remote.response.auth.CheckOTPResponse
import com.codelabs.core.domain.model.CheckOTPModel
import com.codelabs.core.utils.Mapper
import javax.inject.Inject

open class CheckOTPMapper @Inject constructor() : Mapper<Response<CheckOTPResponse>, CheckOTPModel>  {
    override fun mapFromResponseToModel(type: Response<CheckOTPResponse>): CheckOTPModel {
        return CheckOTPModel(
            code = type.code,
            message = type.message,
            id = type.data?.id,
            userId = type.data?.userId,
            expires = type.data?.expires,
            token = type.data?.token
        )
    }
}