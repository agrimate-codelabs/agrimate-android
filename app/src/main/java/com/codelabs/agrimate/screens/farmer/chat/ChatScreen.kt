package com.codelabs.agrimate.screens.farmer.chat

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Chat
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import com.codelabs.agrimate.ui.common.menu.farmerNavBarMenu
import com.codelabs.agrimate.ui.components.AGNavBarMenuItem
import com.codelabs.agrimate.ui.components.AGNavbar
import com.codelabs.agrimate.ui.components.AGTopAppBar
import com.codelabs.agrimate.ui.components.AGTopAppBarDefaultTitle
import com.codelabs.agrimate.ui.components.chat.AGChatCard
import com.codelabs.agrimate.ui.navigation.AGRoute
import com.codelabs.agrimate.ui.theme.Green100

@Composable
fun ChatScreen(modifier: Modifier = Modifier, navController: NavController) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    Scaffold(
        modifier = modifier,
        topBar = { TopAppBar() },
        contentWindowInsets = WindowInsets(0),
        floatingActionButton = {
            AddNewChat(onClick = {})
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
        ChatContent(
            modifier = Modifier.padding(paddingValues),
            onChatCardClick = { navController.navigate(AGRoute.Farmer.Main.Chat.User.route) })
    }
}

@Composable
fun ChatContent(modifier: Modifier = Modifier, onChatCardClick: (String) -> Unit) {
    LazyColumn(
        modifier = modifier.fillMaxWidth(),
        contentPadding = PaddingValues(14.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        items(10) {
            AGChatCard(onClick = { onChatCardClick("chatid") })
        }
    }
}

@Composable
private fun TopAppBar(modifier: Modifier = Modifier) {
    AGTopAppBar(
        modifier = modifier,
    ) {
        AGTopAppBarDefaultTitle(text = "Chat")
    }
}

@Composable
private fun AddNewChat(modifier: Modifier = Modifier, onClick: () -> Unit) {
    Surface(
        modifier = modifier, shape = RoundedCornerShape(100),
        color = Color.White,
        onClick = onClick,
        shadowElevation = 4.dp
    ) {
        Box(Modifier.padding(16.dp)) {
            Icon(imageVector = Icons.Outlined.Chat, contentDescription = "add", tint = Green100)
        }
    }
}