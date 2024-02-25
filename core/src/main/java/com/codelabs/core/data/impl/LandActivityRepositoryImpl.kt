package com.codelabs.core.data.impl

import com.codelabs.core.data.source.remote.LandActivityRemoteDataSource
import com.codelabs.core.domain.repository.LandActivityRepository
import javax.inject.Inject

class LandActivityRepositoryImpl @Inject constructor(private val landActivityRemoteDataSource: LandActivityRemoteDataSource) :
    LandActivityRepository {
}