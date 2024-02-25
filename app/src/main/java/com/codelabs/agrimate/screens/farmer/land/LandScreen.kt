package com.codelabs.agrimate.screens.farmer.land

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import com.codelabs.agrimate.ui.common.menu.farmerNavBarMenu
import com.codelabs.agrimate.ui.components.AGInputSearch
import com.codelabs.agrimate.ui.components.AGLandCard
import com.codelabs.agrimate.ui.components.AGNavBarMenuItem
import com.codelabs.agrimate.ui.components.AGNavbar
import com.codelabs.agrimate.ui.components.AGTopAppBar
import com.codelabs.agrimate.ui.components.AGTopAppBarDefaultTitle
import com.codelabs.agrimate.ui.components.dialog.AGDeleteConfirmDialog
import com.codelabs.agrimate.ui.components.shimmer.shimmerBrush
import com.codelabs.agrimate.ui.navigation.AGRoute
import com.codelabs.agrimate.ui.navigation.DestinationsArg
import com.codelabs.agrimate.ui.theme.Green100
import com.codelabs.agrimate.ui.theme.GreyScale500
import com.codelabs.agrimate.utils.DateUtils
import com.codelabs.core.domain.model.FarmerLandListItemModel
import com.codelabs.core.utils.Resource

@Composable
fun LandScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    viewModel: LandViewModel = hiltViewModel()
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    val farmerLand by viewModel.farmerLand.collectAsStateWithLifecycle()
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    DisposableEffect(Unit) {
        viewModel.getFarmerLand()
        onDispose { }
    }

    Scaffold(
        modifier = modifier,
        topBar = {
            LandTopAppBar()
        },
        floatingActionButton = {
            LandAddButton(onClick = {
                navController.navigate(AGRoute.Farmer.Main.Land.Add.route)
            }
            )
        },
        bottomBar = {
            AGNavbar(modifier = Modifier.fillMaxWidth()) {
                farmerNavBarMenu.forEach { menu ->
                    AGNavBarMenuItem(
                        modifier = Modifier.weight(1f),
                        data = menu,
                        onClick = {
                            navController.navigate(menu.link) {
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        },
                        selected = currentDestination?.hierarchy?.any { it.route == menu.link } == true)
                }
            }
        }
    ) { paddingValues ->
        LandContent(
            modifier = Modifier.padding(paddingValues),
            uiState = uiState,
            navigateToLandDetail = {
                navController.navigate(
                    AGRoute.Farmer.Main.Land.Detail.route.replace(
                        "{${DestinationsArg.LAND_ID_ARG}}",
                        it
                    )
                )
            },
            farmerLand = farmerLand,
            onUpdateClick = {
                navController.navigate(
                    AGRoute.Farmer.Main.Land.Edit.route.replace(
                        "{${DestinationsArg.LAND_ID_ARG}}",
                        it
                    )
                )
            },
            onDelete = {
                viewModel.deleteLand(it)
            },
        )
    }

}

@Composable
fun LandContent(
    modifier: Modifier = Modifier,
    uiState: LandUiState,
    navigateToLandDetail: (String) -> Unit,
    farmerLand: Resource<List<FarmerLandListItemModel>>?,
    onUpdateClick: (id: String) -> Unit,
    onDelete: (id: String) -> Unit,
) {
    var deleteConfirmDialogShowed by remember { mutableStateOf(false) }
    var selectedDeleteLandId by remember { mutableStateOf<String?>(null) }

    if (deleteConfirmDialogShowed) {
        AGDeleteConfirmDialog(
            onDismissRequest = { deleteConfirmDialogShowed = false },
            onDeleteClick = {
                onDelete(selectedDeleteLandId.toString())
                deleteConfirmDialogShowed = false
            })
    }

    if (uiState.isDeleting) {
        CircularProgressIndicator()
    }

    Column(modifier = modifier) {
        Box(
            modifier = Modifier.padding(
                start = 18.dp,
                end = 18.dp,
                bottom = 18.dp,
                top = 24.dp
            )
        ) {
            AGInputSearch(
                modifier = Modifier.fillMaxWidth(),
                value = "",
                onValueChange = {/*TODO*/ },
                placeholder = "Cari Lahan"
            )
        }
        if (farmerLand is Resource.Error) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = farmerLand.message.orEmpty(),
                    textAlign = TextAlign.Center
                )
            }
        } else {
            LazyColumn(
                contentPadding = PaddingValues(start = 18.dp, end = 18.dp, bottom = 16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
            ) {
                when (farmerLand) {
                    is Resource.Success -> {
                        if (farmerLand.data?.isEmpty() == true) {
                            item {
                                Box(
                                    modifier = Modifier
                                        .fillParentMaxSize()
                                        .weight(1f),
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
                        } else {
                            items(farmerLand.data ?: emptyList(), key = { it.id.toString() }) {
                                AGLandCard(
                                    onClick = { navigateToLandDetail(it.id.toString()) },
                                    title = it.name.toString(),
                                    district = it.district.toString(),
                                    landArea = it.landArea ?: 0.0,
                                    lastActivity = DateUtils.formatDate(it.lastActivity.toString())
                                        .toString(),
                                    imageUrl = it.image.toString(),
                                    onUpdateClick = { onUpdateClick(it.id.orEmpty()) },
                                    onDeleteClick = {
                                        deleteConfirmDialogShowed = true
                                        selectedDeleteLandId = it.id
                                    }
                                )
                            }
                        }

                    }

                    else -> {
                        items(3) {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(134.dp)
                                    .background(
                                        shimmerBrush(),
                                        RoundedCornerShape(12.dp)
                                    )
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun LandTopAppBar(
    modifier: Modifier = Modifier,
) {
    AGTopAppBar(
        modifier = modifier,
    ) {
        AGTopAppBarDefaultTitle(text = "Pilih Lahan")
    }
}

@Composable
private fun LandAddButton(modifier: Modifier = Modifier, onClick: () -> Unit) {
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