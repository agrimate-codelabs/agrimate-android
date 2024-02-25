package com.codelabs.core.data.source.remote.body

data class SignUpBody(
    val confirmPassword: String,
    val password: String,
    val role: String,
    val email: String,
    val name: String,
    val phone: String,
    val provinceId: String,
    val cityId: String,
    val districtId: String,
    val villageId: String,
    val address: String,
)

object SignUpRole {
    const val FARMER = "farmer"
    const val COLLECTOR = "collector"
}