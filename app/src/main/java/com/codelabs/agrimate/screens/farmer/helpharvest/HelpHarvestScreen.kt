package com.codelabs.agrimate.screens.farmer.helpharvest

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

private val helpHarvestMenu = listOf(
    FeatureCardModel(
        icon = R.drawable.ag_pie_chart_icon,
        title = "Harga Pasar",
        description = "Cek kondisi harga pasar terkini sebagai referensi",
        route = AGRoute.Farmer.Main.Home.Explorer.HelpHarvest.MarketPrice.route
    ),
    FeatureCardModel(
        icon = R.drawable.ag_counselor_icon,
        title = "Pengepul",
        description = "Jual dan negosiasi harga komoditas",
        route = AGRoute.Farmer.Main.Home.Explorer.HelpHarvest.Collector.route
    ),
)

@Composable
fun HelpHarvestScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(onNavigationClick = {
                navController.popBackStack()
            })
        }) { paddingValues ->
        LazyVerticalGrid(
            modifier = Modifier
                .fillMaxWidth()
                .padding(paddingValues),
            columns = GridCells.Adaptive(minSize = 160.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            horizontalArrangement = Arrangement.spacedBy(18.dp),
            contentPadding = PaddingValues(18.dp)
        ) {
            items(helpHarvestMenu) {
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
private fun TopAppBar(onNavigationClick: () -> Unit) {
    AGTopAppBar(navigationIcon = { AGTopAppBarDefaultNavigationIcon(onClick = onNavigationClick) }) {
        AGTopAppBarDefaultTitle(text = "Bantu Panen")
    }
}