package com.codelabs.agrimate.screens.farmer.land.detail.activity.detail

import com.codelabs.agrimate.common.FormHandler

data class LandDetailActivityUiState(
    val isLoading: Boolean = false,

    val dateHarvest: String = "",
    val amountHarvest: String = "",

    val inputDateHarvest: FormHandler = FormHandler(true, ""),
    val inputAmountHarvest: FormHandler = FormHandler(true, ""),

    val isSaveSuccess: Boolean = false,
    val isSaveFailed: Boolean = false

)
