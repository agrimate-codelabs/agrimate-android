package com.codelabs.agrimate.ui.navigation.farmer.main

import androidx.compose.animation.EnterTransition
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.codelabs.agrimate.screens.farmer.chat.ChatScreen
import com.codelabs.agrimate.screens.farmer.home.HomeScreen
import com.codelabs.agrimate.screens.farmer.land.LandScreen
import com.codelabs.agrimate.screens.farmer.profile.ProfileScreen
import com.codelabs.agrimate.ui.navigation.AGRoute

fun NavGraphBuilder.farmerMainGraph(navController: NavHostController) {
    composable(
        AGRoute.Farmer.Main.Home.route,
        enterTransition = { EnterTransition.None }) {
        HomeScreen(navController = navController)
    }

    composable(
        AGRoute.Farmer.Main.Land.route,
        enterTransition = { EnterTransition.None }) {
        LandScreen(navController = navController)
    }

    composable(
        AGRoute.Farmer.Main.Profile.route,
        enterTransition = { EnterTransition.None }) {
        ProfileScreen(navController = navController)
    }
    composable(
        AGRoute.Farmer.Main.Chat.route,
        enterTransition = { EnterTransition.None }) {
        ChatScreen(navController = navController)
    }
}