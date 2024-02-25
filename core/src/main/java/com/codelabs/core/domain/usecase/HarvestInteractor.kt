package com.codelabs.core.domain.usecase

import com.codelabs.core.data.source.remote.body.CreateHarvestBody
import com.codelabs.core.domain.repository.HarvestRepository
import com.codelabs.core.utils.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class HarvestInteractor @Inject constructor(private val harvestRepository: HarvestRepository) :
    HarvestUseCase {
    override fun saveSuccess(body: CreateHarvestBody): Flow<Resource<String>> =
        harvestRepository.addSuccess(body)

    override fun saveFailed(body: CreateHarvestBody): Flow<Resource<String>> =
        harvestRepository.addFailure(body)

}