package com.codelabs.agrimate.screens.farmer.land.detail

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.codelabs.agrimate.ui.components.AGFilterButton
import com.codelabs.agrimate.ui.components.AGLandDetailCard
import com.codelabs.agrimate.ui.components.AGLandDetailFilterSheet
import com.codelabs.agrimate.ui.components.AGTab
import com.codelabs.agrimate.ui.components.AGTabBar
import com.codelabs.agrimate.ui.components.AGTopAppBar
import com.codelabs.agrimate.ui.components.AGTopAppBarDefaultNavigationIcon
import com.codelabs.agrimate.ui.components.AGTopAppBarDefaultTitle
import com.codelabs.agrimate.ui.components.shimmer.shimmerBrush
import com.codelabs.agrimate.ui.navigation.AGRoute
import com.codelabs.agrimate.ui.navigation.DestinationsArg
import com.codelabs.agrimate.ui.theme.Green100
import com.codelabs.agrimate.ui.theme.GreyScale400
import com.codelabs.agrimate.ui.theme.GreyScale500
import com.codelabs.agrimate.ui.theme.GreyScale700
import com.codelabs.agrimate.utils.FormatUtils
import com.codelabs.core.domain.model.PlantNutritionModel
import com.codelabs.core.utils.Resource
import kotlinx.coroutines.launch

private val landDetailContentTab = listOf("Aktivitas Lahan", "Nutrisi Tanaman")

private enum class EnumLandDetailContent {
    LAND_ACTIVITY, PLANT_NUTRITION
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LandDetailScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    viewModel: LandDetailViewModel = hiltViewModel()
) {
    var openFilterSheet by rememberSaveable { mutableStateOf(false) }
    val bottomSheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val coroutineScope = rememberCoroutineScope()

    val landDetailContentValues = EnumLandDetailContent.values()
    var selectedTabContentValue by rememberSaveable { mutableStateOf(EnumLandDetailContent.LAND_ACTIVITY) }

    val plantNutritionData by viewModel.plantNutritionData.observeAsState()
    val landDetail by viewModel.landDetail.observeAsState()

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    DisposableEffect(Unit) {
        viewModel.getListOfPlanting()
        viewModel.getRemainingLandArea()
        onDispose { }
    }

    val closeFilterSheet = {
        coroutineScope.launch { bottomSheetState.hide() }.invokeOnCompletion {
            if (!bottomSheetState.isVisible) {
                openFilterSheet = false
            }
        }
    }

    Scaffold(
        modifier = modifier,
        contentWindowInsets = WindowInsets(bottom = 0.dp),
        topBar = {
            TopAppBar(
                onNavigationClick = { navController.popBackStack() },
                title = landDetail?.data?.name ?: "Lahan",
            )
        },
        floatingActionButton = {
            if (selectedTabContentValue == EnumLandDetailContent.LAND_ACTIVITY) {
                AddActivityButton(onClick = {
                    navController.navigate(
                        AGRoute.Farmer.Main.Land.Detail.Activity.Add.route.replace(
                            "{${DestinationsArg.LAND_ID_ARG}}",
                            viewModel.landId
                        )
                    )
                })
            }
        },

        ) { paddingValues ->
        LandDetailContent(
            modifier = Modifier.padding(paddingValues),
            onFilterClick = { openFilterSheet = true },
            onLandCardClick = {
                navController.navigate(
                    AGRoute.Farmer.Main.Land.Detail.Activity.Detail.route.replace(
                        "{${DestinationsArg.LAND_ACTIVITY_ID_ARG}}",
                        it
                    ).replace(
                        "{${DestinationsArg.LAND_ID_ARG}}",
                        viewModel.landId
                    )
                )
            },
            tabContentValues = landDetailContentValues,
            selectedTabContentValue = selectedTabContentValue,
            onTabContentClick = { selectedTab ->
                selectedTabContentValue = selectedTab
            },
            plantNutritionData = plantNutritionData,
            uiState = uiState,
        )
    }

    if (openFilterSheet) {
        ModalBottomSheet(
            onDismissRequest = { openFilterSheet = false },
            sheetState = bottomSheetState,
            tonalElevation = 0.dp,
            windowInsets = WindowInsets(bottom = 0),
            containerColor = Color.White
        ) {
            AGLandDetailFilterSheet(onClose = { closeFilterSheet() })
        }
    }
}

