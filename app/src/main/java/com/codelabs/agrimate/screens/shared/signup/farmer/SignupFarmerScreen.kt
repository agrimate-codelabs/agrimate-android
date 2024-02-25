package com.codelabs.agrimate.screens.shared.signup.farmer

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.codelabs.agrimate.ui.common.impl.RegionSelectInputImpl
import com.codelabs.agrimate.ui.components.AGAlreadyHaveAccount
import com.codelabs.agrimate.ui.components.AGAuthTopBanner
import com.codelabs.agrimate.ui.components.AGButton
import com.codelabs.agrimate.ui.components.AGInputEmail
import com.codelabs.agrimate.ui.components.AGInputFullAddress
import com.codelabs.agrimate.ui.components.AGInputId
import com.codelabs.agrimate.ui.components.AGInputLayout
import com.codelabs.agrimate.ui.components.AGInputPassword
import com.codelabs.agrimate.ui.components.AGInputPhoneNumber
import com.codelabs.agrimate.ui.components.AGMessageDialogSuccess
import com.codelabs.agrimate.ui.components.AGSelectInputWithSearch
import com.codelabs.agrimate.ui.navigation.AGRoute
import com.codelabs.core.domain.model.RegionModel
import com.codelabs.core.utils.Resource
import kotlinx.coroutines.delay

@Composable
fun SignupFarmerScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    viewModel: SignupFarmerViewModel = hiltViewModel(),
) {
    val context = LocalContext.current
    val focusManager = LocalFocusManager.current
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val listOfProvince by viewModel.listOfProvince.observeAsState()
    val listOfCity by viewModel.listOfCity.observeAsState()
    val listOfDistrict by viewModel.listOfDistrict.observeAsState()
    val listOfVillage by viewModel.listOfVillage.observeAsState()

    val navigateToSignIn = {
        navController.currentDestination?.id?.let { navController.popBackStack(it, true) }
        navController.navigate(AGRoute.Auth.SignIn.route) {
            popUpTo(AGRoute.Auth.SignIn.route) {
                inclusive = true
            }
            launchSingleTop = true
        }
    }

    LaunchedEffect(Unit) {
        viewModel.toastMessage.collect { message ->
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
        }
    }

    SignupContent(
        modifier = modifier.imePadding(),
        navigateToSignIn = navigateToSignIn,
        listOfProvince = listOfProvince,
        listOfCity = listOfCity,
        listOfDistrict = listOfDistrict,
        listOfVillage = listOfVillage,
        onNameChange = viewModel::updateName,
        onEmailChange = viewModel::updateEmail,
        onPhoneNumberChange = viewModel::updatePhoneNumber,
        onProvinceChange = viewModel::updateProvince,
        onCityChange = viewModel::updateCity,
        onDistrictChange = viewModel::updateDistrict,
        onVillagesChange = viewModel::updateVillages,
        onFullAddressChange = viewModel::updateFullAddress,
        onPasswordChange = viewModel::updatePassword,
        onConfirmPasswordChange = viewModel::updateConfirmPassword,
        uiState = uiState,
        onSignupClick = {
            focusManager.clearFocus()
            viewModel.signUp()
        },
    )

    if (uiState.isLoading) {
        Dialog(onDismissRequest = {}) {
            CircularProgressIndicator()
        }
    }

    if (uiState.isSuccess) {
        LaunchedEffect(Unit) {
            delay(5000)
            navigateToSignIn()
        }
        AGMessageDialogSuccess(
            onDismissRequest = { navigateToSignIn() },
            title = "Daftar Petani Berhasil!",
            description = "Silahkan verifikasi email Anda!"
        )
    }
}

