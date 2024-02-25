package com.codelabs.agrimate.di

import com.codelabs.core.domain.usecase.AuthInteractor
import com.codelabs.core.domain.usecase.AuthUseCase
import com.codelabs.core.domain.usecase.CommodityInteractor
import com.codelabs.core.domain.usecase.CommodityUseCase
import com.codelabs.core.domain.usecase.DataStoreInteractor
import com.codelabs.core.domain.usecase.DataStoreUseCase
import com.codelabs.core.domain.usecase.FarmerLandDetailInteractor
import com.codelabs.core.domain.usecase.FarmerLandDetailUseCase
import com.codelabs.core.domain.usecase.FarmerLandInteractor
import com.codelabs.core.domain.usecase.FarmerLandUseCase
import com.codelabs.core.domain.usecase.HarvestInteractor
import com.codelabs.core.domain.usecase.HarvestUseCase
import com.codelabs.core.domain.usecase.NewsInteractor
import com.codelabs.core.domain.usecase.NewsUseCase
import com.codelabs.core.domain.usecase.PlantDiseaseInteractor
import com.codelabs.core.domain.usecase.PlantDiseaseUseCase
import com.codelabs.core.domain.usecase.PlantingInteractor
import com.codelabs.core.domain.usecase.PlantingUseCase
import com.codelabs.core.domain.usecase.RegionInteractor
import com.codelabs.core.domain.usecase.RegionUseCase
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class AppModule {
    @Binds
    @Singleton
    abstract fun provideAuthUseCase(authInteractor: AuthInteractor): AuthUseCase

    @Binds
    @Singleton
    abstract fun provideRegionUseCase(regionInteractor: RegionInteractor): RegionUseCase

    @Binds
    @Singleton
    abstract fun provideDataStoreUseCase(dataStoreInteractor: DataStoreInteractor): DataStoreUseCase

    @Binds
    @Singleton
    abstract fun providePlantDiseaseUseCase(plantDiseaseInteractor: PlantDiseaseInteractor): PlantDiseaseUseCase

    @Binds
    @Singleton
    abstract fun provideFarmerLandUseCase(farmerLandInteractor: FarmerLandInteractor): FarmerLandUseCase

    @Binds
    @Singleton
    abstract fun provideFarmerLandDetailUse(farmerLandDetailInteractor: FarmerLandDetailInteractor): FarmerLandDetailUseCase

    @Binds
    @Singleton
    abstract fun provideCommodityUseCase(commodityInteractor: CommodityInteractor): CommodityUseCase

    @Binds
    @Singleton
    abstract fun providePlantingUseCase(plantingInteractor: PlantingInteractor): PlantingUseCase

    @Binds
    @Singleton
    abstract fun harvestUseCase(harvestInteractor: HarvestInteractor): HarvestUseCase

    @Binds
    @Singleton
    abstract fun newsUseCase(newsInteractor: NewsInteractor): NewsUseCase
}