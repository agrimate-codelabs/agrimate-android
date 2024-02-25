package com.codelabs.agrimate.screens.farmer.planthealth

import androidx.activity.compose.BackHandler
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Divider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.codelabs.agrimate.ui.components.AGButton
import com.codelabs.agrimate.ui.components.AGInputId
import com.codelabs.agrimate.ui.components.AGInputLayout
import com.codelabs.agrimate.ui.components.AGSelectInputWithSearch
import com.codelabs.agrimate.ui.components.AGTextOverview
import com.codelabs.agrimate.ui.components.AGTopAppBar
import com.codelabs.agrimate.ui.components.AGTopAppBarDefaultNavigationIcon
import com.codelabs.agrimate.ui.components.AGTopAppBarDefaultTitle
import com.codelabs.agrimate.ui.components.maps.AGMaps
import com.codelabs.agrimate.ui.theme.Green100
import com.codelabs.agrimate.ui.theme.GreyScale100
import com.codelabs.agrimate.ui.theme.GreyScale800
import com.codelabs.agrimate.ui.theme.Red500
import com.google.maps.android.compose.CameraPositionState
import com.google.maps.android.compose.rememberCameraPositionState

@Composable
fun SpreadOfDiseaseScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
) {
    var isSearched by remember { mutableStateOf(false) }

    val cameraPositionState = rememberCameraPositionState()
    var columnScrollEnabled by remember {
        mutableStateOf(true)
    }

    LaunchedEffect(cameraPositionState.isMoving) {
        if (!cameraPositionState.isMoving) {
            columnScrollEnabled = true
        }
    }

    BackHandler(isSearched) {
        isSearched = false
    }

    Scaffold(
        modifier = modifier,
        contentWindowInsets = WindowInsets(bottom = 0.dp),
        topBar = { TopAppBar(onNavigationClick = { navController.popBackStack() }) }) { paddingValues ->
        SpreadOfDiseaseContent(
            modifier = Modifier
                .padding(paddingValues)
                .verticalScroll(
                    rememberScrollState(),
                    enabled = columnScrollEnabled
                ),
            isSearched = isSearched,
            onIsSearchedChange = { isSearched = it },
            cameraPositionState = cameraPositionState,
            onMapTouched = {
                columnScrollEnabled = false
            }
        )
    }
}

@Composable
fun SpreadOfDiseaseContent(
    modifier: Modifier = Modifier,
    isSearched: Boolean,
    onIsSearchedChange: (Boolean) -> Unit,
    cameraPositionState: CameraPositionState,
    onMapTouched: () -> Unit,
) {
    val localDensity = LocalDensity.current
    val configuration = LocalConfiguration.current
    var diseaseCounterCardHeight by remember { mutableStateOf(10.dp) }
    val overlapPadding by remember { derivedStateOf { diseaseCounterCardHeight / 2 * -1 } }
    val expandedMapHeight by remember { derivedStateOf { (configuration.screenHeightDp - (40 * (configuration.screenHeightDp) / 100)).dp } }

    Box(modifier) {
        Column(
            modifier = Modifier,
            verticalArrangement = Arrangement.spacedBy(overlapPadding)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .animateContentSize()
                    .height(if (isSearched) expandedMapHeight else 250.dp)
            ) {
                AGMaps(
                    cameraPositionState = cameraPositionState,
                    onMapTouched = onMapTouched,
                    modifier = Modifier.fillMaxHeight()
                ) {

                }
            }
            Column(modifier = Modifier.padding(horizontal = 18.dp)) {
                DiseaseCounterCard(modifier = Modifier.onGloballyPositioned { coordinates ->
                    diseaseCounterCardHeight = with(localDensity) { coordinates.size.height.toDp() }
                })
                Spacer(Modifier.padding(bottom = 24.dp))
                SearchSpreadOfDiseaseContent(
                    isSearched = isSearched,
                    onIsSearchedChange = onIsSearchedChange
                )
                Spacer(Modifier.padding(bottom = 24.dp))
            }
        }
    }
}

