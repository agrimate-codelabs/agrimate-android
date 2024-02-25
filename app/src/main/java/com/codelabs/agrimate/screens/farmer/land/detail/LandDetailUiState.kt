package com.codelabs.agrimate.screens.farmer.land.detail

import com.codelabs.core.domain.model.PlantingListItemModel
import com.codelabs.core.domain.model.RemainingLandAreaModel
import com.codelabs.core.utils.Resource

data class LandDetailUiState(
    val listOfPlatingItem: Resource<List<PlantingListItemModel>> = Resource.Loading(),
    val remainingLandArea: Resource<RemainingLandAreaModel> = Resource.Loading(),
)
