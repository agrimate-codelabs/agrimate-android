package com.codelabs.agrimate.screens.farmer.land.edit

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.IntentSender
import android.location.Geocoder
import android.location.Location
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.core.app.ActivityCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.codelabs.agrimate.ui.common.impl.RegionSelectInputImpl
import com.codelabs.agrimate.ui.components.AGButton
import com.codelabs.agrimate.ui.components.AGInputFullAddress
import com.codelabs.agrimate.ui.components.AGInputId
import com.codelabs.agrimate.ui.components.AGInputImage
import com.codelabs.agrimate.ui.components.AGInputLayout
import com.codelabs.agrimate.ui.components.AGInputWithSuffix
import com.codelabs.agrimate.ui.components.AGMessageDialogSuccess
import com.codelabs.agrimate.ui.components.AGPermissionDialog
import com.codelabs.agrimate.ui.components.AGSelectInputWithSearch
import com.codelabs.agrimate.ui.components.AGTopAppBar
import com.codelabs.agrimate.ui.components.AGTopAppBarDefaultNavigationIcon
import com.codelabs.agrimate.ui.components.AGTopAppBarDefaultTitle
import com.codelabs.agrimate.ui.components.maps.AGMaps
import com.codelabs.agrimate.ui.components.maps.AGMapsCrosshair
import com.codelabs.agrimate.ui.components.maps.AGMapsMarkerController
import com.codelabs.agrimate.ui.components.maps.AGMapsPolygonMarker
import com.codelabs.agrimate.ui.theme.md_theme_light_error
import com.codelabs.agrimate.utils.CameraUtils
import com.codelabs.agrimate.utils.CoarseLocationPermissionTextProvider
import com.codelabs.agrimate.utils.PermissionsViewModel
import com.codelabs.core.domain.model.RegionModel
import com.codelabs.core.utils.Resource
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.LocationSettingsRequest
import com.google.android.gms.location.Priority
import com.google.android.gms.location.SettingsClient
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.maps.android.compose.CameraPositionState
import com.google.maps.android.compose.rememberCameraPositionState
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.Locale

@Composable
fun LandEditScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    viewModel: LandEditViewModel = hiltViewModel(),
    permissionsViewModel: PermissionsViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val geocoder by remember { mutableStateOf(Geocoder(context, Locale("id", "ID"))) }

    val uiState by viewModel.uiState.collectAsState()
    val listOfProvince by viewModel.listOfProvince.observeAsState()
    val listOfCity by viewModel.listOfCity.observeAsState()
    val listOfDistrict by viewModel.listOfDistrict.observeAsState()
    val listOfVillage by viewModel.listOfVillage.observeAsState()

    LaunchedEffect(Unit) {
        delay(1000)
        viewModel.getFarmerLandData()
    }

    val permissionsToRequest by remember {
        mutableStateOf(
            arrayOf(
                Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION
            )
        )
    }
    var isPermissionGranted by remember { mutableStateOf(false) }

    val imageLauncher =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.GetContent(),
            onResult = { imageUri ->
                if (imageUri != null) {
                    val file = CameraUtils.imageUriToFile(imageUri, context)
                    if (file != null) {
                        viewModel.uploadImage(file)
                    }
                }
            })

    val requestPermission =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.RequestMultiplePermissions(),
            onResult = { perms ->
                permissionsToRequest.forEach { permission ->
                    permissionsViewModel.onPermissionResult(
                        permission = permission, isGranted = perms[permission] == true
                    )
                    isPermissionGranted = perms[permission] == true
                }
            })

    val permissionDialogQueue = permissionsViewModel.visiblePermissionDialogQueue

    permissionDialogQueue.reversed().forEach { permission ->
        AGPermissionDialog(permissionTextProvider = CoarseLocationPermissionTextProvider(context),
            isPermanentlyDeclined = !ActivityCompat.shouldShowRequestPermissionRationale(
                context as Activity, permission
            ),
            onDismiss = { /*TODO*/ },
            onGrantClick = { /*TODO*/ }) {

        }
    }

    LaunchedEffect(Unit) {
        requestPermission.launch(permissionsToRequest)
    }

    val cameraPositionState = rememberCameraPositionState()
    var columnScrollEnabled by remember {
        mutableStateOf(true)
    }

    LaunchedEffect(uiState.isGettingData) {
        if (uiState.landMarker.isNotEmpty() && !uiState.isGettingData) {
            val latLngBounds = LatLngBounds.builder()
            uiState.landMarker.map {
                latLngBounds.include(it)
            }
            cameraPositionState.animate(
                CameraUpdateFactory.newLatLngBounds(latLngBounds.build(), 100), 250
            )
        }
    }


    LaunchedEffect(cameraPositionState.isMoving) {
        if (!cameraPositionState.isMoving) {
            columnScrollEnabled = true
        }
    }

    LaunchedEffect(Unit) {
        viewModel.toastMessage.collect { message ->
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
        }
    }

    if (uiState.isLoading) {
        Dialog(onDismissRequest = {}) {
            CircularProgressIndicator()
        }
    }

    if (uiState.isSuccess) {
        AGMessageDialogSuccess(
            onDismissRequest = { navController.popBackStack() },
            title = "Ubah Data Lahan Berhasil!",
            description = ""
        )
    }

    Scaffold(modifier = modifier.imePadding(),
        contentWindowInsets = WindowInsets(0, 0, 0, 0),
        topBar = { TopAppBar(onNavigationClick = { navController.popBackStack() }) }) { paddingValues ->
        if (uiState.isGettingData) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }
        } else {
            LandEditContent(
                modifier = Modifier
                    .padding(paddingValues)
                    .verticalScroll(rememberScrollState(), enabled = columnScrollEnabled),
                geocoder = geocoder,
                mapCameraPositionState = cameraPositionState,
                onMapTouched = {
                    columnScrollEnabled = false
                },
                addLandMarkerPoint = {
                    viewModel.updateLandMarker(uiState.landMarker + cameraPositionState.position.target)
                },
                clearLandMarkerPoint = {
                    viewModel.updateLandMarker(emptyList())
                },
                uiState = uiState,
                listOfProvince = listOfProvince,
                listOfCity = listOfCity,
                listOfDistrict = listOfDistrict,
                listOfVillage = listOfVillage,
                onLandNameChange = viewModel::updateLandName,
                onProvinceChange = viewModel::updateProvince,
                onCityChange = viewModel::updateCity,
                onDistrictChange = viewModel::updateDistrict,
                onVillageChange = viewModel::updateVillages,
                onAddressChange = viewModel::updateAddress,
                onLandAreaChange = viewModel::updateLandArea,
                onInputImageClick = {
                    imageLauncher.launch("image/*")
                },
                onSaveClick = viewModel::saveLand,
                isPermissionGranted = isPermissionGranted
            )
        }

    }
}

