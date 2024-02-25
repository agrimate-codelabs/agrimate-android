package com.codelabs.agrimate.screens.farmer.helpplan

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.codelabs.agrimate.R
import com.codelabs.agrimate.ui.components.AGFeatureCard
import com.codelabs.agrimate.ui.components.AGTopAppBar
import com.codelabs.agrimate.ui.components.AGTopAppBarDefaultNavigationIcon
import com.codelabs.agrimate.ui.components.AGTopAppBarDefaultTitle
import com.codelabs.agrimate.ui.components.model.FeatureCardModel
import com.codelabs.agrimate.ui.navigation.AGRoute

private val helpPlantMenu = listOf(
    FeatureCardModel(
        icon = R.drawable.ag_plan_recomendation_icon,
        title = "Rekomendasi Tanam",
        description = "Cek jenis tanaman yang cocok pada lahan Anda",
        route = AGRoute.Farmer.Main.Home.Explorer.HelpPlant.PlantRecommendation.route
    ),
    FeatureCardModel(
        icon = R.drawable.ag_farmer_calculator_icon,
        title = "Kalkulator Tani",
        description = "Hitung kebutuhan pestisida air, pupuk, dll",
        route = AGRoute.Farmer.Main.Home.Explorer.HelpPlant.Calculator.route
    ),
    FeatureCardModel(
        icon = R.drawable.ag_check_plant_health_icon,
        title = "Kesehatan Tanaman",
        description = "Diagnosa dan antisipasi penyakit tanaman",
        route = AGRoute.Farmer.Main.Home.Explorer.HelpPlant.PlantHealth.route
    )
)

@Composable
fun HelpPlanScreen(modifier: Modifier = Modifier, navController: NavController) {
    Scaffold(
        modifier = modifier,
        topBar = { TopAppBar(onNavigationClick = { navController.popBackStack() }) }) { paddingValues ->
        LazyVerticalGrid(
            modifier = Modifier
                .fillMaxWidth()
                .padding(paddingValues),
            columns = GridCells.Adaptive(minSize = 160.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            horizontalArrangement = Arrangement.spacedBy(18.dp),
            contentPadding = PaddingValues(18.dp)
        ) {
            items(helpPlantMenu) {
                AGFeatureCard(
                    modifier = Modifier.fillMaxWidth(),
                    onClick = { navController.navigate(it.route) },
                    icon = it.icon,
                    title = it.title,
                    description = it.description
                )
            }
        }
    }
}

@Composable
fun TopAppBar(onNavigationClick: () -> Unit) {
    AGTopAppBar(navigationIcon = { AGTopAppBarDefaultNavigationIcon(onClick = onNavigationClick) }) {
        AGTopAppBarDefaultTitle(text = "Bantu Tanam")
    }
}