@Composable
private fun LandDetailContent(
    modifier: Modifier = Modifier,
    onFilterClick: () -> Unit,
    onLandCardClick: (String) -> Unit,
    tabContentValues: Array<EnumLandDetailContent>,
    selectedTabContentValue: EnumLandDetailContent,
    onTabContentClick: (v: EnumLandDetailContent) -> Unit,
    plantNutritionData: PlantNutritionModel?,
    uiState: LandDetailUiState
) {
    Box(modifier = modifier) {
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            contentPadding = PaddingValues(horizontal = 18.dp, vertical = 19.dp)
        ) {
            item {
                Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                    when (uiState.remainingLandArea) {
                        is Resource.Success -> {
                            HeadContent(
                                totalArea = "${
                                    uiState.remainingLandArea.data?.totalArea?.let {
                                        FormatUtils.formatDecimal(
                                            it
                                        )
                                    } ?: 0
                                }",
                                totalAreaUsed = "${
                                    uiState.remainingLandArea.data?.usedArea?.let {
                                        FormatUtils.formatDecimal(
                                            it
                                        )
                                    } ?: 0
                                }"
                            )
                        }

                        else -> {
                            Box(
                                modifier = Modifier
                                    .background(shimmerBrush(), RoundedCornerShape(8.dp))
                                    .fillMaxWidth()
                                    .height(80.dp)
                            )
                        }
                    }
                    AGTabBar {
                        AGTab(
                            titles = landDetailContentTab,
                            enumValues = tabContentValues,
                            tabSelected = selectedTabContentValue,
                            onTabSelected = onTabContentClick
                        )
                    }
                    when (selectedTabContentValue) {
                        EnumLandDetailContent.LAND_ACTIVITY -> {
                            AGFilterButton(onClick = onFilterClick)
                        }

                        EnumLandDetailContent.PLANT_NUTRITION -> {
                            PlantNutritionContent(plantNutritionData = plantNutritionData)
                        }
                    }
                }
            }
            if (selectedTabContentValue == EnumLandDetailContent.LAND_ACTIVITY) {
                when (uiState.listOfPlatingItem) {
                    is Resource.Success -> {
                        if (uiState.listOfPlatingItem.data.orEmpty().isEmpty()) {
                            item {
                                Box(
                                    modifier = Modifier.padding(vertical = 100.dp),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(
                                        text = "Belum ada lahan yang ditambahkan",
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(18.dp),
                                        textAlign = TextAlign.Center,
                                        fontWeight = FontWeight.Bold,
                                        color = GreyScale500
                                    )
                                }
                            }
                        }
                        items(uiState.listOfPlatingItem.data.orEmpty(), key = { it.id }) {
                            AGLandDetailCard(
                                onClick = { onLandCardClick(it.id) },
                                status = it.status,
                                plantingDate = it.plantingDate,
                                harvestingDate = it.harvestingDate,
                                plantingSize = it.plantingSize,
                                commodity = it.commodity,
                                lastActivity = it.lastActivity
                            )
                        }
                    }

                    else -> {
                        items(3) {
                            Box(
                                modifier = Modifier
                                    .background(shimmerBrush(), RoundedCornerShape(8.dp))
                                    .fillMaxWidth()
                                    .height(160.dp)
                            )
                        }
                    }
                }
            }

        }
    }
}

@Composable
private fun TopAppBar(
    modifier: Modifier = Modifier,
    onNavigationClick: () -> Unit,
    title: String = "",
) {
    AGTopAppBar(
        modifier = modifier,
        navigationIcon = { AGTopAppBarDefaultNavigationIcon(onClick = onNavigationClick) }) {
        AGTopAppBarDefaultTitle(
            text = title,
            modifier = Modifier
                .defaultMinSize(minWidth = 100.dp)
        )
    }
}

@Composable
private fun HeadContent(modifier: Modifier = Modifier, totalArea: String, totalAreaUsed: String) {
    val textHighlightStyle = TextStyle(
        fontSize = 24.sp,
        lineHeight = 42.12.sp,
        fontWeight = FontWeight.Bold,
        textAlign = TextAlign.Center,
    )
    val textLabelStyle = TextStyle(
        fontSize = 11.sp,
        lineHeight = 19.3.sp,
        fontWeight = FontWeight.Medium,
        textAlign = TextAlign.Center,
    )
    Surface(
        modifier = modifier,
        shape = RoundedCornerShape(8.dp),
        border = BorderStroke(width = 1.dp, color = GreyScale700)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp, vertical = 16.dp)
                .animateContentSize(),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Column(
                modifier = Modifier.weight(1f),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text("$totalArea Hektare", style = textHighlightStyle, color = Green100)
                Text("Total luas", style = textLabelStyle, color = GreyScale400)
            }
            Divider(
                modifier = Modifier
                    .width(1.dp)
                    .height(55.dp)
            )
            Column(
                modifier = Modifier.weight(1f),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text("$totalAreaUsed Hektare", style = textHighlightStyle, color = GreyScale400)
                Text("Luas terpakai", style = textLabelStyle, color = GreyScale400)
            }

        }
    }
}

@Composable
private fun AddActivityButton(modifier: Modifier = Modifier, onClick: () -> Unit) {
    Surface(
        modifier = modifier, shape = RoundedCornerShape(100),
        color = Color.White,
        onClick = onClick,
        shadowElevation = 4.dp
    ) {
        Box(Modifier.padding(16.dp)) {
            Icon(imageVector = Icons.Default.Add, contentDescription = "add", tint = Green100)
        }
    }
}