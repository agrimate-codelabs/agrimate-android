package com.codelabs.agrimate.screens.farmer.farmercalculator

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

private val farmerCalculatorMenu = listOf(
    FeatureCardModel(
        icon = R.drawable.ag_fertilizer_icon,
        title = "Kebutuhan Pupuk",
        description = "Hitung kebutuhan pupuk",
        route = AGRoute.Farmer.Main.Home.Explorer.HelpPlant.Calculator.Fertilizer.route
    ),
    FeatureCardModel(
        icon = R.drawable.ag_chemical_icon,
        title = "Kebutuhan Pestisida",
        description = "Hitung kebutuhan pestisida",
        route = AGRoute.Farmer.Main.Home.Explorer.HelpPlant.Calculator.Pesticide.route
    ),
    FeatureCardModel(
        icon = R.drawable.ag_plant_icon,
        title = "Populasi Tanam",
        description = "Hitung populasi",
        route = AGRoute.Farmer.Main.Home.Explorer.HelpPlant.Calculator.PlantPopulation.route
    ),
    FeatureCardModel(
        icon = R.drawable.ag_seed_icon,
        title = "Kebutuhan Benih",
        description = "Hitung kebutuhan benih",
        route = AGRoute.Farmer.Main.Home.Explorer.HelpPlant.Calculator.Seeds.route
    ),
    FeatureCardModel(
        icon = R.drawable.ag_water_drops_icon,
        title = "Kebutuhan Air",
        description = "Hitung kebutuhan air",
        route = AGRoute.Farmer.Main.Home.Explorer.HelpPlant.Calculator.Water.route
    ),
    FeatureCardModel(
        icon = R.drawable.ag_finance_icon,
        title = "Keuangan",
        description = "Hitung Pengeluaran",
        route = AGRoute.Farmer.Main.Home.Explorer.HelpPlant.Calculator.Finance.route
    ),
)

@Composable
fun FarmerCalculatorScreen(modifier: Modifier = Modifier, navController: NavController) {
    Scaffold(modifier = modifier,
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
            items(farmerCalculatorMenu) {
                AGFeatureCard(
                    modifier = Modifier.fillMaxWidth(),
                    onClick = { navController.navigate(it.route) },
                    icon = it.icon,
                    title = it.title,
                    description = it.description,
                    iconSpace = 18.dp
                )
            }
        }
    }
}

@Composable
fun TopAppBar(onNavigationClick: () -> Unit) {
    AGTopAppBar(navigationIcon = { AGTopAppBarDefaultNavigationIcon(onClick = onNavigationClick) }) {
        AGTopAppBarDefaultTitle(text = "Kalkulator Tani")
    }
}