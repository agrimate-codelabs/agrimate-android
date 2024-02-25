package com.codelabs.core.data.source.remote.response.diseasedetection

import com.google.gson.annotations.SerializedName

data class ResponseDiseaseDetectionAPI(
	@field:SerializedName("created")
	val created: String,

	@field:SerializedName("project")
	val project: String,

	@field:SerializedName("iteration")
	val iteration: String,

	@field:SerializedName("id")
	val id: String,

	@field:SerializedName("predictions")
	val predictions: List<PredictionsItemResponse>
)

data class PredictionsItemResponse(

	@field:SerializedName("tagId")
	val tagId: String,

	@field:SerializedName("probability")
	val probability: Double,

	@field:SerializedName("tagName")
	val tagName: String
)
