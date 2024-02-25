package com.codelabs.core.mapper

import com.codelabs.core.data.source.remote.response.Response
import com.codelabs.core.data.source.remote.response.auth.SignInResponse
import com.codelabs.core.domain.model.SignInModel
import com.codelabs.core.utils.Mapper
import javax.inject.Inject

open class SignInMapper @Inject constructor() : Mapper<Response<SignInResponse>, SignInModel> {
    override fun mapFromResponseToModel(type: Response<SignInResponse>): SignInModel {
        return SignInModel(
            code = type.code,
            message = type.message,
            accessToken = type.data?.accessToken,
            refreshToken = type.data?.refreshToken,
        )
    }
}