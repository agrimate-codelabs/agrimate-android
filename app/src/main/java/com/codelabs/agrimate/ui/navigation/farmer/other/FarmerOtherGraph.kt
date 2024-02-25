package com.codelabs.agrimate.ui.navigation.farmer.other

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.codelabs.agrimate.screens.farmer.capital.CapitalScreen
import com.codelabs.agrimate.screens.farmer.chat.UserChatScreen
import com.codelabs.agrimate.screens.farmer.discussion.DiscussionScreen
import com.codelabs.agrimate.screens.farmer.farmercalculator.FarmerCalculatorScreen
import com.codelabs.agrimate.screens.farmer.helpharvest.HelpHarvestScreen
import com.codelabs.agrimate.screens.farmer.helpplan.HelpPlanScreen
import com.codelabs.agrimate.screens.farmer.news.NewsScreen
import com.codelabs.agrimate.screens.farmer.newsdetail.NewsDetailScreen
import com.codelabs.agrimate.screens.farmer.notification.NotificationScreen
import com.codelabs.agrimate.screens.farmer.planthealth.CheckDiseaseScreen
import com.codelabs.agrimate.screens.farmer.planthealth.DiseaseDetailScreen
import com.codelabs.agrimate.screens.farmer.planthealth.PlantHealthScreen
import com.codelabs.agrimate.screens.farmer.planthealth.SpreadOfDiseaseScreen
import com.codelabs.agrimate.screens.farmer.plantrecommendation.PlantRecommendationScreen
import com.codelabs.agrimate.screens.farmer.weather_forecast.WeatherForecastScreen
import com.codelabs.agrimate.ui.navigation.AGRoute
import com.codelabs.agrimate.ui.navigation.DestinationsArg

fun NavGraphBuilder.farmerOtherGraph(navController: NavController) {
    composable(AGRoute.Farmer.Main.Home.Explorer.HelpPlant.route) {
        HelpPlanScreen(navController = navController)
    }
    composable(AGRoute.Farmer.Main.Home.Explorer.HelpHarvest.route) {
        HelpHarvestScreen(navController = navController)
    }
    composable(AGRoute.Farmer.Main.Home.Explorer.Capital.route) {
        CapitalScreen(navController = navController)
    }
    composable(AGRoute.Farmer.Main.Home.Explorer.Discussion.route) {
        DiscussionScreen(navController = navController)
    }
    /**
     * Help Plant
     * - Plant Recommendation
     * - Farmer Calculator
     * - Plant Health
     */
    /**
     * Help Plant - Plant Recommendation
     * - From Input
     * - From My Farm
     */
    composable(AGRoute.Farmer.Main.Home.Explorer.HelpPlant.PlantRecommendation.route) {
        PlantRecommendationScreen(navController = navController)
    }
    composable(AGRoute.Farmer.Main.Home.Explorer.HelpPlant.PlantRecommendation.FromInput.route) {
        // TODO
    }
    composable(AGRoute.Farmer.Main.Home.Explorer.HelpPlant.PlantRecommendation.FromInput.route) {
        // TODO
    }
    /**
     * Help Plant - Calculator
     * There are 6 farmer calculator
     */
    composable(AGRoute.Farmer.Main.Home.Explorer.HelpPlant.Calculator.route) {
        FarmerCalculatorScreen(navController = navController)
    }
    composable(AGRoute.Farmer.Main.Home.Explorer.HelpPlant.Calculator.Fertilizer.route) {

    }
    composable(AGRoute.Farmer.Main.Home.Explorer.HelpPlant.Calculator.Pesticide.route) {

    }
    composable(AGRoute.Farmer.Main.Home.Explorer.HelpPlant.Calculator.PlantPopulation.route) {

    }
    composable(AGRoute.Farmer.Main.Home.Explorer.HelpPlant.Calculator.Seeds.route) {

    }
    composable(AGRoute.Farmer.Main.Home.Explorer.HelpPlant.Calculator.Water.route) {

    }
    composable(AGRoute.Farmer.Main.Home.Explorer.HelpPlant.Calculator.Finance.route) {

    }
    /**
     * Help Plant - Plant Health
     * - Spread of Disease
     * - Check Plant Disease
     */
    composable(AGRoute.Farmer.Main.Home.Explorer.HelpPlant.PlantHealth.route) {
        PlantHealthScreen(navController = navController)
    }
    composable(AGRoute.Farmer.Main.Home.Explorer.HelpPlant.PlantHealth.SpreadOfDisease.route) {
        SpreadOfDiseaseScreen(navController = navController)
    }
    composable(AGRoute.Farmer.Main.Home.Explorer.HelpPlant.PlantHealth.CheckDisease.route) {
        CheckDiseaseScreen(navController = navController)
    }
    composable(
        AGRoute.Farmer.Main.Home.Explorer.HelpPlant.PlantHealth.DiseaseDetail.route,
        arguments = listOf(navArgument(DestinationsArg.PLANT_DISEASE_ID_ARG) {
            type = NavType.StringType
        })
    ) {
        DiseaseDetailScreen(navController = navController)
    }
    // Help Harvest
    composable(AGRoute.Farmer.Main.Home.Explorer.HelpHarvest.MarketPrice.route) {
        // TODO
    }
    composable(AGRoute.Farmer.Main.Home.Explorer.HelpHarvest.Collector.route) {
        // TODO
    }

    composable(AGRoute.Farmer.Main.News.route) {
        NewsScreen(navController = navController)
    }
    composable(
        AGRoute.Farmer.Main.News.Detail.route,
        arguments = listOf(navArgument(DestinationsArg.NEWS_URL_ARG) {
            type = NavType.StringType
        })
    ) {
        NewsDetailScreen(navController = navController)
    }
    composable(AGRoute.Farmer.Main.Notification.route) {
        NotificationScreen(navController = navController)
    }
    composable(AGRoute.Farmer.Main.WeatherForecast.route) {
        WeatherForecastScreen(navController = navController)
    }
    /**
     * Chat User
     */
    composable(AGRoute.Farmer.Main.Chat.User.route) {
        UserChatScreen(navController = navController)
    }
}