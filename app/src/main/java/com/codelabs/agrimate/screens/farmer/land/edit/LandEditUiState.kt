package com.codelabs.agrimate.screens.farmer.land.edit

import com.codelabs.agrimate.common.FormHandler
import com.codelabs.core.domain.model.RegionModel
import com.google.android.gms.maps.model.LatLng

data class LandEditUiState(
    val landName: String = "",
    val landMarker: List<LatLng> = listOf(),
    val province: RegionModel = RegionModel("", ""),
    val city: RegionModel = RegionModel("", ""),
    val district: RegionModel = RegionModel("", ""),
    val village: RegionModel = RegionModel("", ""),
    val address: String = "",
    val imageUrl: String = "",
    val landArea: String = "",

    val inputLandName: FormHandler = FormHandler(true, ""),
    val inputLandMarker: FormHandler = FormHandler(true, ""),
    val inputProvince: FormHandler = FormHandler(true, ""),
    val inputCity: FormHandler = FormHandler(true, ""),
    val inputDistrict: FormHandler = FormHandler(true, ""),
    val inputVillage: FormHandler = FormHandler(true, ""),
    val inputAddress: FormHandler = FormHandler(true, ""),
    val inputImage: FormHandler = FormHandler(true, ""),
    val inputLandArea: FormHandler = FormHandler(true, ""),

    val isSuccess: Boolean = false,
    val isLoading: Boolean = false,
    val isUploadingImage: Boolean = false,
    val isGettingData: Boolean = true
)