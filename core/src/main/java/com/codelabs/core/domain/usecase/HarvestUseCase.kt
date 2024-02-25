package com.codelabs.core.domain.usecase

import com.codelabs.core.data.source.remote.body.CreateHarvestBody
import com.codelabs.core.utils.Resource
import kotlinx.coroutines.flow.Flow

interface HarvestUseCase {
    fun saveSuccess(body: CreateHarvestBody): Flow<Resource<String>>

    fun saveFailed(body: CreateHarvestBody): Flow<Resource<String>>
}