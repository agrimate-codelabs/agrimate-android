package com.codelabs.core.utils

import com.codelabs.core.data.source.remote.response.PathMessage
import com.codelabs.core.data.source.remote.response.Response
import com.codelabs.core.data.source.remote.response.ResponseMessage
import com.google.gson.Gson
import com.google.gson.JsonObject
import retrofit2.HttpException

object ErrorUtils {
    fun <T> createErrorResponse(error: HttpException): Response<T> {
        val errorString = error.response()?.errorBody()?.string()
        try {
            val jsonObject = Gson().fromJson(errorString, JsonObject::class.java)
            val code = jsonObject.get("code").asInt
            val status = jsonObject.get("status").asString
            val message = jsonObject.get("message")
            val responseMessage = if (message.isJsonArray) {
                val list = ArrayList<PathMessage>()
                val messageJsonArray = Gson().fromJson(message, Array<PathMessage>::class.java)
                list.addAll(messageJsonArray)
                ResponseMessage.ArrayMessage(list)
            } else {
                ResponseMessage.StringMessage(message.asString)
            }
            return Response(code = code, status = status, message = responseMessage, data = null)
        } catch (e: Exception) {
            throw Exception(e.message.toString())
        }
    }
}
