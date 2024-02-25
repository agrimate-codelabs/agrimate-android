package com.codelabs.agrimate.screens.shared.signin

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.codelabs.agrimate.R
import com.codelabs.agrimate.ui.components.AGButton
import com.codelabs.agrimate.ui.components.AGInputEmail
import com.codelabs.agrimate.ui.components.AGInputLayout
import com.codelabs.agrimate.ui.components.AGInputPassword
import com.codelabs.agrimate.ui.components.AGNotHaveAccount
import com.codelabs.agrimate.ui.navigation.AGRoute
import com.codelabs.agrimate.ui.theme.Green100

@Composable
fun SignInScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    viewModel: SignInViewModel = hiltViewModel(),
) {
    val context = LocalContext.current
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    val navigateToSignup = {
        navController.navigate(AGRoute.Auth.SignUp.route) {
            launchSingleTop = true
        }
    }

    val navigateToForgotPassword = {
        navController.navigate(AGRoute.Auth.ForgotPassword.route) {
            launchSingleTop = true
        }
    }

    val navigateToHome = {
        navController.popBackStack()
        navController.navigate(AGRoute.Farmer.route)
    }

    LaunchedEffect(Unit) {
        viewModel.toastMessage.collect { message ->
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
        }
    }

    SignInContent(
        modifier = modifier.imePadding(),
        navigateToSignup = navigateToSignup,
        navigateToForgotPassword = navigateToForgotPassword,
        uiState = uiState,
        onLoginIdChange = viewModel::updateLoginId,
        onPasswordChange = viewModel::updatePassword,
        onSignInClick = {
            viewModel.signIn()
        }
    )

    if (uiState.isLoading) {
        Dialog(onDismissRequest = { }) {
            CircularProgressIndicator()
        }
    }

    if (uiState.isSuccess) {
        LaunchedEffect(Unit) {
            navigateToHome()
        }
    }
}

@Composable
fun SignInContent(
    modifier: Modifier = Modifier,
    navigateToSignup: () -> Unit,
    navigateToForgotPassword: () -> Unit,
    uiState: SignInUiState,
    onLoginIdChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onSignInClick: () -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .verticalScroll(rememberScrollState())
    ) {
        SignInBanner()
        Spacer(modifier = Modifier.padding(bottom = 44.dp))
        Column(
            modifier = modifier
                .fillMaxWidth()
                .padding(horizontal = 18.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            AGInputLayout(
                modifier = Modifier.fillMaxWidth(),
                label = "Email atau no Telp",
                isRequired = true
            ) {
                AGInputEmail(
                    modifier = Modifier.fillMaxWidth(),
                    value = uiState.loginId,
                    onValueChange = onLoginIdChange,
                    placeholder = "Masukkan email atau no telp",
                    imeAction = ImeAction.Next,
                    isError = !uiState.inputEmail.isValid,
                    supportingText = if (!uiState.inputEmail.isValid) {
                        { Text(text = uiState.inputEmail.message) }
                    } else null
                )
            }
            AGInputLayout(
                modifier = Modifier.fillMaxWidth(),
                label = "Password",
                isRequired = true
            ) {
                AGInputPassword(
                    modifier = Modifier.fillMaxWidth(),
                    value = uiState.password,
                    onValueChange = onPasswordChange,
                    placeholder = "Masukkan password",
                    imeAction = ImeAction.Done,
                    isError = !uiState.inputPassword.isValid,
                    supportingText = if (!uiState.inputPassword.isValid) {
                        { Text(text = uiState.inputPassword.message) }
                    } else null
                )
            }
        }
        Spacer(modifier = Modifier.padding(bottom = 8.dp))
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 18.dp)
        ) {
            Text(
                text = "Lupa Password?",
                modifier = Modifier
                    .align(Alignment.End)
                    .clickable { navigateToForgotPassword() },
                color = Green100,
                fontWeight = FontWeight.Medium,
                fontSize = 14.sp
            )
            Spacer(modifier = Modifier.padding(bottom = 10.dp))
            AGButton(
                onClick = {
                    onSignInClick()
                },
                modifier = Modifier.fillMaxWidth(),
                contentPadding = PaddingValues(horizontal = 40.dp, vertical = 20.dp)
            ) {
                Text(text = "Login", fontSize = 16.sp, fontWeight = FontWeight.Bold)
            }
            Spacer(modifier = Modifier.padding(bottom = 10.dp))
            AGNotHaveAccount(navigateToSignup = navigateToSignup)
        }
    }
}

@Composable
private fun SignInBanner() {
    Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.TopCenter) {
        Image(
            modifier = Modifier
                .fillMaxWidth()
                .zIndex(1f),
            painter = painterResource(id = R.drawable.login_layer1_bg),
            contentDescription = null
        )
        Image(
            modifier = Modifier
                .fillMaxWidth()
                .zIndex(2f),
            painter = painterResource(id = R.drawable.login_layer2_bg),
            contentDescription = null
        )
        Image(
            modifier = Modifier
                .width(288.dp)
                .zIndex(3f)
                .align(Alignment.Center),
            painter = painterResource(id = R.drawable.splash_banner),
            contentDescription = stringResource(
                id = R.string.app_name
            ),
        )
    }
}