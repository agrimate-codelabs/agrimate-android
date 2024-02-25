package com.codelabs.core.domain.repository

import com.codelabs.core.domain.model.CommodityModel
import com.codelabs.core.utils.Resource
import kotlinx.coroutines.flow.Flow

interface CommodityRepository {
    fun getAll(): Flow<Resource<List<CommodityModel>>>
}