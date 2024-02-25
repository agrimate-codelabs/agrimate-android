package com.codelabs.agrimate.screens.shared.resetpassword

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.codelabs.agrimate.ui.components.AGAuthTopBanner
import com.codelabs.agrimate.ui.components.AGButton
import com.codelabs.agrimate.ui.components.AGInputLayout
import com.codelabs.agrimate.ui.components.AGInputPassword
import com.codelabs.agrimate.ui.navigation.AGRoute

@Composable
fun ResetPasswordScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    viewModel: ResetPasswordViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val uiState = viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.toastMessage.collect { message ->
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
        }
    }

    ResetPasswordContent(
        modifier = modifier,
        uiState = uiState.value,
        updatePassword = viewModel::updatePassword,
        updateConfirmPassword = viewModel::updateConfirmPassword,
        onPasswordSaveClick = viewModel::savePassword
    )

    if (uiState.value.isLoading) {
        Dialog(onDismissRequest = {}) {
            CircularProgressIndicator()
        }
    }

    if (uiState.value.isSuccess) {
        LaunchedEffect(Unit) {
            navController.currentDestination?.id?.let { navController.popBackStack(it, true) }
            navController.navigate(AGRoute.Auth.SignIn.route) {
                popUpTo(AGRoute.Auth.SignIn.route) {
                    inclusive = true
                }
                launchSingleTop = true
            }
        }
    }
}

@Composable
fun ResetPasswordContent(
    modifier: Modifier = Modifier,
    uiState: ResetPasswordUiState,
    updatePassword: (String) -> Unit,
    updateConfirmPassword: (String) -> Unit,
    onPasswordSaveClick: () -> Unit,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .verticalScroll(rememberScrollState())
    ) {
        AGAuthTopBanner(title = "Reset Password")
        Spacer(modifier = Modifier.padding(bottom = 20.dp))
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 18.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = "Masukan password baru akun Anda",
                color = Color.Gray,
                fontWeight = FontWeight.Medium,
                fontSize = 14.sp,
            )
            AGInputLayout(modifier = Modifier.fillMaxWidth(), label = "Password") {
                AGInputPassword(
                    modifier = Modifier.fillMaxWidth(),
                    value = uiState.password,
                    onValueChange = updatePassword,
                    placeholder = "Password",
                    imeAction = ImeAction.Next,
                )
            }
            AGInputLayout(modifier = Modifier.fillMaxWidth(), label = "Konfirmasi Password") {
                AGInputPassword(
                    modifier = Modifier.fillMaxWidth(),
                    value = uiState.confirmPassword,
                    onValueChange = updateConfirmPassword,
                    placeholder = "Konfirmasi Password",
                    imeAction = ImeAction.Done,
                )
            }
            AGButton(
                modifier = Modifier.fillMaxWidth(),
                onClick = onPasswordSaveClick,
                contentPadding = PaddingValues(horizontal = 40.dp, vertical = 20.dp),
            ) {
                Text(text = "Simpan Password")
            }
            Spacer(modifier = Modifier.padding(bottom = 40.dp))
        }
    }
}