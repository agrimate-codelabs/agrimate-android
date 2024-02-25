package com.codelabs.agrimate.ui.navigation.shared

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.codelabs.agrimate.screens.shared.getstarted.GetStartedScreen
import com.codelabs.agrimate.screens.shared.splash.SplashScreen
import com.codelabs.agrimate.ui.navigation.AGRoute

fun NavGraphBuilder.onBoardingGraph(navController: NavController) {
    navigation(
        startDestination = AGRoute.OnBoarding.Splash.route,
        route = AGRoute.OnBoarding.route
    ) {
        composable(AGRoute.OnBoarding.Splash.route) {
            SplashScreen(navController = navController)
        }
        composable(AGRoute.OnBoarding.GetStarted.route) {
            GetStartedScreen(navController = navController)
        }
    }
}