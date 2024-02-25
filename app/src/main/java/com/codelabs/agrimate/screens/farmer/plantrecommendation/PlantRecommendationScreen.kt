package com.codelabs.agrimate.screens.farmer.plantrecommendation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
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
fun PlantRecommendationScreen(modifier: Modifier = Modifier, navController: NavController) {
    Scaffold(modifier = modifier, topBar = {
        TopAppBar(onNavigationClick = {
            navController.popBackStack()
        })
    }) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(paddingValues)
                .padding(horizontal = 18.dp, vertical = 14.dp)
                .verticalScroll(
                    rememberScrollState()
                ),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            PlantRecommendationCard(
                title = "Masukkan data secara manual",
                description = "Cari tau rekomendasi berdasarkan analisis korelasi",
                buttonLabel = "Input Data",
                onClick = {
                    navController.navigate(AGRoute.Farmer.Main.Home.Explorer.HelpPlant.PlantRecommendation.FromInput.route)
                })
            PlantRecommendationCard(
                title = "Ambil dari lahan yang Anda miliki",
                description = "Cari tau potensi lahan yang Anda miliki saat ini",
                buttonLabel = "Pilih dari lahan Anda",
                onClick = {
                    navController.navigate(AGRoute.Farmer.Main.Home.Explorer.HelpPlant.PlantRecommendation.FromMyFarm.route)
                })
        }
    }
}

@Composable
private fun TopAppBar(modifier: Modifier = Modifier, onNavigationClick: () -> Unit) {
    AGTopAppBar(
        modifier = modifier,
        navigationIcon = { AGTopAppBarDefaultNavigationIcon(onClick = onNavigationClick) }) {
        AGTopAppBarDefaultTitle(text = "Rekomendasi Tanam")
    }
}

@Composable
private fun PlantRecommendationCard(
    modifier: Modifier = Modifier,
    title: String,
    description: String,
    buttonLabel: String,
    onClick: () -> Unit
) {
    Surface(
        modifier = modifier.shadow(22.dp, spotColor = Color(0x0D000000)),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp)
        ) {
            Text(
                text = title,
                fontWeight = FontWeight.Bold,
                color = GreyScale200,
                fontSize = 16.sp,
                lineHeight = 27.2.sp
            )
            Text(
                text = description,
                fontWeight = FontWeight.Medium,
                color = GreyScale500,
                fontSize = 14.sp,
                lineHeight = 27.3.sp
            )
            Spacer(modifier = Modifier.padding(bottom = 30.dp))
            AGButton(onClick = { onClick() }, modifier = Modifier.fillMaxWidth()) {
                Text(buttonLabel)
            }
        }
    }
}

