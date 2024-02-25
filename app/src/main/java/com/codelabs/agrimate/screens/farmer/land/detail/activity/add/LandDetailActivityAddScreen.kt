package com.codelabs.agrimate.screens.farmer.land.detail.activity.add

import android.Manifest
import android.app.Activity
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.core.app.ActivityCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.codelabs.agrimate.R
import com.codelabs.agrimate.ui.common.impl.PlantCommoditySelectInputImpl
import com.codelabs.agrimate.ui.common.impl.UnitSelectInputImpl
import com.codelabs.agrimate.ui.components.AGButton
import com.codelabs.agrimate.ui.components.AGInputDate
import com.codelabs.agrimate.ui.components.AGInputLayout
import com.codelabs.agrimate.ui.components.AGInputWithPrefix
import com.codelabs.agrimate.ui.components.AGInputWithSelectSuffix
import com.codelabs.agrimate.ui.components.AGInputWithSuffix
import com.codelabs.agrimate.ui.components.AGPermissionDialog
import com.codelabs.agrimate.ui.components.AGSelectInputWithSearch
import com.codelabs.agrimate.ui.components.AGTopAppBar
import com.codelabs.agrimate.ui.components.AGTopAppBarDefaultNavigationIcon
import com.codelabs.agrimate.ui.components.AGTopAppBarDefaultTitle
import com.codelabs.agrimate.ui.components.maps.AGMaps
import com.codelabs.agrimate.ui.components.maps.AGMapsCrosshair
import com.codelabs.agrimate.ui.components.maps.AGMapsMarkerController
import com.codelabs.agrimate.ui.components.maps.AGMapsPolygonDefault
import com.codelabs.agrimate.ui.components.visualtransformation.NumberTransformation
import com.codelabs.agrimate.ui.theme.Red1
import com.codelabs.agrimate.ui.theme.md_theme_light_error
import com.codelabs.agrimate.utils.CoarseLocationPermissionTextProvider
import com.codelabs.agrimate.utils.DateUtils
import com.codelabs.agrimate.utils.PermissionsViewModel
import com.codelabs.core.domain.model.CommodityModel
import com.codelabs.core.utils.Resource
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.maps.android.compose.CameraPositionState
import com.google.maps.android.compose.MarkerComposable
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState
import kotlinx.coroutines.launch

@Composable
fun LandDetailActivityAddScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    viewModel: LandDetailActivityAddViewModel = hiltViewModel(),
    permissionsViewModel: PermissionsViewModel = hiltViewModel()
) {
    val context = LocalContext.current

    val farmPolygon by viewModel.farmPolygon.collectAsStateWithLifecycle()
    val landMarker by viewModel.marker.collectAsStateWithLifecycle()
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val listOfCommodity by viewModel.listOfCommodity.observeAsState()

    val permissionsToRequest by remember {
        mutableStateOf(
            arrayOf(
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION
            )
        )
    }
    var isPermissionGranted by remember { mutableStateOf(false) }

    val requestPermission = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions(),
        onResult = { perms ->
            permissionsToRequest.forEach { permission ->
                permissionsViewModel.onPermissionResult(
                    permission = permission,
                    isGranted = perms[permission] == true
                )
                isPermissionGranted = perms[permission] == true
            }
        })

    val permissionDialogQueue = permissionsViewModel.visiblePermissionDialogQueue

    permissionDialogQueue.reversed().forEach { permission ->
        AGPermissionDialog(
            permissionTextProvider = CoarseLocationPermissionTextProvider(context),
            isPermanentlyDeclined = !ActivityCompat.shouldShowRequestPermissionRationale(
                context as Activity,
                permission
            ),
            onDismiss = {
                navController.popBackStack()
            },
            onGrantClick = { navController.popBackStack() }) {

        }
    }

    LaunchedEffect(Unit) {
        requestPermission.launch(permissionsToRequest)
    }


    var columnScrollEnabled by remember { mutableStateOf(true) }
    val cameraPositionState = rememberCameraPositionState()
    var isMapLoaded by remember { mutableStateOf(false) }

    LaunchedEffect(cameraPositionState.isMoving) {
        if (!cameraPositionState.isMoving) {
            columnScrollEnabled = true
        }
    }

    LaunchedEffect(farmPolygon, isMapLoaded) {
        if (isMapLoaded) {
            if (farmPolygon.isNotEmpty()) {
                val latLngBounds = LatLngBounds.builder()
                farmPolygon.map {
                    latLngBounds.include(it)
                }
                cameraPositionState.animate(
                    CameraUpdateFactory.newLatLngBounds(
                        latLngBounds.build(),
                        100
                    )
                )
            }
        }
    }

    LaunchedEffect(Unit) {
        viewModel.toastMessage.collect { message ->
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
        }
    }

    Scaffold(
        modifier = modifier.imePadding(),
        contentWindowInsets = WindowInsets(bottom = 0),
        topBar = { TopAppBar(onNavigationClick = { navController.popBackStack() }) }
    ) { paddingValues ->
        if (uiState.isGettingData) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }
        } else {
            LandDetailActivityAddContent(
                modifier = Modifier
                    .padding(paddingValues)
                    .verticalScroll(
                        rememberScrollState(),
                        enabled = columnScrollEnabled
                    ),
                cameraPositionState = cameraPositionState,
                onMapTouched = {
                    columnScrollEnabled = false
                },
                listLatLng = farmPolygon,
                onMapLoaded = { isMapLoaded = true },
                landMarker = landMarker,
                onAddMarker = {
                    viewModel.setMarker(cameraPositionState.position.target)
                },
                onClearMarker = {
                    viewModel.setMarker(null)
                },
                uiState = uiState,
                onLandAreaChange = viewModel::updateLandArea,
                onPlantAmountChange = viewModel::updatePlantAmount,
                onProductionCostChange = viewModel::updateProductionCost,
                onUnitChange = viewModel::updateSelectedUnit,
                onTypeOfPlantingChange = viewModel::updateTypeOfPlanting,
                listOfCommodity = listOfCommodity,
                updateCommodity = viewModel::updateCommodity,
                updateDatePlant = viewModel::updateDatePlant,
                onSaveClick = viewModel::save,
                isPermissionGranted = isPermissionGranted,
            )
        }

    }

    if (uiState.isLoading) {
        Dialog(onDismissRequest = {}) {
            CircularProgressIndicator()
        }
    }

    if (uiState.isSuccess) {
        LaunchedEffect(Unit) {
            navController.popBackStack()
        }
    }
}

