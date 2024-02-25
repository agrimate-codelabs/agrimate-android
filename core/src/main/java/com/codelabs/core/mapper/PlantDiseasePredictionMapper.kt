package com.codelabs.core.mapper

import com.codelabs.core.data.source.remote.response.diseasedetection.PredictionsItemResponse
import com.codelabs.core.domain.model.PlantDiseasePredictionModel
import com.codelabs.core.utils.Mapper
import javax.inject.Inject

class PlantDiseasePredictionMapper @Inject constructor() :
    Mapper<PredictionsItemResponse, PlantDiseasePredictionModel> {
    override fun mapFromResponseToModel(type: PredictionsItemResponse): PlantDiseasePredictionModel {
        return PlantDiseasePredictionModel(
            id = type.tagId,
            probability = type.probability,
            name = type.tagName
        )
    }
}