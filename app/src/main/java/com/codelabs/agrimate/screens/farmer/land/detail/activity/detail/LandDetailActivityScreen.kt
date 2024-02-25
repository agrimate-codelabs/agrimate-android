package com.codelabs.agrimate.screens.farmer.land.detail.activity.detail

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.codelabs.agrimate.R
import com.codelabs.agrimate.ui.common.state.UiState
import com.codelabs.agrimate.ui.components.AGButton
import com.codelabs.agrimate.ui.components.AGButtonOutlined
import com.codelabs.agrimate.ui.components.AGFilledCardWithAction
import com.codelabs.agrimate.ui.components.AGInputBox
import com.codelabs.agrimate.ui.components.AGInputDate
import com.codelabs.agrimate.ui.components.AGInputLayout
import com.codelabs.agrimate.ui.components.AGInputWithSuffix
import com.codelabs.agrimate.ui.components.AGLabelChip
import com.codelabs.agrimate.ui.components.AGTab
import com.codelabs.agrimate.ui.components.AGTabBar
import com.codelabs.agrimate.ui.components.AGTextOverview
import com.codelabs.agrimate.ui.components.AGTextWithPrefix
import com.codelabs.agrimate.ui.components.AGTopAppBar
import com.codelabs.agrimate.ui.components.AGTopAppBarDefaultNavigationIcon
import com.codelabs.agrimate.ui.components.AGTopAppBarDefaultTitle
import com.codelabs.agrimate.ui.components.dialog.AGFailedHarvestConfirmDialog
import com.codelabs.agrimate.ui.components.dialog.AGSuccessHarvestConfirmDialog
import com.codelabs.agrimate.ui.components.maps.AGMaps
import com.codelabs.agrimate.ui.components.maps.AGMapsPolygonDefault
import com.codelabs.agrimate.ui.components.shimmer.shimmerBrush
import com.codelabs.agrimate.ui.theme.Green100
import com.codelabs.agrimate.ui.theme.GreyScale500
import com.codelabs.agrimate.ui.theme.Red1
import com.codelabs.agrimate.ui.theme.md_theme_light_error
import com.codelabs.agrimate.utils.DateUtils
import com.codelabs.agrimate.utils.FormatUtils
import com.codelabs.agrimate.utils.UiUtils
import com.codelabs.core.domain.model.PlantingModel
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.maps.android.compose.CameraPositionState
import com.google.maps.android.compose.MarkerComposable
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState

private val mainContentTabTitles = listOf("Detail Aktivitas", "Aktivitas", "Riwayat Laporan")
private val detailActivityTabTitles = listOf("Tanam", "Panen")

private enum class MainContentScreen {
    DETAIL_ACTIVITY, ACTIVITY, REPORT_HISTORY
}

private enum class DetailActivityContentScreen {
    PLAN, HARVEST
}

