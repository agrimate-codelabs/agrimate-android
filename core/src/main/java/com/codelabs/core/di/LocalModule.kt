package com.codelabs.core.di

import android.content.Context
import com.codelabs.core.data.source.local.LocalDataStore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object LocalModule {
    @Provides
    @Singleton
    fun provideLocalDataStore(@ApplicationContext context: Context): LocalDataStore {
        return LocalDataStore(context)
    }
}