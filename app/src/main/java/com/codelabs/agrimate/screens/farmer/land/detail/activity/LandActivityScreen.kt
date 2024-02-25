package com.codelabs.agrimate.screens.farmer.land.detail.activity

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.codelabs.agrimate.ui.components.AGInputImage
import com.codelabs.agrimate.ui.components.AGInputLayout
import com.codelabs.agrimate.ui.components.AGTextOverview
import com.codelabs.agrimate.ui.components.AGTopAppBar
import com.codelabs.agrimate.ui.components.AGTopAppBarDefaultNavigationIcon
import com.codelabs.agrimate.ui.components.AGTopAppBarDefaultTitle

@Composable
fun LandActivityScreen(modifier: Modifier = Modifier, navController: NavController) {
    Scaffold(
        modifier = modifier,
        contentWindowInsets = WindowInsets(bottom = 0.dp),
        topBar = { TopAppBar(onNavigationClick = { navController.popBackStack() }) }) { paddingValues ->
        LandActivityContent(
            modifier = Modifier
                .padding(paddingValues)
                .verticalScroll(
                    rememberScrollState()
                )
        )
    }
}

@Composable
private fun LandActivityContent(modifier: Modifier = Modifier) {
    Box(modifier = modifier) {
        Column(
            modifier = Modifier.padding(horizontal = 18.dp, vertical = 24.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            AGInputLayout(label = "Aktivitas") {
                AGTextOverview(modifier = Modifier.fillMaxWidth(), text = "Pemberian Pupuk")
            }
            AGInputLayout(label = "Jumlah Pupuk") {
                AGTextOverview(modifier = Modifier.fillMaxWidth(), text = "5 Hektare")
            }
            AGInputLayout(label = "Tanggal Pembelian Pupuk") {
                AGTextOverview(modifier = Modifier.fillMaxWidth(), text = "21 April 2023")
            }
            AGInputLayout(label = "Gambar Aktivitas") {
                AGInputImage(onClick = {})
            }
        }
    }
}

@Composable
private fun TopAppBar(modifier: Modifier = Modifier, onNavigationClick: () -> Unit) {
    AGTopAppBar(
        modifier = modifier,
        navigationIcon = { AGTopAppBarDefaultNavigationIcon(onClick = onNavigationClick) }) {
        AGTopAppBarDefaultTitle(text = "Detail Aktivitas")
    }
}


