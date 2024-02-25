package com.codelabs.agrimate.screens.farmer.planthealth

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.camera.core.AspectRatio
import androidx.camera.core.ImageCapture
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.codelabs.agrimate.R
import com.codelabs.agrimate.ui.components.AGCheckDiseaseResultSheet
import com.codelabs.agrimate.ui.components.AGPermissionDialog
import com.codelabs.agrimate.ui.components.CameraPreview
import com.codelabs.agrimate.ui.navigation.AGRoute
import com.codelabs.agrimate.ui.navigation.DestinationsArg
import com.codelabs.agrimate.ui.theme.RedStatus
import com.codelabs.agrimate.utils.CameraPermissionTextProvider
import com.codelabs.agrimate.utils.CameraUtils
import com.codelabs.agrimate.utils.PermissionsViewModel
import kotlinx.coroutines.cancelChildren
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CheckDiseaseScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    viewModel: CheckDiseaseViewModel = hiltViewModel(),
    permissionsViewModel: PermissionsViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    val requestScope = rememberCoroutineScope()

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    /**
     * Handling Permission
     */
    val permissionToRequest =
        arrayOf(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE)
    val permissionDialogQueue = permissionsViewModel.visiblePermissionDialogQueue

    val requestPermission = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions(),
        onResult = { perms ->
            permissionToRequest.forEach { permission ->
                permissionsViewModel.onPermissionResult(
                    permission = permission,
                    isGranted = perms[permission] == true
                )
            }
        })

    LaunchedEffect(Unit) {
        requestPermission.launch(permissionToRequest)
    }

    permissionDialogQueue.reversed().forEach { permission ->
        AGPermissionDialog(
            permissionTextProvider = when (permission) {
                Manifest.permission.CAMERA -> CameraPermissionTextProvider(context)
                else -> return@forEach
            },
            isPermanentlyDeclined = !ActivityCompat.shouldShowRequestPermissionRationale(
                context as Activity,
                permission
            ),
            onDismiss = {
                permissionsViewModel.dismissDialog()
                navController.popBackStack()
            },
            onGrantClick = {
                context.startActivity(
                    Intent(
                        Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                        Uri.fromParts("package", context.packageName, null)
                    )
                )
            },
            onConfirmClick = {
                permissionsViewModel.dismissDialog()
                requestPermission.launch(permissionToRequest)
            }

        )
    }

    /**
     * Handling take image
     */
    var isCapturing by rememberSaveable { mutableStateOf(false) }
    val imageCapture by remember {
        mutableStateOf(
            ImageCapture.Builder().setCaptureMode(ImageCapture.CAPTURE_MODE_MINIMIZE_LATENCY)
                .setTargetAspectRatio(AspectRatio.RATIO_4_3)
                .build()
        )
    }

    val dismissCapturing = {
        isCapturing = false
        requestScope.coroutineContext.cancelChildren()
        viewModel.resetPrediction()
        viewModel.updateSelectedImage("")
    }

    val onSuccessCapture: (uri: Uri?) -> Unit = { imageUri ->
        if (imageUri != null) {
            requestScope.launch {
                val file = CameraUtils.imageUriToFile(imageUri, context)
                viewModel.updateSelectedImage(file.toString())
                if (file != null) {
                    viewModel.predict(file)
                }
            }
        }
    }

    val onFailedCapture: (message: String?) -> Unit = { errorMsg ->
        dismissCapturing()
        Toast.makeText(context, "$errorMsg", Toast.LENGTH_SHORT)
            .show()
    }

    /**
     * Handling pick image from gallery
     */

    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult(),
        onResult = { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val selectedImgUri = result.data?.data
                if (selectedImgUri != null) {
                    isCapturing = true
                    onSuccessCapture(selectedImgUri)
                }
            }
        }
    )

    val pickFromGallery = {
        val intent = Intent().apply {
            action = Intent.ACTION_GET_CONTENT
            type = "image/*"
        }
        galleryLauncher.launch(Intent.createChooser(intent, "Pilih gambar!"))
    }

    // end of handling take image

    val predictResult by viewModel.predictRes.collectAsStateWithLifecycle()
    val bottomSheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    LaunchedEffect(Unit) {
        viewModel.toastMessage.collect { message ->
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
        }
    }

    Box(
        modifier.windowInsetsPadding(WindowInsets(bottom = 0.dp, top = 0.dp))
    ) {
        CheckDiseaseContent(
            modifier = Modifier,
            imageCapture = imageCapture,
            onTakeImage = {
                isCapturing = true
                CameraUtils.takePicture(
                    context,
                    imageCapture,
                    onSuccessCapture,
                    onFailedCapture
                )
            },
            onPickPickFromGallery = pickFromGallery,
            isCapturing = isCapturing,
            uiState = uiState
        )
    }

    // Show process/result when/after capturing
    if (isCapturing) {
        ModalBottomSheet(
            onDismissRequest = {
                dismissCapturing()
            },
            sheetState = bottomSheetState,
            tonalElevation = 0.dp,
            windowInsets = WindowInsets(bottom = 0),
            containerColor = Color.White
        ) {
            AGCheckDiseaseResultSheet(
                modifier = Modifier, onReset = {
                    coroutineScope.launch {
                        dismissCapturing()
                    }
                },
                diseasePredict = predictResult,
                onDetailClick = {
                    navController.navigate(
                        AGRoute.Farmer.Main.Home.Explorer.HelpPlant.PlantHealth.DiseaseDetail.route.replace(
                            "{${DestinationsArg.PLANT_DISEASE_ID_ARG}}",
                            it
                        ),
                    )
                }
            )
        }
    }
}

