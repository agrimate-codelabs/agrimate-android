package com.codelabs.core.domain.usecase

import com.codelabs.core.domain.model.RegionModel
import com.codelabs.core.domain.repository.RegionRepository
import com.codelabs.core.utils.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RegionInteractor @Inject constructor(private val regionRepository: RegionRepository) :
    RegionUseCase {
    override fun getListOfProvince(): Flow<Resource<List<RegionModel>>> =
        regionRepository.getListRegion(null, "province")

    override fun getListOfCity(provinceCode: String): Flow<Resource<List<RegionModel>>> =
        regionRepository.getListRegion(provinceCode, "city")

    override fun getListOfDistrict(cityCode: String): Flow<Resource<List<RegionModel>>> =
        regionRepository.getListRegion(cityCode, "district")

    override fun getListOfVillage(districtCode: String): Flow<Resource<List<RegionModel>>> =
        regionRepository.getListRegion(districtCode, "village")
}