package com.codelabs.agrimate.screens.farmer.planthealth

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.codelabs.agrimate.ui.components.AGButton
import com.codelabs.agrimate.ui.components.AGTopAppBar
import com.codelabs.agrimate.ui.components.AGTopAppBarDefaultNavigationIcon
import com.codelabs.agrimate.ui.components.AGTopAppBarDefaultTitle
import com.codelabs.agrimate.ui.navigation.AGRoute
import com.codelabs.agrimate.ui.theme.GreyScale200
import com.codelabs.agrimate.ui.theme.GreyScale500

@Composable
fun PlantHealthScreen(modifier: Modifier = Modifier, navController: NavController) {
    Scaffold(modifier = modifier,
        contentWindowInsets = WindowInsets(bottom = 0.dp),
        topBar = { TopAppBar(onNavigationClick = { navController.popBackStack() }) }) { paddingValues ->
        PlantHealthContent(modifier = Modifier
            .padding(paddingValues)
            .verticalScroll(
                rememberScrollState()
            ),
            navigateToCheckPlantDisease = { navController.navigate(AGRoute.Farmer.Main.Home.Explorer.HelpPlant.PlantHealth.CheckDisease.route) },
            navigateToSpreadOfDisease = { navController.navigate(AGRoute.Farmer.Main.Home.Explorer.HelpPlant.PlantHealth.SpreadOfDisease.route) })
    }
}

@Composable
fun PlantHealthContent(
    modifier: Modifier = Modifier,
    navigateToSpreadOfDisease: () -> Unit,
    navigateToCheckPlantDisease: () -> Unit,
) {
    Box(modifier) {
        Column(
            modifier = Modifier.padding(horizontal = 18.dp, vertical = 14.dp),
            verticalArrangement = Arrangement.spacedBy(21.dp)
        ) {
            PlantHealthContentCard(title = "Lihat penyebaran penyakit",
                description = "lihat penyebaran penyakit dan hama di lokasi Anda",
                buttonLabel = "Buka Map",
                buttonAction = { navigateToSpreadOfDisease() })
            PlantHealthContentCard(title = "Cek penyakit tanaman Anda",
                description = "Cek penyakit di lahan Anda sekarang",
                buttonLabel = "Ambil Gambar",
                buttonAction = { navigateToCheckPlantDisease() })
        }
    }
}

@Composable
private fun PlantHealthContentCard(
    modifier: Modifier = Modifier,
    title: String,
    description: String,
    buttonAction: () -> Unit,
    buttonLabel: String,
) {
    Surface(
        modifier = modifier,
        color = Color.White,
        tonalElevation = 0.dp,
        shadowElevation = 1.dp,
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(Modifier.padding(12.dp)) {
            Text(
                text = title, style = TextStyle(
                    fontSize = 16.sp,
                    lineHeight = 27.2.sp,
                    fontWeight = FontWeight.Bold,
                    color = GreyScale200,
                )
            )
            Text(
                text = description, style = TextStyle(
                    fontSize = 14.sp,
                    lineHeight = 27.3.sp,
                    fontWeight = FontWeight.Medium,
                    color = GreyScale500,
                )
            )
            Spacer(Modifier.padding(bottom = 30.dp))
            AGButton(modifier = Modifier.fillMaxWidth(), onClick = buttonAction) {
                Text(text = buttonLabel)
            }
        }
    }
}

@Composable
private fun TopAppBar(modifier: Modifier = Modifier, onNavigationClick: () -> Unit) {
    AGTopAppBar(modifier = modifier,
        navigationIcon = { AGTopAppBarDefaultNavigationIcon(onClick = onNavigationClick) }) {
        AGTopAppBarDefaultTitle(text = "Kesehatan Tanaman")
    }
}