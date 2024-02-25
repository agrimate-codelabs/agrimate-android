package com.codelabs.agrimate.screens.farmer.planthealth

import android.widget.TextView
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.codelabs.agrimate.ui.common.state.UiState
import com.codelabs.agrimate.ui.components.AGButton
import com.codelabs.agrimate.ui.components.AGTopAppBar
import com.codelabs.agrimate.ui.components.AGTopAppBarDefaultNavigationIcon
import com.codelabs.agrimate.ui.components.AGTopAppBarDefaultTitle
import com.codelabs.agrimate.ui.components.shimmer.shimmerBrush
import com.codelabs.agrimate.ui.theme.Green100
import com.codelabs.core.BuildConfig
import com.codelabs.core.domain.model.PlantDiseaseDetectionModel
import io.noties.markwon.Markwon

@Composable
fun DiseaseDetailScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    viewModel: DiseaseDetailViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Scaffold(
        modifier = modifier,
        contentWindowInsets = WindowInsets(bottom = 0.dp),
        topBar = {
            TopAppBar(
                onNavigationClick = { navController.popBackStack() },
                title = (uiState as? UiState.Success)?.data?.name
                    ?: "Nama Penyakit"
            )
        },
        bottomBar = { ActionBottomBar(onClick = {/*TODO*/ }) }
    ) { paddingValues ->
        DiseaseDetailContent(
            modifier = Modifier
                .padding(paddingValues)
                .verticalScroll(rememberScrollState()),
            uiState = uiState
        )
    }
}

@Composable
fun DiseaseDetailContent(
    modifier: Modifier = Modifier,
    uiState: UiState<PlantDiseaseDetectionModel>
) {
    Box(modifier = modifier) {
        when (uiState) {
            is UiState.Error -> {
                Box(modifier = Modifier.fillMaxSize()) {
                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .align(Alignment.Center),
                        text = uiState.message,
                        textAlign = TextAlign.Center
                    )
                }
            }

            is UiState.Loading -> {
                Box(
                    modifier = Modifier
                        .padding(vertical = 200.dp)
                        .fillMaxWidth()
                ) {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                }
            }

            is UiState.Success -> {
                Column {
                    AsyncImage(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(209.dp)
                            .background(shimmerBrush()),
                        model = "${BuildConfig.API_AGRIMATE_BASE_URL}/diseases-plants/thumbnail/${uiState.data.image}",
                        contentDescription = uiState.data.name,
                        contentScale = ContentScale.Crop
                    )
                    Spacer(modifier = Modifier.padding(bottom = 17.dp))
                    Column(modifier = Modifier.padding(horizontal = 18.dp)) {
                        TextDescription(
                            text = uiState.data.symtomps,
                        )
                        Spacer(modifier = Modifier.padding(8.dp))
                        TextDescription(text = uiState.data.howTo)
                        Spacer(modifier = Modifier.padding(8.dp))
                    }
                }
            }
        }
    }
}

@Composable
private fun TextDescription(modifier: Modifier = Modifier, text: String) {
    val context = LocalContext.current
    AndroidView(modifier = modifier, factory = {
        return@AndroidView TextView(it)
    },
        update = {
            val markwon = Markwon.create(context)
            markwon.setMarkdown(it, text)
        }
    )
}

@Composable
private fun TopAppBar(
    modifier: Modifier = Modifier,
    onNavigationClick: () -> Unit,
    title: String
) {
    AGTopAppBar(
        modifier = modifier,
        navigationIcon = { AGTopAppBarDefaultNavigationIcon(onClick = onNavigationClick) }) {
        AGTopAppBarDefaultTitle(text = title)
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