@Composable
fun LandDetailActivityAddContent(
    modifier: Modifier = Modifier,
    cameraPositionState: CameraPositionState,
    onMapTouched: () -> Unit,
    listLatLng: List<LatLng>,
    onMapLoaded: () -> Unit,
    landMarker: LatLng?,
    onAddMarker: () -> Unit,
    onClearMarker: () -> Unit,
    uiState: LandDetailActivityAddUiState,
    onLandAreaChange: (String) -> Unit,
    onUnitChange: (UnitSelectInputImpl) -> Unit,
    onPlantAmountChange: (String) -> Unit,
    onProductionCostChange: (String) -> Unit,
    onTypeOfPlantingChange: (TypeOfPlantingModel) -> Unit,
    listOfCommodity: Resource<List<PlantCommoditySelectInputImpl>>?,
    updateCommodity: (CommodityModel) -> Unit,
    updateDatePlant: (String) -> Unit,
    onSaveClick: () -> Unit,
    isPermissionGranted: Boolean
) {
    val coroutineScope = rememberCoroutineScope()

    Box(modifier) {
        Column(modifier = Modifier) {
            if (isPermissionGranted) {
                Box(modifier = Modifier) {
                    AGMaps(
                        modifier = Modifier.fillMaxWidth(),
                        cameraPositionState = cameraPositionState,
                        onMapTouched = onMapTouched,
                        onMapLoaded = onMapLoaded,
                        onMyLocationButtonClick = {
                            coroutineScope.launch {
                                val latLngBounds = LatLngBounds.builder()
                                listLatLng.map {
                                    latLngBounds.include(it)
                                }
                                cameraPositionState.animate(
                                    CameraUpdateFactory.newLatLngBounds(
                                        latLngBounds.build(),
                                        100
                                    )
                                )
                            }
                            true
                        }
                    ) {
                        if (landMarker != null) {
                            MarkerComposable(state = MarkerState(landMarker)) {
                                Icon(
                                    modifier = Modifier.size(34.dp),
                                    painter = painterResource(id = R.drawable.map_pin),
                                    contentDescription = null,
                                    tint = Red1
                                )
                            }
                        }
                        AGMapsPolygonDefault(listLatLng = listLatLng)
                    }
                    AGMapsCrosshair(modifier = Modifier.align(Alignment.Center))
                }

                Column {
                    AGMapsMarkerController(
                        modifier = Modifier,
                        onAddClick = onAddMarker,
                        onClearClick = onClearMarker,
                        addLabel = "Tandai Lahan",
                        clearLabel = "Hapus Tanda"
                    )
                    if (!uiState.inputLandMarker.isValid) {
                        Text(
                            modifier = Modifier.padding(horizontal = 18.dp),
                            text = uiState.inputLandMarker.message,
                            color = md_theme_light_error,
                            style = MaterialTheme.typography.bodySmall
                        )
                    }
                }
            }
            Spacer(Modifier.padding(bottom = 22.dp))
            Column(
                modifier = Modifier.padding(horizontal = 18.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                AGInputLayout(label = "Komoditas") {
                    AGSelectInputWithSearch(
                        modifier = Modifier.fillMaxWidth(),
                        value = uiState.commodity.name,
                        onValueChange = {
                            updateCommodity(CommodityModel(it.value, it.label))
                        },
                        label = "Komoditas",
                        placeholder = "Pilih Komoditas",
                        options = when (listOfCommodity) {
                            is Resource.Success -> listOfCommodity.data!!
                            else -> emptyList()
                        },
                        isLoading = listOfCommodity is Resource.Loading,
                        isError = !uiState.inputCommodity.isValid,
                        supportingText = if (!uiState.inputCommodity.isValid) {
                            { Text(text = uiState.inputCommodity.message) }
                        } else null
                    )
                }
                AGInputLayout(label = "Luas Lahan Tanam") {
                    AGInputWithSelectSuffix(
                        value = uiState.landArea,
                        onValueChange = onLandAreaChange,
                        onUnitChange = {
                            onUnitChange(it)
                        },
                        placeholder = "Sisa ${uiState.remainingLandArea}",
                        suffix = uiState.unit.label,
                        isError = !uiState.inputLandArea.isValid,
                        listSuffix = uiState.listOfUnit
                    )
                    if (!uiState.inputLandArea.isValid) {
                        Text(
                            text = uiState.inputLandArea.message,
                            color = md_theme_light_error,
                            style = MaterialTheme.typography.bodySmall
                        )
                    }
                }
                AGInputLayout(label = "Tanggal Tanam") {
                    AGInputDate(
                        value = if (uiState.datePlant.isNotEmpty()) DateUtils.convertDateToLong(
                            uiState.datePlant
                        ) else null,
                        onValueChange = {
                            if (it != null) {
                                updateDatePlant(DateUtils.convertLongToTime(it))
                            }
                        },
                        isError = !uiState.inputDatePlant.isValid,
                        supportingText = if (!uiState.inputDatePlant.isValid) {
                            { Text(text = uiState.inputDatePlant.message) }
                        } else null
                    )
                }
                AGInputLayout(label = "Jenis Tanam") {
                    AGSelectInputWithSearch(
                        value = uiState.typeOfPlating.value,
                        onValueChange = {
                            onTypeOfPlantingChange(TypeOfPlantingModel(it.label, it.value, it.unit))
                        },
                        label = "Jenis Tanam",
                        placeholder = "Pilih Jenis Tanam",
                        options = uiState.listTypeOfPlating,
                        isError = !uiState.inputTypeOfPlanting.isValid,
                        supportingText = if (!uiState.inputTypeOfPlanting.isValid) {
                            { Text(text = uiState.inputTypeOfPlanting.message) }
                        } else null
                    )
                }
                if (uiState.typeOfPlating.value.isNotEmpty()) {
                    AGInputLayout(label = "Jumlah Tanam") {
                        AGInputWithSuffix(
                            value = uiState.plantAmount,
                            onValueChange = onPlantAmountChange,
                            placeholder = "0",
                            suffix = uiState.typeOfPlating.unit,
                            isError = !uiState.inputPlantAmount.isValid
                        )
                        if (!uiState.inputPlantAmount.isValid) {
                            Text(
                                text = uiState.inputPlantAmount.message,
                                color = md_theme_light_error,
                                style = MaterialTheme.typography.bodySmall
                            )
                        }
                    }
                }
                AGInputLayout(label = "Biaya Produksi") {
                    AGInputWithPrefix(
                        value = uiState.productionCost,
                        onValueChange = onProductionCostChange,
                        placeholder = "0",
                        prefix = "Rp",
                        keyboardType = KeyboardType.NumberPassword,
                        isError = !uiState.inputProductionCost.isValid,
                        visualTransformation = NumberTransformation()
                    )
                    if (!uiState.inputProductionCost.isValid) {
                        Text(
                            text = uiState.inputProductionCost.message,
                            color = md_theme_light_error,
                            style = MaterialTheme.typography.bodySmall
                        )
                    }
                }
            }
            Spacer(Modifier.padding(bottom = 24.dp))
            Box(Modifier.padding(horizontal = 18.dp)) {
                AGButton(
                    modifier = Modifier.fillMaxWidth(),
                    onClick = onSaveClick,
                    contentPadding = PaddingValues(horizontal = 40.dp, vertical = 20.dp),
                    shape = RoundedCornerShape(12.dp),
                    enabled = !uiState.isLoading
                ) {
                    Text(text = "Simpan", fontSize = 16.sp, fontWeight = FontWeight.Bold)
                }
            }
            Spacer(Modifier.padding(bottom = 30.dp))
        }
    }
}

@Composable
private fun TopAppBar(modifier: Modifier = Modifier, onNavigationClick: () -> Unit) {
    AGTopAppBar(
        modifier = modifier,
        navigationIcon = { AGTopAppBarDefaultNavigationIcon(onClick = onNavigationClick) }) {
        AGTopAppBarDefaultTitle(text = "Tambah Aktivitas Lahan")
    }
}