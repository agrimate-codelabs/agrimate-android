package com.codelabs.agrimate.ui.navigation.farmer

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.navigation
import com.codelabs.agrimate.ui.navigation.AGRoute
import com.codelabs.agrimate.ui.navigation.farmer.land.farmerLandGraph
import com.codelabs.agrimate.ui.navigation.farmer.main.farmerMainGraph
import com.codelabs.agrimate.ui.navigation.farmer.other.farmerOtherGraph

fun NavGraphBuilder.farmerGraph(navController: NavHostController) {
    navigation(
        startDestination = AGRoute.Farmer.Main.Home.route,
        route = AGRoute.Farmer.route
    ) {
        farmerMainGraph(navController)
        farmerOtherGraph(navController)
        farmerLandGraph(navController)
    }
}