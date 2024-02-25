package com.codelabs.agrimate.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.codelabs.agrimate.ui.navigation.farmer.farmerGraph
import com.codelabs.agrimate.ui.navigation.shared.authGraph
import com.codelabs.agrimate.ui.navigation.shared.onBoardingGraph

@Composable
fun AgrimateNavGraph(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    startDestination: String = AGRoute.OnBoarding.route,
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier,
    ) {
        onBoardingGraph(navController)
        authGraph(navController)
        farmerGraph(navController)
    }
}