@Composable
fun LandDetailActivityScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    viewModel: LandDetailActivityViewModel = hiltViewModel()
) {
    var columnScrollEnabled by remember { mutableStateOf(true) }

    val plantingDetailUiState by viewModel.plantingDetailUiState.collectAsStateWithLifecycle()
    val landPolygon by viewModel.mapPolygon.collectAsStateWithLifecycle()

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    var isHarvestButtonClick by remember {
        mutableStateOf(false)
    }

    var failedHarvestConfirmDialogShowed by remember {
        mutableStateOf(false)
    }
    var successHarvestConfirmDialogShowed by remember {
        mutableStateOf(false)
    }

    val cameraPositionState = rememberCameraPositionState()

    var isMapLoaded by remember { mutableStateOf(false) }

    LaunchedEffect(cameraPositionState.isMoving) {
        if (!cameraPositionState.isMoving) {
            columnScrollEnabled = true
        }
    }

    LaunchedEffect(landPolygon, isMapLoaded) {
        if (landPolygon.isNotEmpty() && isMapLoaded) {
            val latLngBounds = LatLngBounds.builder()
            landPolygon.map {
                latLngBounds.include(it)
            }
            cameraPositionState.animate(
                CameraUpdateFactory.newLatLngBounds(
                    latLngBounds.build(), 100
                )
            )
        }
    }

    if (uiState.isLoading) {
        Dialog(onDismissRequest = {}) {
            CircularProgressIndicator()
        }
    }

    if (failedHarvestConfirmDialogShowed) {
        AGFailedHarvestConfirmDialog(onDismissRequest = {
            failedHarvestConfirmDialogShowed = false
        }, onConfirmClick = {
            viewModel.saveFailed()
        })
    }

    if (successHarvestConfirmDialogShowed) {
        AGSuccessHarvestConfirmDialog(onDismissRequest = {
            successHarvestConfirmDialogShowed = false
        }, onConfirmClick = {
            viewModel.saveSuccess()
        })
    }

    LaunchedEffect(uiState.isLoading, uiState.isSaveFailed, uiState.isSaveSuccess) {
        if (!uiState.isLoading && (uiState.isSaveFailed || uiState.isSaveSuccess)) {
            viewModel.getPlantingLandActivityDetail()
            failedHarvestConfirmDialogShowed = false
            successHarvestConfirmDialogShowed = false
            isHarvestButtonClick = false
        }
    }

    Scaffold(
        modifier = modifier.imePadding(),
        contentWindowInsets = WindowInsets(bottom = 0.dp),
        bottomBar = {
            if (isHarvestButtonClick) {
                BottomHarvestActionButton(modifier = Modifier,
                    onCancelClick = { isHarvestButtonClick = false },
                    onSaveClick = {
                        viewModel.resetValidation()
                        viewModel.updateValidation()
                        if (viewModel.isFormValid()) {
                            successHarvestConfirmDialogShowed = true
                        }
                    })
            } else if (plantingDetailUiState is UiState.Success) {
                if ((plantingDetailUiState as UiState.Success<PlantingModel>).data.status == "Belum Panen") {
                    BottomActionButton(modifier = Modifier, onFailedClick = {
                        failedHarvestConfirmDialogShowed = true
                    }, onSuccessClick = {
                        isHarvestButtonClick = true
                    })
                }
            }
        },
        topBar = { TopAppBar(onNavigationClick = { navController.popBackStack() }) }) { paddingValues ->
        LandDetailActivityContent(modifier = Modifier
            .padding(paddingValues)
            .fillMaxSize()
            .verticalScroll(
                rememberScrollState(), enabled = columnScrollEnabled
            ),
            updateColumnScroll = {
                columnScrollEnabled = it
            },
            uiState = uiState,
            plantingDetailUiState = plantingDetailUiState,
            landPolygon = landPolygon,
            isHarvestButtonClick = isHarvestButtonClick,
            onDateHarvestChange = viewModel::updateDateHarvest,
            onAmountHarvestChange = viewModel::updateAmountHarvest,
            cameraPositionState = cameraPositionState,
            onIsMapLoaded = {
                isMapLoaded = true
            })
    }
}

@Composable
fun LandDetailActivityContent(
    modifier: Modifier = Modifier,
    updateColumnScroll: (Boolean) -> Unit,
    uiState: LandDetailActivityUiState,
    plantingDetailUiState: UiState<PlantingModel>,
    landPolygon: List<LatLng>,
    isHarvestButtonClick: Boolean,
    onDateHarvestChange: (String) -> Unit,
    onAmountHarvestChange: (String) -> Unit,
    cameraPositionState: CameraPositionState,
    onIsMapLoaded: (Boolean) -> Unit
) {
    val tabBarValues = MainContentScreen.values()
    val selectedTab = remember { mutableStateOf(MainContentScreen.DETAIL_ACTIVITY) }
    Box(modifier) {
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            Box(Modifier.padding(top = 8.dp, bottom = 10.dp, start = 18.dp, end = 18.dp)) {
                AGTabBar {
                    AGTab(titles = mainContentTabTitles,
                        enumValues = tabBarValues,
                        tabSelected = selectedTab.value,
                        onTabSelected = { newTab ->
                            selectedTab.value = newTab
                        })
                }
            }
            Crossfade(targetState = selectedTab.value, label = "main-content") { targetState ->
                when (targetState) {
                    MainContentScreen.DETAIL_ACTIVITY -> {
                        DetailActivityContent(
                            uiState = uiState,
                            plantingDetailUiState = plantingDetailUiState,
                            landPolygon = landPolygon,
                            onMapTouched = {
                                updateColumnScroll(false)
                            },
                            isHarvestButtonClick = isHarvestButtonClick,
                            onDateHarvestChange = onDateHarvestChange,
                            onAmountHarvestChange = onAmountHarvestChange,
                            cameraPositionState = cameraPositionState,
                            onIsMapLoaded = onIsMapLoaded
                        )
                    }

                    MainContentScreen.ACTIVITY -> {
                        ActivityContent()
                    }

                    MainContentScreen.REPORT_HISTORY -> {
                        ReportHistoryContent()
                    }
                }
            }
        }
    }
}

