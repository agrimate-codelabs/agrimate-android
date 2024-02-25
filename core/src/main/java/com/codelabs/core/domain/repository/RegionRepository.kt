package com.codelabs.core.domain.repository

import com.codelabs.core.domain.model.RegionModel
import com.codelabs.core.utils.Resource
import kotlinx.coroutines.flow.Flow

interface RegionRepository {
    fun getListRegion(code: String?, type: String): Flow<Resource<List<RegionModel>>>
}