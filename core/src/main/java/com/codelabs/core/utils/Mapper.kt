package com.codelabs.core.utils

interface Mapper<Response, Model> {
    fun mapFromResponseToModel(type: Response): Model
}