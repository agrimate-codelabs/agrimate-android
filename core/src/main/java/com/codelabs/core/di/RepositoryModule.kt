package com.codelabs.core.di

import com.codelabs.core.data.impl.AuthRepositoryImpl
import com.codelabs.core.data.impl.CommodityRepositoryImpl
import com.codelabs.core.data.impl.DataStoreRepositoryImpl
import com.codelabs.core.data.impl.FarmerLandRepositoryImpl
import com.codelabs.core.data.impl.HarvestRepositoryImpl
import com.codelabs.core.data.impl.NewsRepositoryImpl
import com.codelabs.core.data.impl.PlantDiseaseRepositoryImpl
import com.codelabs.core.data.impl.PlantingRepositoryImpl
import com.codelabs.core.data.impl.RegionRepositoryImpl
import com.codelabs.core.domain.repository.AuthRepository
import com.codelabs.core.domain.repository.CommodityRepository
import com.codelabs.core.domain.repository.DataStoreRepository
import com.codelabs.core.domain.repository.FarmerLandRepository
import com.codelabs.core.domain.repository.HarvestRepository
import com.codelabs.core.domain.repository.NewsRepository
import com.codelabs.core.domain.repository.PlantDiseaseRepository
import com.codelabs.core.domain.repository.PlantingRepository
import com.codelabs.core.domain.repository.RegionRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    abstract fun provideAuthRepository(authRepository: AuthRepositoryImpl): AuthRepository

    @Binds
    abstract fun provideRegionRepository(regionRepository: RegionRepositoryImpl): RegionRepository

    @Binds
    abstract fun provideDataStoreRepository(dataStoreRepository: DataStoreRepositoryImpl): DataStoreRepository

    @Binds
    abstract fun providePlantDiseaseRepository(plantDiseaseRepository: PlantDiseaseRepositoryImpl): PlantDiseaseRepository

    @Binds
    abstract fun provideFarmerLandRepository(farmerLandRepository: FarmerLandRepositoryImpl): FarmerLandRepository

    @Binds
    abstract fun provideCommodityRepository(commodityRepository: CommodityRepositoryImpl): CommodityRepository

    @Binds
    abstract fun providePlantingRepository(plantingRepository: PlantingRepositoryImpl): PlantingRepository

    @Binds
    abstract fun provideHarvestRepository(harvestRepository: HarvestRepositoryImpl): HarvestRepository

    @Binds
    abstract fun provideNewsRepository(newsRepository: NewsRepositoryImpl): NewsRepository
}