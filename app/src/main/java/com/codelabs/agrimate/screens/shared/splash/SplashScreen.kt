package com.codelabs.agrimate.screens.shared.splash

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.codelabs.agrimate.R
import com.codelabs.agrimate.ui.navigation.AGRoute
import com.codelabs.agrimate.ui.theme.Green500
import com.codelabs.core.utils.Resource

@Composable
fun SplashScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    viewModel: SplashViewModel = hiltViewModel()
) {
    val isAuthorized = viewModel.isAuthorized.collectAsStateWithLifecycle()

    val navigateToHome = {
        navController.popBackStack()
        navController.navigate(AGRoute.Farmer.route)
    }

    val navigateToOnBoarding = {
        navController.popBackStack()
        navController.navigate(AGRoute.OnBoarding.GetStarted.route)
    }

    LaunchedEffect(Unit) {
        viewModel.authMe()
    }

    LaunchedEffect(isAuthorized.value) {
        when (isAuthorized.value) {
            is Resource.Success -> {
                navigateToHome()
            }

            is Resource.Loading -> {
                // do nothing
            }

            is Resource.Error -> {
                navigateToOnBoarding()
            }
        }
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Green500),
        contentAlignment = Alignment.Center
    ) {
        Image(
            modifier = Modifier.width(288.dp),
            painter = painterResource(id = R.drawable.splash_banner),
            contentDescription = stringResource(
                id = R.string.app_name
            )
        )
    }
}