@Composable
private fun SignupContent(
    modifier: Modifier = Modifier,
    navigateToSignIn: () -> Unit,
    listOfProvince: Resource<List<RegionSelectInputImpl>>?,
    listOfCity: Resource<List<RegionSelectInputImpl>>?,
    listOfDistrict: Resource<List<RegionSelectInputImpl>>?,
    listOfVillage: Resource<List<RegionSelectInputImpl>>?,
    onNameChange: (String) -> Unit,
    onEmailChange: (String) -> Unit,
    onPhoneNumberChange: (String) -> Unit,
    onProvinceChange: (RegionModel) -> Unit,
    onCityChange: (RegionModel) -> Unit,
    onDistrictChange: (RegionModel) -> Unit,
    onVillagesChange: (RegionModel) -> Unit,
    onFullAddressChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onConfirmPasswordChange: (String) -> Unit,
    uiState: SignupFarmerUiState,
    onSignupClick: () -> Unit,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .verticalScroll(rememberScrollState())
    ) {
        AGAuthTopBanner(title = "Daftar Akun Petani")
        Spacer(modifier = Modifier.padding(bottom = 6.dp))
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 18.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            AGInputLayout(modifier = Modifier.fillMaxWidth(), label = "Nama") {
                AGInputId(
                    modifier = Modifier.fillMaxWidth(),
                    value = uiState.name,
                    onValueChange = onNameChange,
                    placeholder = "Masukkan nama Anda",
                    imeAction = ImeAction.Next,
                    isError = !uiState.inputName.isValid,
                    supportingText = if (!uiState.inputName.isValid) {
                        { Text(text = uiState.inputName.message) }
                    } else null
                )
            }
            AGInputLayout(modifier = Modifier.fillMaxWidth(), label = "Email") {
                AGInputEmail(
                    modifier = Modifier.fillMaxWidth(),
                    value = uiState.email,
                    onValueChange = onEmailChange,
                    placeholder = "Masukkan email Anda",
                    imeAction = ImeAction.Next,
                    isError = !uiState.inputEmail.isValid,
                    supportingText = if (!uiState.inputEmail.isValid) {
                        { Text(text = uiState.inputEmail.message) }
                    } else null
                )
            }
            AGInputLayout(modifier = Modifier.fillMaxWidth(), label = "Nomor Telepon") {
                AGInputPhoneNumber(
                    modifier = Modifier.fillMaxWidth(),
                    value = uiState.phoneNumber,
                    onValueChange = onPhoneNumberChange,
                    placeholder = "contoh: 08123456789",
                    imeAction = ImeAction.Done,
                    isError = !uiState.inputPhoneNumber.isValid,
                    supportingText = if (!uiState.inputPhoneNumber.isValid) {
                        { Text(text = uiState.inputPhoneNumber.message) }
                    } else null
                )
            }
            AGInputLayout(modifier = Modifier.fillMaxWidth(), label = "Provinsi") {
                AGSelectInputWithSearch(
                    modifier = Modifier.fillMaxWidth(),
                    value = uiState.province.name,
                    onValueChange = { onProvinceChange(RegionModel(it.value, it.label)) },
                    placeholder = "Pilih Provinsi",
                    label = "Provinsi",
                    options = when (listOfProvince) {
                        is Resource.Success -> listOfProvince.data!!
                        else -> emptyList()
                    },
                    isLoading = listOfProvince is Resource.Loading,
                    isError = !uiState.inputProvince.isValid,
                    supportingText = if (!uiState.inputProvince.isValid) {
                        { Text(text = uiState.inputProvince.message) }
                    } else null
                )
            }
            AGInputLayout(modifier = Modifier.fillMaxWidth(), label = "Kota") {
                AGSelectInputWithSearch(
                    modifier = Modifier.fillMaxWidth(),
                    value = uiState.city.name,
                    onValueChange = { onCityChange(RegionModel(it.value, it.label)) },
                    placeholder = "Pilih Kota",
                    label = "Kota",
                    options = when (listOfCity) {
                        is Resource.Success -> listOfCity.data!!
                        else -> emptyList()
                    },
                    isLoading = listOfCity is Resource.Loading,
                    message = "Kota Tidak Ditemukan.\nPilih provinsi terlebih dahulu!",
                    isError = !uiState.inputCity.isValid,
                    supportingText = if (!uiState.inputCity.isValid) {
                        { Text(text = uiState.inputCity.message) }
                    } else null
                )
            }
            AGInputLayout(modifier = Modifier.fillMaxWidth(), label = "Kecamatan") {
                AGSelectInputWithSearch(
                    modifier = Modifier.fillMaxWidth(),
                    value = uiState.district.name,
                    onValueChange = { onDistrictChange(RegionModel(it.value, it.label)) },
                    placeholder = "Pilih Kecamatan",
                    label = "Kecamatan",
                    options = when (listOfDistrict) {
                        is Resource.Success -> listOfDistrict.data!!
                        else -> emptyList()
                    },
                    isLoading = listOfDistrict is Resource.Loading,
                    message = "Kecamatan Tidak Ditemukan.\nPilih kota terlebih dahulu!",
                    isError = !uiState.inputDistrict.isValid,
                    supportingText = if (!uiState.inputDistrict.isValid) {
                        { Text(text = uiState.inputDistrict.message) }
                    } else null
                )
            }
            AGInputLayout(modifier = Modifier.fillMaxWidth(), label = "Kelurahan/Desa") {
                AGSelectInputWithSearch(
                    modifier = Modifier.fillMaxWidth(),
                    value = uiState.villages.name,
                    onValueChange = { onVillagesChange(RegionModel(it.value, it.label)) },
                    placeholder = "Pilih Kelurahan/Desa",
                    label = "Kelurahan/Desa",
                    options = when (listOfVillage) {
                        is Resource.Success -> listOfVillage.data!!
                        else -> emptyList()
                    },
                    isLoading = listOfVillage is Resource.Loading,
                    message = "Desa Tidak Ditemukan.\nPilih kecamatan terlebih dahulu!",
                    isError = !uiState.inputVillage.isValid,
                    supportingText = if (!uiState.inputVillage.isValid) {
                        { Text(text = uiState.inputVillage.message) }
                    } else null
                )
            }
            AGInputLayout(modifier = Modifier.fillMaxWidth(), label = "Alamat lengkap") {
                AGInputFullAddress(
                    modifier = Modifier.fillMaxWidth(),
                    value = uiState.fullAddress,
                    onValueChange = onFullAddressChange,
                    placeholder = "contoh: Jl. Gatot Subroto, No. 123, RT.09/RW.01",
                    imeAction = ImeAction.Next,
                    isError = !uiState.inputFullAddress.isValid,
                    supportingText = if (!uiState.inputFullAddress.isValid) {
                        { Text(text = uiState.inputFullAddress.message) }
                    } else null
                )
            }
            AGInputLayout(modifier = Modifier.fillMaxWidth(), label = "Password") {
                AGInputPassword(
                    modifier = Modifier.fillMaxWidth(),
                    value = uiState.password,
                    onValueChange = onPasswordChange,
                    placeholder = "Masukkan password Anda",
                    imeAction = ImeAction.Next,
                    isError = !uiState.inputPassword.isValid,
                    supportingText = if (!uiState.inputPassword.isValid) {
                        { Text(text = uiState.inputPassword.message) }
                    } else null
                )
            }
            AGInputLayout(modifier = Modifier.fillMaxWidth(), label = "Konfirmasi password") {
                AGInputPassword(
                    modifier = Modifier.fillMaxWidth(),
                    value = uiState.confirmPassword,
                    onValueChange = onConfirmPasswordChange,
                    placeholder = "Ketik ulang password Anda",
                    imeAction = ImeAction.Done,
                    isError = !uiState.inputConfirmPassword.isValid,
                    supportingText = if (!uiState.inputConfirmPassword.isValid) {
                        { Text(text = uiState.inputConfirmPassword.message) }
                    } else null
                )
            }
            Column(modifier.fillMaxWidth()) {
                AGButton(
                    modifier = Modifier.fillMaxWidth(),
                    onClick = onSignupClick,
                    contentPadding = PaddingValues(horizontal = 40.dp, vertical = 20.dp),
                ) {
                    Text(text = "Daftar")
                }
                Spacer(modifier = Modifier.padding(bottom = 10.dp))
                AGAlreadyHaveAccount(navigateToSignin = navigateToSignIn)
            }
        }
        Spacer(modifier = Modifier.padding(bottom = 40.dp))
    }
}