@Composable
private fun ReportHistoryContent() {
    Column(
        modifier = Modifier.padding(horizontal = 18.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        AGInputBox(text = "Laporkan Masalah", onClick = {}, modifier = Modifier.fillMaxWidth())
        repeat(10) { index ->
            key(index) {
                AGFilledCardWithAction(modifier = Modifier,
                    title = "Bencana Alam",
                    description = "diberikan pada 23 Mei 2023 | 08.00 WIB ",
                    trailing = {
                        AGLabelChip(
                            text = "Dalam Review", compact = true, shape = RoundedCornerShape(8.dp)
                        )
                    },
                    onClick = {})
            }
        }
    }
}

@Composable
private fun ActivityContent() {
    Column(
        modifier = Modifier.padding(horizontal = 18.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        AGInputBox(text = "Tambah Aktifitas", onClick = {}, modifier = Modifier.fillMaxWidth())
        repeat(10) { index ->
            key(index) {
                AGFilledCardWithAction(modifier = Modifier,
                    title = "Pemberian Pupuk",
                    description = "diberikan pada 23 Mei 2023 | 08.00 WIB ",
                    trailing = {
                        IconButton(onClick = {}) {
                            Icon(
                                imageVector = Icons.Default.MoreVert,
                                contentDescription = "aksi",
                                tint = GreyScale500
                            )
                        }
                    },
                    onClick = {})
            }
        }
    }
}

@Composable
private fun DetailActivityContent(
    uiState: LandDetailActivityUiState,
    plantingDetailUiState: UiState<PlantingModel>,
    cameraPositionState: CameraPositionState,
    onIsMapLoaded: (Boolean) -> Unit,
    landPolygon: List<LatLng>,
    onMapTouched: () -> Unit,
    isHarvestButtonClick: Boolean,
    onDateHarvestChange: (String) -> Unit,
    onAmountHarvestChange: (String) -> Unit
) {
    val tabBarValue = DetailActivityContentScreen.values()
    val selectedTab = remember { mutableStateOf(DetailActivityContentScreen.PLAN) }



    when (plantingDetailUiState) {
        is UiState.Error -> {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(vertical = 100.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = plantingDetailUiState.message,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(18.dp),
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Bold,
                    color = GreyScale500
                )
            }
        }

        is UiState.Loading -> {
            Column(modifier = Modifier, verticalArrangement = Arrangement.spacedBy(22.dp)) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(189.dp)
                        .background(shimmerBrush())
                )
                Column(
                    modifier = Modifier.padding(horizontal = 18.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .width(100.dp)
                            .height(24.dp)
                            .background(shimmerBrush(), RoundedCornerShape(100))
                    )
                    repeat(3) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(18.dp)
                                .background(shimmerBrush(), RoundedCornerShape(12.dp))
                        )
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(54.dp)
                                .background(shimmerBrush(), RoundedCornerShape(12.dp))
                        )
                    }
                }
            }
        }

        is UiState.Success -> {
            Column(modifier = Modifier, verticalArrangement = Arrangement.spacedBy(22.dp)) {
                Box(modifier = Modifier.height(189.dp)) {
                    AGMaps(cameraPositionState = cameraPositionState,
                        onMapTouched = onMapTouched,
                        onMapLoaded = { onIsMapLoaded(true) }) {
                        MarkerComposable(
                            state = MarkerState(
                                LatLng(
                                    plantingDetailUiState.data.latitude.toDouble(),
                                    plantingDetailUiState.data.longitude.toDouble()
                                )
                            )
                        ) {
                            Icon(
                                modifier = Modifier.size(34.dp),
                                painter = painterResource(id = R.drawable.map_pin),
                                contentDescription = null,
                                tint = Red1
                            )
                        }
                        AGMapsPolygonDefault(listLatLng = landPolygon)
                    }
                }
                if (!isHarvestButtonClick) {
                    Column(
                        modifier = Modifier.padding(horizontal = 18.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        AGInputLayout(label = "Status Aktivitas Lahan") {
                            AGLabelChip(
                                text = plantingDetailUiState.data.status,
                                color = UiUtils.getStatusColor(plantingDetailUiState.data.status)
                            )
                        }
                        AGInputLayout(label = "Komoditas") {
                            AGTextOverview(
                                modifier = Modifier.fillMaxWidth(),
                                text = plantingDetailUiState.data.commodity
                            )
                        }
                        AGInputLayout(label = "Luas Lahan Tanam") {
                            AGTextOverview(
                                modifier = Modifier.fillMaxWidth(),
                                text = "${plantingDetailUiState.data.plantingSize} Hektare"
                            )
                        }
                        if (plantingDetailUiState.data.status == "Panen") {
                            AGTabBar {
                                AGTab(titles = detailActivityTabTitles,
                                    enumValues = tabBarValue,
                                    tabSelected = selectedTab.value,
                                    onTabSelected = { newTab ->
                                        selectedTab.value = newTab
                                    })
                            }
                        }
                        AnimatedContent(
                            targetState = selectedTab.value,
                            label = "land-activity-content-type",
                        ) { targetState ->
                            when (targetState) {
                                DetailActivityContentScreen.PLAN -> {
                                    LandPlanContent(
                                        date = DateUtils.formatDate(plantingDetailUiState.data.date)
                                            ?: "-",
                                        type = plantingDetailUiState.data.plantingType,
                                        quantity = (plantingDetailUiState.data.plantingQuantity).toString(),
                                        unit = plantingDetailUiState.data.unit,
                                        productionCost = FormatUtils.formatNumber(
                                            plantingDetailUiState.data.productionCost.toString()
                                        )
                                    )
                                }

                                DetailActivityContentScreen.HARVEST -> {
                                    LandHarvestContent(
                                        date = DateUtils.formatDate(
                                            plantingDetailUiState.data.harvesting?.date ?: "-"
                                        ).orEmpty(),
                                        harvestAmount = (plantingDetailUiState.data.harvesting?.amount).toString()
                                    )
                                }
                            }
                        }
                    }
                } else {
                    Column(
                        modifier = Modifier.padding(horizontal = 18.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        AGInputLayout(label = "Tanggal Tanam") {
                            AGTextOverview(
                                modifier = Modifier.fillMaxWidth(),
                                text = DateUtils.formatDate(plantingDetailUiState.data.date) ?: "-"
                            )
                        }
                        AGInputLayout(label = "Tanggal Panen") {
                            AGInputDate(value = if (uiState.dateHarvest.isNotEmpty()) DateUtils.convertDateToLong(
                                uiState.dateHarvest
                            ) else null,
                                onValueChange = {
                                    if (it != null) {
                                        onDateHarvestChange(DateUtils.convertLongToTime(it))
                                    }
                                },
                                isError = !uiState.inputDateHarvest.isValid,
                                supportingText = if (!uiState.inputDateHarvest.isValid) {
                                    { Text(text = uiState.inputDateHarvest.message) }
                                } else null)
                        }
                        AGInputLayout(label = "Jumlah Panen") {
                            AGInputWithSuffix(
                                value = uiState.amountHarvest,
                                onValueChange = onAmountHarvestChange,
                                placeholder = "0",
                                suffix = plantingDetailUiState.data.unit,
                                imeAction = ImeAction.Done,
                                isError = !uiState.inputAmountHarvest.isValid
                            )
                            if (!uiState.inputAmountHarvest.isValid) {
                                Text(
                                    text = uiState.inputAmountHarvest.message,
                                    color = md_theme_light_error,
                                    style = MaterialTheme.typography.bodySmall
                                )
                            }
                        }
                    }
                }

            }
        }
    }
}

