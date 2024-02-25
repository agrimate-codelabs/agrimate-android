package com.codelabs.core.di

import com.codelabs.core.BuildConfig
import com.codelabs.core.data.source.remote.network.AuthService
import com.codelabs.core.data.source.remote.network.CommodityService
import com.codelabs.core.data.source.remote.network.FarmerLandService
import com.codelabs.core.data.source.remote.network.HarvestService
import com.codelabs.core.data.source.remote.network.LandActivityService
import com.codelabs.core.data.source.remote.network.PlantDiseaseDetectionService
import com.codelabs.core.data.source.remote.network.PlantingService
import com.codelabs.core.data.source.remote.network.RegionService
import com.codelabs.core.utils.AuthAuthenticator
import com.codelabs.core.utils.AuthInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {
    @Provides
    @Singleton
    fun provideOkhttpInterceptor(
        authInterceptor: AuthInterceptor,
        authAuthenticator: AuthAuthenticator
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .addInterceptor(authInterceptor)
            .authenticator(authAuthenticator)
            .connectTimeout(120, TimeUnit.SECONDS)
            .readTimeout(120, TimeUnit.SECONDS)
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder().client(okHttpClient)
            .baseUrl(BuildConfig.API_AGRIMATE_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideAuthService(retrofit: Retrofit): AuthService {
        return retrofit.create(AuthService::class.java)
    }

    @Provides
    @Singleton
    fun provideRegionService(retrofit: Retrofit): RegionService {
        return retrofit.create(RegionService::class.java)
    }

    @Provides
    @Singleton
    fun provideFarmerLandService(retrofit: Retrofit): FarmerLandService {
        return retrofit.create(FarmerLandService::class.java)
    }

    @Provides
    @Singleton
    fun providePlantDiseaseDetectionService(retrofit: Retrofit): PlantDiseaseDetectionService =
        retrofit.create(PlantDiseaseDetectionService::class.java)

    @Provides
    @Singleton
    fun provideCommodityService(retrofit: Retrofit): CommodityService =
        retrofit.create(CommodityService::class.java)

    @Provides
    @Singleton
    fun providePlantingService(retrofit: Retrofit): PlantingService =
        retrofit.create(PlantingService::class.java)

    @Provides
    @Singleton
    fun provideHarvestService(retrofit: Retrofit): HarvestService =
        retrofit.create(HarvestService::class.java)

    @Provides
    @Singleton
    fun landActivityService(retrofit: Retrofit): LandActivityService =
        retrofit.create(LandActivityService::class.java)

}