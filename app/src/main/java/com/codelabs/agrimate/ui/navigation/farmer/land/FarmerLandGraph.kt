package com.codelabs.agrimate.ui.navigation.farmer.land

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.EaseIn
import androidx.compose.animation.core.EaseOut
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.codelabs.agrimate.screens.farmer.land.add.LandAddScreen
import com.codelabs.agrimate.screens.farmer.land.detail.LandDetailScreen
import com.codelabs.agrimate.screens.farmer.land.detail.activity.add.LandDetailActivityAddScreen
import com.codelabs.agrimate.screens.farmer.land.detail.activity.detail.LandDetailActivityScreen
import com.codelabs.agrimate.screens.farmer.land.edit.LandEditScreen
import com.codelabs.agrimate.ui.navigation.AGRoute
import com.codelabs.agrimate.ui.navigation.DestinationsArg

fun NavGraphBuilder.farmerLandGraph(navController: NavController) {
    composable(AGRoute.Farmer.Main.Land.Add.route,
        enterTransition = {
            fadeIn(
                animationSpec = tween(
                    300, easing = LinearEasing
                )
            ) + slideIntoContainer(
                animationSpec = tween(300, easing = EaseIn),
                towards = AnimatedContentTransitionScope.SlideDirection.Start
            )
        },
        exitTransition = {
            fadeOut(
                animationSpec = tween(
                    300, easing = LinearEasing
                )
            ) + slideOutOfContainer(
                animationSpec = tween(300, easing = EaseOut),
                towards = AnimatedContentTransitionScope.SlideDirection.End
            )
        }
    ) {
        LandAddScreen(navController = navController)
    }
    composable(
        route = AGRoute.Farmer.Main.Land.Edit.route,
        arguments = listOf(navArgument(DestinationsArg.LAND_ID_ARG) {
            type = NavType.StringType
        })
    ) {
        LandEditScreen(navController = navController)
    }
    composable(
        route = AGRoute.Farmer.Main.Land.Detail.route,
        arguments = listOf(navArgument(DestinationsArg.LAND_ID_ARG) {
            type = NavType.StringType
        })
    ) {
        LandDetailScreen(
            navController = navController
        )
    }
    composable(
        route = AGRoute.Farmer.Main.Land.Detail.Activity.Detail.route,
        arguments = listOf(navArgument(DestinationsArg.LAND_ACTIVITY_ID_ARG) {
            type = NavType.StringType
        }, navArgument(DestinationsArg.LAND_ID_ARG) {
            type = NavType.StringType
        })
    ) {
        LandDetailActivityScreen(navController = navController)
    }
    composable(
        route = AGRoute.Farmer.Main.Land.Detail.Activity.Add.route,
        arguments = listOf(navArgument(DestinationsArg.LAND_ID_ARG) {
            type = NavType.StringType
        })
    ) {
        LandDetailActivityAddScreen(navController = navController)
    }
}