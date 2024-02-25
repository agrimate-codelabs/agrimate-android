package com.codelabs.agrimate.screens.farmer.newsdetail

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.codelabs.agrimate.ui.components.AGTopAppBar
import com.codelabs.agrimate.ui.components.AGTopAppBarDefaultNavigationIcon
import com.codelabs.agrimate.ui.components.AGTopAppBarDefaultTitle
import com.codelabs.agrimate.ui.webview.AGWebView

@Composable
fun NewsDetailScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    viewModel: NewsDetailViewModel = hiltViewModel()
) {
    val url = viewModel.url
    val title = viewModel.title

    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(title = title, onNavigationClick = {
                navController.popBackStack()
            })
        },
        contentWindowInsets = WindowInsets(bottom = 0.dp)
    ) { paddingValues ->
        Box(Modifier.padding(paddingValues)) {
            AGWebView(url = url)
        }
    }
}

@Composable
fun TopAppBar(
    modifier: Modifier = Modifier,
    title: String = "Berita",
    onNavigationClick: () -> Unit,
) {
    AGTopAppBar(
        modifier = modifier,
        navigationIcon = { AGTopAppBarDefaultNavigationIcon(onClick = onNavigationClick) }
    ) {
        AGTopAppBarDefaultTitle(text = title)
    }
}