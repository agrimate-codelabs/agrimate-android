package com.codelabs.core.mapper

import com.codelabs.core.data.source.remote.response.Response
import com.codelabs.core.data.source.remote.response.auth.AuthMeResponse
import com.codelabs.core.domain.model.AuthMeModel
import com.codelabs.core.utils.Mapper
import javax.inject.Inject

class AuthMeMapper @Inject constructor() : Mapper<Response<AuthMeResponse>, AuthMeModel> {
    override fun mapFromResponseToModel(type: Response<AuthMeResponse>): AuthMeModel =
        AuthMeModel(
            id = type.data?.id,
            email = type.data?.email,
            role = type.data?.role,
            name = type.data?.userDetail?.name,
        )

}