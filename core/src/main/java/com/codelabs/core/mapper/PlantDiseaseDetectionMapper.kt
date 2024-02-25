package com.codelabs.core.mapper

import com.codelabs.core.data.source.remote.response.Response
import com.codelabs.core.data.source.remote.response.diseasedetection.DiseaseDetectionResponse
import com.codelabs.core.domain.model.PlantDiseaseDetectionModel
import com.codelabs.core.utils.Mapper
import javax.inject.Inject

class PlantDiseaseDetectionMapper @Inject constructor() :
    Mapper<Response<DiseaseDetectionResponse>, PlantDiseaseDetectionModel> {
    override fun mapFromResponseToModel(type: Response<DiseaseDetectionResponse>): PlantDiseaseDetectionModel {
        return PlantDiseaseDetectionModel(
            id = type.data?.id.orEmpty(),
            image = type.data?.image.orEmpty(),
            name = type.data?.name.orEmpty(),
            symtomps = type.data?.symtomps.orEmpty(),
            howTo = type.data?.howTo.orEmpty(),
            createdAt = type.data?.createdAt.orEmpty(),
            updatedAt = type.data?.updatedAt.orEmpty()
        )
    }
}