@Composable
private fun LandPlanContent(
    type: String, quantity: String, unit: String, date: String, productionCost: String
) {
    Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
        AGInputLayout(label = "Tanggal Tanam") {
            AGTextOverview(modifier = Modifier.fillMaxWidth(), text = date)
        }

        AGInputLayout(label = "Jenis Tanam") {
            AGTextOverview(modifier = Modifier.fillMaxWidth(), text = type)
        }

        AGInputLayout(label = "Jumlah Tanam") {
            AGTextOverview(modifier = Modifier.fillMaxWidth(), text = "$quantity $unit")
        }

        AGInputLayout(label = "Biaya Produksi") {
            AGTextWithPrefix(
                modifier = Modifier.fillMaxWidth(), prefix = "Rp", text = productionCost
            )
        }
    }
}

@Composable
private fun LandHarvestContent(date: String, harvestAmount: String) {
    Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
        AGInputLayout(label = "Tanggal Panen") {
            AGTextOverview(modifier = Modifier.fillMaxWidth(), text = date)
        }

        AGInputLayout(label = "Jumlah Panen") {
            AGTextOverview(modifier = Modifier.fillMaxWidth(), text = "$harvestAmount kg")
        }
    }
}

@Composable
private fun TopAppBar(modifier: Modifier = Modifier, onNavigationClick: () -> Unit) {
    AGTopAppBar(modifier = modifier, navigationIcon = {
        AGTopAppBarDefaultNavigationIcon(onClick = onNavigationClick)
    }) {
        AGTopAppBarDefaultTitle(text = "Aktivitas Lahan")
    }
}

