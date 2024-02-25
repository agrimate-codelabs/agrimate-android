package com.codelabs.agrimate.screens.farmer.discussion

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.codelabs.agrimate.ui.components.AGTopAppBar
import com.codelabs.agrimate.ui.components.AGTopAppBarDefaultActionButton
import com.codelabs.agrimate.ui.components.AGTopAppBarDefaultNavigationIcon
import com.codelabs.agrimate.ui.components.AGTopAppBarDefaultTitle
import com.codelabs.agrimate.ui.components.card.AGDiscussionCard

@Composable
fun DiscussionScreen(modifier: Modifier = Modifier, navController: NavController) {
    Scaffold(modifier = modifier, topBar = {
        TopAppBar(onNavigationClick = {
            navController.popBackStack()
        },
            onNotificationClick = {}
        )
    }) { paddingValues ->
        DiscussionContent(modifier = Modifier.padding(paddingValues))
    }
}

@Composable
private fun DiscussionContent(modifier: Modifier = Modifier) {
    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(horizontal = 18.dp, vertical = 16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(3) {
            AGDiscussionCard(
                modifier = Modifier.fillMaxWidth(),
                name = "Asep Muhaimin",
                createdAt = "14 jam yang lalu",
                postTitle = "Cara tanam cabe buat pemula?",
                postDescription = "misi agan agan, ini saya udah nanam cabe tapi kok selalu gagal tumbuh ya? padahal udah sering disirami dan saya coba kasih vitamin tapi selalu gagal, minta penjelasannya dong",
                postCommentCount = 12,
                postLikeCount = 50,
            )
        }
    }
}

@Composable
private fun TopAppBar(onNavigationClick: () -> Unit, onNotificationClick: () -> Unit) {
    AGTopAppBar(
        navigationIcon = { AGTopAppBarDefaultNavigationIcon(onClick = onNavigationClick) },
        trailingContent = {
            AGTopAppBarDefaultActionButton(onClick = onNotificationClick) {
                Icon(
                    imageVector = Icons.Outlined.Notifications,
                    contentDescription = "notifikasi",
                    tint = Color.White
                )
            }
        }) {
        AGTopAppBarDefaultTitle(text = "Diskusi")
    }
}