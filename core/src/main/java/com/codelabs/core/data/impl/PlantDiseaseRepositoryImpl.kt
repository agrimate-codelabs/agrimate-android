package com.codelabs.core.data.impl

import com.codelabs.core.data.source.remote.DiseaseDetectionRemoteDataSource
import com.codelabs.core.domain.model.PlantDiseaseDetectionModel
import com.codelabs.core.domain.model.PlantDiseasePredictionModel
import com.codelabs.core.domain.repository.PlantDiseaseRepository
import com.codelabs.core.mapper.PlantDiseaseDetectionMapper
import com.codelabs.core.mapper.PlantDiseasePredictionMapper
import com.codelabs.core.utils.Resource
import com.google.gson.Gson
import com.google.gson.JsonObject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.HttpException
import javax.inject.Inject

class PlantDiseaseRepositoryImpl @Inject constructor(
    private val diseaseDetectionRemoteDataSource: DiseaseDetectionRemoteDataSource,
    private val plantDiseasePredictionMapper: PlantDiseasePredictionMapper,
    private val plantDiseaseDetectionMapper: PlantDiseaseDetectionMapper
) :
    PlantDiseaseRepository {
    override fun predictPlantDisease(image: RequestBody): Flow<Resource<List<PlantDiseasePredictionModel>>> =
        flow {
            emit(Resource.Loading())
            try {
                val response = diseaseDetectionRemoteDataSource.checkDisease(image)
                val listPrediction = response.predictions.map {
                    plantDiseasePredictionMapper.mapFromResponseToModel(it)
                }
                emit(Resource.Success(listPrediction))
            } catch (e: HttpException) {
                emit(Resource.Error(e.message.toString()))
            } catch (e: Exception) {
                emit(Resource.Error(e.message.toString()))
            }
        }

    override fun predictPlantDisease(file: MultipartBody.Part): Flow<Resource<PlantDiseaseDetectionModel>> =
        flow {
            emit(Resource.Loading())
            try {
                val response = diseaseDetectionRemoteDataSource.checkDisease(file)
                val model = plantDiseaseDetectionMapper.mapFromResponseToModel(response)
                emit(Resource.Success(model))
            } catch (e: HttpException) {
                if (e.code() == 422) {
                    try {
                        val jsonObject = Gson().fromJson(
                            e.response()?.errorBody()?.string(),
                            JsonObject::class.java
                        )
                        val message = jsonObject.get("message").asString
                        emit(Resource.Error(message))

                    } catch (e: Exception) {
                        emit(Resource.Error(message = e.message.toString()))
                    }
                } else {
                    emit(Resource.Error(e.message.toString()))
                }
            } catch (e: Exception) {
                emit(Resource.Error(e.message.toString()))
            }
        }

    override fun getDiseasePlant(name: String): Flow<Resource<PlantDiseaseDetectionModel>> =
        flow {
            emit(Resource.Loading())
            try {
                val response = diseaseDetectionRemoteDataSource.getDiseasesPlants(name)
                val diseasePlant = response.data?.get(0)

                val model = PlantDiseaseDetectionModel(
                    id = diseasePlant?.id.orEmpty(),
                    name = diseasePlant?.name.orEmpty(),
                    image = diseasePlant?.image.orEmpty(),
                    symtomps = diseasePlant?.symtomps.orEmpty(),
                    howTo = diseasePlant?.howTo.orEmpty(),
                    createdAt = diseasePlant?.createdAt.orEmpty(),
                    updatedAt = diseasePlant?.updatedAt.orEmpty()
                )

                emit(Resource.Success(model))
            } catch (e: HttpException) {
                emit(Resource.Error(e.message.toString()))
            } catch (e: Exception) {
                emit(Resource.Error(e.message.toString()))
            }
        }

}