@Composable
private fun SearchSpreadOfDiseaseContent(
    modifier: Modifier = Modifier,
    isSearched: Boolean,
    onIsSearchedChange: (Boolean) -> Unit,
) {
    Column(modifier = modifier, verticalArrangement = Arrangement.spacedBy(16.dp)) {
        AGInputLayout(label = "Waktu Tampil") {
            AGSelectInputWithSearch(
                modifier = Modifier.fillMaxWidth(),
                value = "",
                onValueChange = {},
                label = "Waktu Tampil",
                placeholder = "Pilih Waktu Tampil",
                options = listOf()
            )
        }
        if (isSearched) SearchDetail()
        else SearchForm(onIsSearchedChange = onIsSearchedChange)
    }
}

@Composable
private fun SearchDetail() {
    AGInputLayout(label = "Alamat Detail") {
        AGTextOverview(text = "Jl. Gatot Subroto, No.144 RT.10, RW.01, Kec. Bayongbong Kel. Kiaracondong, Bandung, Jawa Barat, 40222")
    }
    AGInputLayout(label = "Alamat Detail") {
        AGTextOverview(text = "Jagung, padi, Kentang, Jahe, Bawang merah")
    }
}

@Composable
private fun SearchForm(onIsSearchedChange: (Boolean) -> Unit) {
    AGInputLayout(label = "Masukan Lokasi") {
        AGInputId(
            modifier = Modifier.fillMaxWidth(),
            value = "",
            onValueChange = {},
            placeholder = "Cari Lokasi Anda"
        )
    }
    AGInputLayout(label = "Tampilkan komoditas") {
        AGSelectInputWithSearch(
            modifier = Modifier.fillMaxWidth(),
            value = "",
            onValueChange = {},
            label = "Komoditas",
            placeholder = "Pilih Komoditas",
            options = listOf()
        )
    }
    AGButton(
        modifier = Modifier.fillMaxWidth(),
        onClick = { onIsSearchedChange(true) },
        contentPadding = PaddingValues(vertical = 20.dp, horizontal = 40.dp)
    ) {
        Text(
            text = "Cek Penyebaran",
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            lineHeight = 27.2.sp
        )
    }
}

@Composable
private fun DiseaseCounterCard(modifier: Modifier = Modifier) {
    Surface(
        modifier = modifier,
        color = Color.White,
        shadowElevation = 1.dp,
        shape = RoundedCornerShape(12.dp)
    ) {
        Row(
            modifier = Modifier
                .height(IntrinsicSize.Min)
                .padding(12.dp)
                .fillMaxWidth()
        ) {
            DiseaseSpreadCounter(
                modifier = Modifier.weight(1f),
                statusColor = Red500,
                label = "Dilaporkan",
                value = "4"
            )
            Divider(
                modifier = Modifier
                    .fillMaxHeight()
                    .width(1.dp),
                thickness = 1.dp,
                color = GreyScale800
            )
            DiseaseSpreadCounter(
                modifier = Modifier.weight(1f),
                statusColor = Green100,
                label = "Diverifikasi",
                value = "2"
            )
        }
    }
}

@Composable
private fun DiseaseSpreadCounter(
    modifier: Modifier = Modifier,
    statusColor: Color,
    label: String,
    value: String,
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(10.dp),
    ) {
        Text(text = value, fontSize = 16.sp, fontWeight = FontWeight.Bold)
        Row(
            modifier = Modifier,
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(100))
                    .background(statusColor)
                    .size(22.dp)
            )
            Text(
                text = label, fontSize = 16.sp, fontWeight = FontWeight.Bold, color = GreyScale100
            )
        }
    }
}

@Composable
private fun TopAppBar(modifier: Modifier = Modifier, onNavigationClick: () -> Unit) {
    AGTopAppBar(
        modifier = modifier,
        navigationIcon = { AGTopAppBarDefaultNavigationIcon(onClick = onNavigationClick) }) {
        AGTopAppBarDefaultTitle(text = "Sebaran Penyakit")
    }
}