@Composable
fun CheckDiseaseContent(
    modifier: Modifier = Modifier,
    imageCapture: ImageCapture,
    onTakeImage: () -> Unit,
    onPickPickFromGallery: () -> Unit,
    isCapturing: Boolean,
    uiState: CheckDiseaseUiState,
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {
        Column(
            Modifier
                .fillMaxSize()
                .background(Color.Black),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(Modifier.weight(1f)) {

                if (uiState.selectedImage.isNotEmpty()) {
                    Image(
                        modifier = Modifier.fillMaxSize(),
                        painter = rememberAsyncImagePainter(model = uiState.selectedImage),
                        contentDescription = null,
                        contentScale = ContentScale.Crop
                    )
                } else {
                    CameraPreview(
                        modifier = Modifier,
                        imageCapture = imageCapture,
                    )
                    Image(
                        modifier = Modifier
                            .padding(horizontal = 55.dp)
                            .align(Alignment.Center),
                        painter = painterResource(id = R.drawable.ag_camera_target_outline),
                        contentDescription = null
                    )
                    Text(
                        modifier = Modifier
                            .align(Alignment.BottomCenter)
                            .padding(bottom = 29.dp, start = 55.dp, end = 55.dp),
                        text = "Posisikan sejajar dengan tumbuhan yang akan di diagnosis",
                        color = Color.White,
                        textAlign = TextAlign.Center
                    )
                }
            }
            Column {
                CameraControls(
                    modifier = Modifier,
                    onTakeImage = { onTakeImage() },
                    onPickPickFromGallery = onPickPickFromGallery,
                    enabled = !isCapturing
                )
            }
        }
    }
}

@Composable
private fun CameraControls(
    modifier: Modifier = Modifier,
    onTakeImage: () -> Unit,
    onPickPickFromGallery: () -> Unit,
    enabled: Boolean
) {
    Surface(color = Color.Black, modifier = modifier) {
        Row(
            modifier = Modifier
                .defaultMinSize(minHeight = 139.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(53.dp, Alignment.CenterHorizontally),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                modifier = Modifier
                    .size(47.dp)
                    .aspectRatio(1f)
                    .clickable(enabled) { onPickPickFromGallery() },
                painter = painterResource(id = R.drawable.icon_gallery),
                contentDescription = null,
                tint = Color.White
            )
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(100))
                    .background(
                        RedStatus.copy(
                            alpha = if (enabled) 1f else 0.7f
                        )
                    )
                    .border(4.dp, Color.White, RoundedCornerShape(100))
                    .size(81.dp)
                    .clickable(enabled) { onTakeImage() },
            )
            Icon(
                modifier = Modifier
                    .size(47.dp)
                    .aspectRatio(1f)
                    .clickable { /*TODO*/ },
                painter = painterResource(id = R.drawable.icon_help),
                contentDescription = null,
                tint = Color.White
            )
        }
    }
}

