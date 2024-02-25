package com.codelabs.core.domain.usecase

import com.codelabs.core.domain.model.CommodityModel
import com.codelabs.core.utils.Resource
import kotlinx.coroutines.flow.Flow

interface CommodityUseCase {
    fun getAll(): Flow<Resource<List<CommodityModel>>>
}