@Composable
fun LandEditContent(
    modifier: Modifier = Modifier,
    geocoder: Geocoder,
    addLandMarkerPoint: () -> Unit,
    clearLandMarkerPoint: () -> Unit,
    mapCameraPositionState: CameraPositionState,
    onMapTouched: () -> Unit,
    uiState: LandEditUiState,
    listOfProvince: Resource<List<RegionSelectInputImpl>>?,
    listOfCity: Resource<List<RegionSelectInputImpl>>?,
    listOfDistrict: Resource<List<RegionSelectInputImpl>>?,
    listOfVillage: Resource<List<RegionSelectInputImpl>>?,
    onLandNameChange: (String) -> Unit,
    onProvinceChange: (RegionModel) -> Unit,
    onCityChange: (RegionModel) -> Unit,
    onDistrictChange: (RegionModel) -> Unit,
    onVillageChange: (RegionModel) -> Unit,
    onAddressChange: (String) -> Unit,
    onLandAreaChange: (String) -> Unit,
    onInputImageClick: () -> Unit,
    onSaveClick: () -> Unit,
    isPermissionGranted: Boolean,
) {
    val coroutineScope = rememberCoroutineScope()

    Column(
        modifier = modifier.padding(top = 19.dp, bottom = 29.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        Column(
            modifier = Modifier.padding(
                start = 18.dp,
                end = 18.dp,
            ), verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            AGInputLayout(label = "Nama Lahan (Opsional)") {
                AGInputId(
                    modifier = Modifier.fillMaxWidth(),
                    value = uiState.landName,
                    onValueChange = onLandNameChange,
                    placeholder = "Masukkan nama lahan",
                )
            }

            AGInputLayout(label = "Provinsi") {
                AGSelectInputWithSearch(modifier = Modifier.fillMaxWidth(),
                    value = uiState.province.name,
                    onValueChange = {
                        onProvinceChange(RegionModel(it.value, it.label))
                        val location = geocoder.getFromLocationName(it.label, 1)?.get(0)
                        if (location != null) {
                            coroutineScope.launch {
                                mapCameraPositionState.animate(
                                    CameraUpdateFactory.newCameraPosition(
                                        CameraPosition.fromLatLngZoom(
                                            LatLng(location.latitude, location.longitude), 10f
                                        )
                                    ), 250
                                )
                            }
                        }
                    },
                    label = "Provinsi",
                    placeholder = "Pilih Provinsi",
                    options = when (listOfProvince) {
                        is Resource.Success -> listOfProvince.data!!
                        else -> emptyList()
                    },
                    isError = !uiState.inputProvince.isValid,
                    supportingText = if (!uiState.inputProvince.isValid) {
                        { Text(text = uiState.inputProvince.message) }
                    } else null,
                    enabled = false
                )
            }

            AGInputLayout(label = "Kota") {
                AGSelectInputWithSearch(modifier = Modifier.fillMaxWidth(),
                    value = uiState.city.name,
                    onValueChange = {
                        onCityChange(RegionModel(it.value, it.label))
                        val location =
                            geocoder.getFromLocationName("$it.label ${uiState.province}", 1)?.get(0)
                        if (location != null) {
                            coroutineScope.launch {
                                mapCameraPositionState.animate(
                                    CameraUpdateFactory.newCameraPosition(
                                        CameraPosition.fromLatLngZoom(
                                            LatLng(location.latitude, location.longitude), 12.5f
                                        )
                                    ), 250
                                )
                            }
                        }
                    },
                    label = "Kota",
                    placeholder = "Pilih Kota",
                    options = when (listOfCity) {
                        is Resource.Success -> listOfCity.data!!
                        else -> emptyList()
                    },
                    isError = !uiState.inputCity.isValid,
                    supportingText = if (!uiState.inputCity.isValid) {
                        { Text(text = uiState.inputCity.message) }
                    } else null,
                    enabled = false
                )
            }

            AGInputLayout(label = "Kecamatan") {
                AGSelectInputWithSearch(modifier = Modifier.fillMaxWidth(),
                    value = uiState.district.name,
                    onValueChange = {
                        onDistrictChange(RegionModel(it.value, it.label))
                        val location = geocoder.getFromLocationName(
                            "$it.label ${uiState.city} ${uiState.province}", 1
                        )?.get(0)
                        if (location != null) {
                            coroutineScope.launch {
                                mapCameraPositionState.animate(
                                    CameraUpdateFactory.newCameraPosition(
                                        CameraPosition.fromLatLngZoom(
                                            LatLng(location.latitude, location.longitude), 14.5f
                                        )
                                    ), 250
                                )
                            }
                        }
                    },
                    label = "Kecamatan",
                    placeholder = "Pilih Kecamatan",
                    options = when (listOfDistrict) {
                        is Resource.Success -> listOfDistrict.data!!
                        else -> emptyList()
                    },
                    isError = !uiState.inputDistrict.isValid,
                    supportingText = if (!uiState.inputDistrict.isValid) {
                        { Text(text = uiState.inputDistrict.message) }
                    } else null,
                    enabled = false
                )
            }

            AGInputLayout(label = "Kelurahan") {
                AGSelectInputWithSearch(modifier = Modifier.fillMaxWidth(),
                    value = uiState.village.name,
                    onValueChange = {
                        onVillageChange(RegionModel(it.value, it.label))
                        val location = geocoder.getFromLocationName(
                            "$it ${uiState.district} ${uiState.city} ${uiState.province}", 1
                        )?.get(0)
                        if (location != null) {
                            coroutineScope.launch {
                                mapCameraPositionState.animate(
                                    CameraUpdateFactory.newCameraPosition(
                                        CameraPosition.fromLatLngZoom(
                                            LatLng(location.latitude, location.longitude), 16.5f
                                        )
                                    ), 250
                                )
                            }
                        }
                    },
                    label = "Kelurahan",
                    placeholder = "Pilih Kelurahan",
                    options = when (listOfVillage) {
                        is Resource.Success -> listOfVillage.data!!
                        else -> emptyList()
                    },
                    isError = !uiState.inputVillage.isValid,
                    supportingText = if (!uiState.inputVillage.isValid) {
                        { Text(text = uiState.inputVillage.message) }
                    } else null,
                    enabled = false
                )
            }

            AGInputLayout(label = "Tandai Lahan") {
                if (isPermissionGranted) {
                    MapContent(
                        cameraPositionState = mapCameraPositionState,
                        onMapTouched = onMapTouched,
                        listLatLng = uiState.landMarker,
                        addLandMarkerPoint = addLandMarkerPoint,
                        clearLandMarkerPoint = clearLandMarkerPoint,
                    )
                }
                if (!uiState.inputLandMarker.isValid) {
                    Text(
                        text = uiState.inputLandMarker.message,
                        color = md_theme_light_error,
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            }

            AGInputLayout(label = "Luas Tanah") {
                AGInputWithSuffix(
                    value = uiState.landArea,
                    onValueChange = onLandAreaChange,
                    placeholder = "0",
                    suffix = "Hektare",
                    isError = !uiState.inputLandArea.isValid,
                )
                if (!uiState.inputLandArea.isValid) {
                    Text(
                        text = uiState.inputLandArea.message,
                        color = md_theme_light_error,
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            }

            AGInputLayout(label = "Alamat lengkap") {
                AGInputFullAddress(modifier = Modifier.fillMaxWidth(),
                    value = uiState.address,
                    onValueChange = onAddressChange,
                    placeholder = "contoh: Jl. Gatot Subroto, No. 123, RT.09/RW.01",
                    isError = !uiState.inputAddress.isValid,
                    supportingText = if (!uiState.inputAddress.isValid) {
                        { Text(text = uiState.inputAddress.message) }
                    } else null)
            }

            AGInputLayout(label = "Gambar Lahan") {
                AGInputImage(
                    modifier = Modifier.fillMaxWidth(),
                    onClick = onInputImageClick,
                    imageUrl = uiState.imageUrl,
                    isUploading = uiState.isUploadingImage,
                    isError = !uiState.inputImage.isValid,
                )
                if (!uiState.inputImage.isValid) {
                    Text(
                        text = uiState.inputImage.message,
                        color = md_theme_light_error,
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            }
        }
        Box(Modifier.padding(horizontal = 18.dp)) {
            AGButton(
                onClick = onSaveClick,
                modifier = Modifier.fillMaxWidth(),
                contentPadding = PaddingValues(horizontal = 40.dp, vertical = 20.dp),
                enabled = !uiState.isLoading
            ) {
                Text(text = "Simpan", fontSize = 16.sp, fontWeight = FontWeight.Bold)
            }
        }
    }
}

@SuppressLint("MissingPermission")
@Composable
fun MapContent(
    modifier: Modifier = Modifier,
    cameraPositionState: CameraPositionState,
    onMapTouched: () -> Unit,
    addLandMarkerPoint: () -> Unit,
    clearLandMarkerPoint: () -> Unit,
    listLatLng: List<LatLng>,
) {
    val context = LocalContext.current

    val settingResultRequest =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.StartIntentSenderForResult(),
            onResult = { activityResult ->
                if (activityResult.resultCode == Activity.RESULT_OK) {
                    Log.d("resultCode", "Accepted")
                } else {
                    Log.d("resultCode", "Denied")
                }
            })

    val locationFusedClient = remember {
        LocationServices.getFusedLocationProviderClient(context)
    }

    var location by remember {
        mutableStateOf(Location("LandLocation"))
    }

    LaunchedEffect(Unit) {
        checkLocationSetting(context, onDisabled = {
            settingResultRequest.launch(it)
        }, onEnabled = {
            locationFusedClient.lastLocation.addOnSuccessListener {
                if (it != null) {
                    location = it
                }
            }
        })
    }


    Box {
        AGMaps(
            modifier = modifier.fillMaxWidth(),
            cameraPositionState = cameraPositionState,
            onMapTouched = onMapTouched
        ) {
            AGMapsPolygonMarker(listLatLng = listLatLng)
        }
        AGMapsCrosshair(modifier = Modifier.align(Alignment.Center))
        AGMapsMarkerController(
            modifier = Modifier.align(Alignment.BottomCenter),
            onAddClick = addLandMarkerPoint,
            onClearClick = clearLandMarkerPoint
        )
    }
}

private fun checkLocationSetting(
    context: Context,
    onDisabled: (IntentSenderRequest) -> Unit,
    onEnabled: () -> Unit,
) {
    val locationRequest = LocationRequest.Builder(Priority.PRIORITY_HIGH_ACCURACY, 1000).build()
    val client: SettingsClient = LocationServices.getSettingsClient(context)
    val builder: LocationSettingsRequest.Builder =
        LocationSettingsRequest.Builder().addLocationRequest(locationRequest)
    val gpsSettingTask = client.checkLocationSettings(builder.build())
    gpsSettingTask.addOnSuccessListener {
        onEnabled()
    }
    gpsSettingTask.addOnFailureListener { exception ->
        if (exception is ResolvableApiException) {
            try {
                val intentSenderRequest = IntentSenderRequest.Builder(exception.resolution).build()
                onDisabled(intentSenderRequest)
            } catch (sendEx: IntentSender.SendIntentException) {
                Toast.makeText(context, "Error: ${sendEx.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }
}

@Composable
private fun TopAppBar(modifier: Modifier = Modifier, onNavigationClick: () -> Unit) {
    AGTopAppBar(modifier = modifier,
        navigationIcon = { AGTopAppBarDefaultNavigationIcon(onClick = onNavigationClick) }) {
        AGTopAppBarDefaultTitle(text = "Ubah Data Lahan")
    }
}