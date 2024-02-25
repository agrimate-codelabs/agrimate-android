package com.codelabs.agrimate.screens.farmer.chat

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.codelabs.agrimate.ui.components.AGTopAppBar
import com.codelabs.agrimate.ui.components.AGTopAppBarDefaultNavigationIcon
import com.codelabs.agrimate.ui.components.AGTopAppBarUserAvatarTitle
import com.codelabs.agrimate.ui.components.chat.AGBubbleMessageRecipient
import com.codelabs.agrimate.ui.components.chat.AGBubbleMessageSender
import com.codelabs.agrimate.ui.components.chat.AGChatInputText

@Composable
fun UserChatScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    viewModel: UserChatViewModel = hiltViewModel()
) {
    val message by viewModel.message.collectAsStateWithLifecycle()
    Scaffold(
        modifier = modifier.imePadding(),
        contentWindowInsets = WindowInsets(0.dp),
        topBar = {
            TopAppBar(onNavigationClick = {
                navController.popBackStack()
            })
        },
        bottomBar = {
            AGChatInputText(
                modifier = Modifier.imePadding(),
                message = message,
                onMessageChange = viewModel::onMessageChange
            )
        }
    ) { paddingValues ->
        UserChatContent(modifier = Modifier.padding(paddingValues))
    }
}

@Composable
fun UserChatContent(modifier: Modifier = Modifier) {
    Box(modifier = Modifier.fillMaxHeight(), contentAlignment = Alignment.BottomCenter) {
        LazyColumn(
            modifier = modifier
                .fillMaxWidth(),
            contentPadding = PaddingValues(18.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp),
        ) {
            repeat(10) {
                items(1) {
                    AGBubbleMessageRecipient()
                }
                items(1) {
                    AGBubbleMessageSender()
                }
            }
        }
    }
}

@Composable
private fun TopAppBar(onNavigationClick: () -> Unit) {
    AGTopAppBar(navigationIcon = { AGTopAppBarDefaultNavigationIcon(onClick = onNavigationClick) }) {
        AGTopAppBarUserAvatarTitle(user = "Minariyah Rina", role = "Pengepul")
    }
}