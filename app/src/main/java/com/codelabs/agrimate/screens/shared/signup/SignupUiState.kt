package com.codelabs.agrimate.screens.shared.signup

import com.codelabs.agrimate.common.FormHandler
import com.codelabs.agrimate.ui.common.impl.ProfessionSelectInputImpl
import com.codelabs.core.domain.model.RegionModel

data class SignupUiState(
    val role: ProfessionSelectInputImpl = ProfessionSelectInputImpl("", ""),
    val name: String = "",
    val email: String = "",
    val phoneNumber: String = "",
    val province: RegionModel = RegionModel("", ""),
    val city: RegionModel = RegionModel("", ""),
    val district: RegionModel = RegionModel("", ""),
    val villages: RegionModel = RegionModel("", ""),
    val fullAddress: String = "",
    val password: String = "",
    val confirmPassword: String = "",

    val inputRole: FormHandler = FormHandler(true, ""),
    val inputName: FormHandler = FormHandler(true, ""),
    val inputEmail: FormHandler = FormHandler(true, ""),
    val inputPhoneNumber: FormHandler = FormHandler(true, ""),
    val inputProvince: FormHandler = FormHandler(true, ""),
    val inputCity: FormHandler = FormHandler(true, ""),
    val inputDistrict: FormHandler = FormHandler(true, ""),
    val inputVillage: FormHandler = FormHandler(true, ""),
    val inputFullAddress: FormHandler = FormHandler(true, ""),
    val inputPassword: FormHandler = FormHandler(true, ""),
    val inputConfirmPassword: FormHandler = FormHandler(true, ""),

    val isSuccess: Boolean = false,
    val isLoading: Boolean = false,

    val listOfProfession: List<ProfessionSelectInputImpl> = mutableListOf(
        ProfessionSelectInputImpl("Petani", "farmer"),
        ProfessionSelectInputImpl("Pengepul", "collector")
    ),
)

