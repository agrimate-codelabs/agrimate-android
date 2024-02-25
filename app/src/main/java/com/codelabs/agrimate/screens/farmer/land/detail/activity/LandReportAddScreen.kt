package com.codelabs.agrimate.screens.farmer.land.detail.activity

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.codelabs.agrimate.ui.components.AGButton
import com.codelabs.agrimate.ui.components.AGInputId
import com.codelabs.agrimate.ui.components.AGInputImage
import com.codelabs.agrimate.ui.components.AGInputLayout
import com.codelabs.agrimate.ui.components.AGSelectInputWithSearch
import com.codelabs.agrimate.ui.components.AGTopAppBar
import com.codelabs.agrimate.ui.components.AGTopAppBarDefaultNavigationIcon
import com.codelabs.agrimate.ui.components.AGTopAppBarDefaultTitle
import com.codelabs.agrimate.ui.theme.Green100

@Composable
fun LandReportAddScreen(modifier: Modifier = Modifier, navController: NavController) {
    Scaffold(
        modifier = modifier,
        contentWindowInsets = WindowInsets(bottom = 0.dp),
        topBar = { TopAppBar(onNavigationClick = { navController.popBackStack() }) },
        bottomBar = { ActionBottomBar(onClick = {}) }
    ) { paddingValues ->
        LandReportAddContent(
            modifier
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
        )
    }
}

@Composable
fun LandReportAddContent(modifier: Modifier = Modifier) {
    Box(modifier) {
        Column(
            modifier = Modifier.padding(horizontal = 18.dp, vertical = 25.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            AGInputLayout(label = "Jenis Permasalahan") {
                AGSelectInputWithSearch(
                    value = "",
                    onValueChange = {},
                    label = "Jenis Permasalah",
                    placeholder = "Pilih Opsi",
                    options = listOf()
                )
            }
            AGInputLayout(label = "Masukan Keluhan") {
                AGInputId(
                    modifier = Modifier.fillMaxWidth(),
                    value = "",
                    onValueChange = {},
                    placeholder = "Masukan Keluhan"
                )
            }
            AGInputLayout(label = "Masukan bukti gambar") {
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
        AGTopAppBarDefaultTitle(text = "Laporkan Masalah")
    }
}

@Composable
private fun ActionBottomBar(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
) {
    Surface(
        modifier = modifier.windowInsetsPadding(WindowInsets(bottom = 0.dp)),
        color = Color.White,
        shadowElevation = 10.dp
    ) {
        Box(
            Modifier.padding(horizontal = 18.dp, vertical = 16.dp),
        ) {
            AGButton(
                modifier = Modifier.fillMaxWidth(),
                onClick = onClick,
                containerColor = Green100,
                shape = RoundedCornerShape(12.dp),
                contentPadding = PaddingValues(horizontal = 8.dp, vertical = 20.dp)
            ) {
                Text(
                    text = "Buat Laporan",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}