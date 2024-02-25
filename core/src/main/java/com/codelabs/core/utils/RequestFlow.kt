package com.codelabs.core.utils

import com.codelabs.core.data.source.remote.response.Response
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.HttpException

fun <Request, Model> apiRequestFlow(
    call: suspend () -> Response<Request>,
    mapper: Mapper<Response<Request>, Model>
): Flow<Resource<Model>> = flow {
    emit(Resource.Loading())
    try {
        val response = call()
        val model = mapper.mapFromResponseToModel(response)
        emit(Resource.Success(model))
    } catch (e: HttpException) {
        if (e.code() == 401) {
            emit(Resource.Error("Unauthorized"))
        } else {
            try {
                val responseMessage = ErrorUtils.createErrorResponse<Request>(e)
                val model = mapper.mapFromResponseToModel(responseMessage)
                emit(Resource.Error(message = e.message(), data = model))
            } catch (e: Exception) {
                emit(Resource.Error(e.message.toString()))
            }
        }
    } catch (e: Exception) {
        emit(Resource.Error(e.message.toString()))
    }
}.flowOn(Dispatchers.IO)