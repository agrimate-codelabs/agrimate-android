package com.codelabs.agrimate.screens.farmer.capital

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.codelabs.agrimate.ui.components.AGInputBox
import com.codelabs.agrimate.ui.components.AGTopAppBar
import com.codelabs.agrimate.ui.components.AGTopAppBarDefaultNavigationIcon
import com.codelabs.agrimate.ui.components.AGTopAppBarDefaultTitle
import com.codelabs.agrimate.ui.components.card.AGCapitalCard
import com.codelabs.agrimate.ui.theme.AgrimateTheme

@Composable
fun CapitalScreen(modifier: Modifier = Modifier, navController: NavController) {
    Scaffold(modifier = modifier, topBar = {
        TopAppBar(onNavigationClick = {
            navController.popBackStack()
        })
    }) { paddingValues ->
        CapitalContentScreen(modifier = Modifier.padding(paddingValues))
    }
}

@Composable
fun CapitalContentScreen(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.padding(horizontal = 18.dp, vertical = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        AGInputBox(modifier = Modifier.fillMaxWidth(), text = "Ajukan Pemodalan", onClick = {})
        LazyColumn(modifier = Modifier, verticalArrangement = Arrangement.spacedBy(16.dp)) {
            items(3) {
                AGCapitalCard(modifier = Modifier.fillMaxWidth())
            }
        }
    }
}

@Composable
private fun TopAppBar(onNavigationClick: () -> Unit) {
    AGTopAppBar(navigationIcon = { AGTopAppBarDefaultNavigationIcon(onClick = onNavigationClick) }) {
        AGTopAppBarDefaultTitle(text = "Pemodalan")
    }
}

@Preview(showSystemUi = true)
@Composable
fun CapitalContentScreenPreview() {
    AgrimateTheme {
        CapitalContentScreen()
    }
}