@Composable
private fun BottomActionButton(
    modifier: Modifier, onFailedClick: () -> Unit, onSuccessClick: () -> Unit
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(Color.White)
            .shadow(
                1.dp,
                ambientColor = Color.Black.copy(alpha = 0.05f),
                spotColor = Color.Black.copy(alpha = 0.05f)
            )
            .padding(horizontal = 18.dp, vertical = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        AGButton(
            modifier = Modifier.weight(1f),
            onClick = onFailedClick,
            containerColor = Red1,
            contentPadding = PaddingValues(vertical = 16.5.dp)
        ) {
            Text(text = "Gagal Panen", fontWeight = FontWeight.Bold, fontSize = 16.sp)
        }
        AGButton(
            modifier = Modifier.weight(1f),
            onClick = onSuccessClick,
            containerColor = Green100,
            contentPadding = PaddingValues(vertical = 16.5.dp)
        ) {
            Text(text = "Panen", fontWeight = FontWeight.Bold, fontSize = 16.sp)
        }
    }
}

@Composable
private fun BottomHarvestActionButton(
    modifier: Modifier, onCancelClick: () -> Unit, onSaveClick: () -> Unit
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(Color.White)
            .shadow(
                1.dp,
                ambientColor = Color.Black.copy(alpha = 0.05f),
                spotColor = Color.Black.copy(alpha = 0.05f)
            )
            .padding(horizontal = 18.dp, vertical = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        AGButtonOutlined(
            modifier = Modifier.weight(1f),
            onClick = onCancelClick,
            contentPadding = PaddingValues(vertical = 16.5.dp)
        ) {
            Text(text = "Batal", fontWeight = FontWeight.Bold, fontSize = 16.sp)
        }
        AGButton(
            modifier = Modifier.weight(1f),
            onClick = onSaveClick,
            containerColor = Green100,
            contentPadding = PaddingValues(vertical = 16.5.dp)
        ) {
            Text(text = "Simpan", fontWeight = FontWeight.Bold, fontSize = 16.sp)
        }
    }
}