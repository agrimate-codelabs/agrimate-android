package com.codelabs.core.domain.usecase

import com.codelabs.core.domain.model.RegionModel
import com.codelabs.core.utils.Resource
import kotlinx.coroutines.flow.Flow

interface RegionUseCase {
    fun getListOfProvince(): Flow<Resource<List<RegionModel>>>
    fun getListOfCity(provinceCode: String): Flow<Resource<List<RegionModel>>>
    fun getListOfDistrict(cityCode: String): Flow<Resource<List<RegionModel>>>
    fun getListOfVillage(districtCode: String): Flow<Resource<List<RegionModel>>>
}