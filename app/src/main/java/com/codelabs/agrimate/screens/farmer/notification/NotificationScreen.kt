package com.codelabs.agrimate.screens.farmer.notification

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Tune
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.codelabs.agrimate.ui.components.AGNotificationCard
import com.codelabs.agrimate.ui.components.AGNotificationFilterSheetContent
import com.codelabs.agrimate.ui.components.AGNotificationSectionTitle
import com.codelabs.agrimate.ui.components.AGTopAppBar
import com.codelabs.agrimate.ui.components.AGTopAppBarDefaultActionButton
import com.codelabs.agrimate.ui.components.AGTopAppBarDefaultNavigationIcon
import com.codelabs.agrimate.ui.components.AGTopAppBarDefaultTitle
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotificationScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
) {
    var openFilterSheet by rememberSaveable { mutableStateOf(false) }
    val bottomSheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true
    )
    val coroutineScope = rememberCoroutineScope()

    val closeFilterSheet = {
        coroutineScope.launch { bottomSheetState.hide() }.invokeOnCompletion {
            if (!bottomSheetState.isVisible) {
                openFilterSheet = false
            }
        }
    }

    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(onNavigationClick = {
                navController.popBackStack()
            }, onFilterClick = {
                openFilterSheet = true
            })
        },
        contentWindowInsets = WindowInsets(bottom = 0.dp)
    ) { paddingValues ->
        Box(Modifier.padding(paddingValues)) {
            LazyColumn {
                item {
                    AGNotificationSectionTitle(text = "Terbaru")
                }
                items(2) {
                    AGNotificationCard()
                }
                item {
                    AGNotificationSectionTitle(text = "Kemarin")
                }
                items(10) {
                    AGNotificationCard()
                }
            }
        }
    }

    if (openFilterSheet) {
        ModalBottomSheet(
            onDismissRequest = { openFilterSheet = false },
            sheetState = bottomSheetState,
            windowInsets = WindowInsets(bottom = 0),
            containerColor = Color.White,
            tonalElevation = 0.dp
        ) {
            AGNotificationFilterSheetContent(onClose = {
                closeFilterSheet()
            })
        }
    }
}

@Composable
fun TopAppBar(
    modifier: Modifier = Modifier,
    onNavigationClick: () -> Unit,
    onFilterClick: () -> Unit,
) {
    AGTopAppBar(
        modifier = modifier,
        navigationIcon = { AGTopAppBarDefaultNavigationIcon(onClick = onNavigationClick) },
        trailingContent = {
            AGTopAppBarDefaultActionButton(onClick = onFilterClick) {
                Icon(
                    imageVector = Icons.Outlined.Tune,
                    contentDescription = "Filter",
                    tint = Color.White
                )
            }
        }
    ) {
        AGTopAppBarDefaultTitle(text = "Notifikasi")
    }
}