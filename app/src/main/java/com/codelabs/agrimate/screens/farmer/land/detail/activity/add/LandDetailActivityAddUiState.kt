package com.codelabs.agrimate.screens.farmer.land.detail.activity.add

import com.codelabs.agrimate.common.FormHandler
import com.codelabs.agrimate.ui.common.impl.CroppingPatternSelectInputImpl
import com.codelabs.agrimate.ui.common.impl.TypeOfPlatingSelectInputImpl
import com.codelabs.agrimate.ui.common.impl.UnitSelectInputImpl
import com.codelabs.agrimate.ui.common.impl.UnitState
import com.codelabs.core.domain.model.CommodityModel

data class LandDetailActivityAddUiState(
    val commodity: CommodityModel = CommodityModel("", ""),
    val landArea: String = "",
    val datePlant: String = "",
    val plantAmount: String = "",
    val productionCost: String = "",
    val typeOfPlating: TypeOfPlantingModel = TypeOfPlantingModel("", "", ""),
    val unit: UnitSelectInputImpl = UnitSelectInputImpl("hektare", "hektare", UnitState.HA),

    val remainingLandArea: Double = 0.0,

    val inputLandArea: FormHandler = FormHandler(true, ""),
    val inputCommodity: FormHandler = FormHandler(true, ""),
    val inputDatePlant: FormHandler = FormHandler(true, ""),
    val inputPlantAmount: FormHandler = FormHandler(true, ""),
    val inputProductionCost: FormHandler = FormHandler(true, ""),
    val inputTypeOfPlanting: FormHandler = FormHandler(true, ""),
    val inputLandMarker: FormHandler = FormHandler(true, ""),

    val listOfCroppingPattern: List<CroppingPatternSelectInputImpl> = mutableListOf(
        CroppingPatternSelectInputImpl("Persegi", "Persegi"),
        CroppingPatternSelectInputImpl("Lingkaran", "Lingkaran")
    ),

    val listTypeOfPlating: List<TypeOfPlatingSelectInputImpl> = mutableListOf(
        TypeOfPlatingSelectInputImpl("Bibit", "Benih", "kg"),
        TypeOfPlatingSelectInputImpl("Benih", "Benih", "kg"),
        TypeOfPlatingSelectInputImpl("Pohon", "pohon", "buah")
    ),

    val listOfUnit: List<UnitSelectInputImpl> = mutableListOf(
        UnitSelectInputImpl("hektare", "hektare", state = UnitState.HA),
        UnitSelectInputImpl("m²", "m²", state = UnitState.M2)
    ),

    val isLoading: Boolean = false,
    val isSuccess: Boolean = false,
    val isGettingData: Boolean = true,
)

data class TypeOfPlantingModel(
    val label: String,
    val value: String,
    val unit: String,
)