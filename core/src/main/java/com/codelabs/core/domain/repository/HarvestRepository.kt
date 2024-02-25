package com.codelabs.core.domain.repository

import com.codelabs.core.data.source.remote.body.CreateHarvestBody
import com.codelabs.core.utils.Resource
import kotlinx.coroutines.flow.Flow

interface HarvestRepository {
    fun addSuccess(body: CreateHarvestBody): Flow<Resource<String>>
    fun addFailure(body: CreateHarvestBody): Flow<Resource<String>>
}