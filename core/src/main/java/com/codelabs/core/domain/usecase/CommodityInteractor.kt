package com.codelabs.core.domain.usecase

import com.codelabs.core.domain.model.CommodityModel
import com.codelabs.core.domain.repository.CommodityRepository
import com.codelabs.core.utils.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CommodityInteractor @Inject constructor(private val commodityRepository: CommodityRepository) :
    CommodityUseCase {
    override fun getAll(): Flow<Resource<List<CommodityModel>>> =
        commodityRepository.getAll()

}