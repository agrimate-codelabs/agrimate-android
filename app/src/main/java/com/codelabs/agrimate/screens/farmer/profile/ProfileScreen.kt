package com.codelabs.agrimate.screens.farmer.profile

import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material.icons.outlined.PrivacyTip
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import com.codelabs.agrimate.R
import com.codelabs.agrimate.ui.common.menu.farmerNavBarMenu
import com.codelabs.agrimate.ui.components.AGButton
import com.codelabs.agrimate.ui.components.AGLabelChip
import com.codelabs.agrimate.ui.components.AGNavBarMenuItem
import com.codelabs.agrimate.ui.components.AGNavbar
import com.codelabs.agrimate.ui.navigation.AGRoute
import com.codelabs.agrimate.ui.theme.GreyScale200
import com.codelabs.agrimate.ui.theme.GreyScale700
import com.codelabs.agrimate.ui.theme.Red500
import com.codelabs.agrimate.ui.theme.Secondary100
import com.codelabs.core.utils.Resource

@Composable
fun ProfileScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    viewModel: ProfileViewModel = hiltViewModel()
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    val context = LocalContext.current

    val isLogout = viewModel.isLogout.collectAsStateWithLifecycle().value
    val uiState = viewModel.uiState.collectAsStateWithLifecycle()

    val navigateToAuth = {
        navController.navigate(AGRoute.Auth.route) {
            popUpTo(0) {
                inclusive = true
            }
        }
    }

    LaunchedEffect(Unit) {
        viewModel.toastMessage.collect { message ->
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
        }
    }

    when (isLogout) {
        is Resource.Error -> {

        }
        is Resource.Loading -> {
            Dialog(onDismissRequest = { }) {
                CircularProgressIndicator()
            }
        }

        is Resource.Success -> {
            if (isLogout.data == true) {
                LaunchedEffect(Unit) {
                    navigateToAuth()
                }
            }
        }
    }

    Scaffold(
        modifier = modifier,
        contentWindowInsets = WindowInsets(0),
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
        ProfileContent(
            modifier = modifier
                .padding(paddingValues)
                .verticalScroll(rememberScrollState()),
            onLogoutClick = viewModel::logout,
            uiState = uiState.value
        )
    }
}

@Composable
private fun ProfileContent(
    modifier: Modifier = Modifier,
    onLogoutClick: () -> Unit,
    uiState: ProfileUiState
) {
    val imageHeight by remember { mutableStateOf(116.dp) }
    val overlapContent by remember { derivedStateOf { imageHeight / 2 * -1 } }

    Box(modifier = modifier.fillMaxWidth()) {
        Column(
            modifier = Modifier,
            verticalArrangement = Arrangement.spacedBy(overlapContent)
        ) {
            Box(modifier = Modifier) {
                Image(
                    modifier = Modifier.fillMaxWidth(),
                    painter = painterResource(id = R.drawable.profile_bg_layer),
                    contentDescription = null,
                    contentScale = ContentScale.FillWidth
                )
                Text(
                    text = "Profile",
                    modifier = Modifier.padding(top = 65.dp, start = 18.dp),
                    fontSize = 18.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.White
                )
            }
            AnimatedVisibility(
                visible = true,
                enter = EnterTransition.None,
                exit = ExitTransition.None
            ) {
                Column {
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Surface(shape = RoundedCornerShape(100.dp), shadowElevation = 6.dp) {
                            Image(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(100))
                                    .size(imageHeight)
                                    .aspectRatio(1f)
                                    .border(
                                        width = 6.dp,
                                        color = Color.White,
                                        shape = RoundedCornerShape(100)
                                    ),
                                painter = painterResource(id = R.drawable.avatar_default),
                                contentDescription = uiState.username,
                                contentScale = ContentScale.Crop
                            )
                        }
                        Spacer(modifier = Modifier.padding(bottom = 11.dp))
                        Text(
                            text = uiState.username,
                            fontWeight = FontWeight.Bold,
                            color = Color.Black,
                            fontSize = 18.sp,
                            lineHeight = 25.2.sp
                        )
                        Spacer(modifier = Modifier.padding(bottom = 8.dp))
                        AGLabelChip(
                            text = uiState.role,
                            contentPadding = PaddingValues(
                                horizontal = 20.dp,
                                vertical = 8.5.dp
                            ),
                            compact = true,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                    Spacer(modifier = Modifier.padding(bottom = 49.dp))
                    Column(
                        modifier = Modifier.padding(start = 8.dp, end = 8.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        ProfileMenuItem(
                            modifier = Modifier,
                            icon = Icons.Outlined.Lock,
                            label = "Edit Profile",
                            onClick = {})
                        ProfileMenuItem(
                            modifier = Modifier,
                            icon = Icons.Outlined.Lock,
                            label = "Ganti Password",
                            onClick = {})
                        ProfileMenuItem(
                            modifier = Modifier,
                            icon = Icons.Outlined.Settings,
                            label = "Pengaturan",
                            onClick = {})
                        ProfileMenuItem(
                            modifier = Modifier,
                            icon = Icons.Outlined.PrivacyTip,
                            label = "Edit Kebijakan Privasi",
                            onClick = {})
                    }
                    Spacer(modifier = Modifier.padding(bottom = 21.dp))
                    Box(modifier = Modifier.padding(horizontal = 16.dp)) {
                        AGButton(
                            onClick = onLogoutClick,
                            modifier = Modifier
                                .fillMaxWidth()
                                .shadow(elevation = 15.dp, spotColor = Color(0xFFF54336)),
                            contentPadding = PaddingValues(vertical = 19.dp, horizontal = 16.dp),
                            shape = RoundedCornerShape(8.dp),
                            containerColor = Red500
                        ) {
                            Text("Logout", fontSize = 16.sp, fontWeight = FontWeight.Bold)
                        }
                    }
                    Spacer(modifier = Modifier.padding(bottom = 29.dp))
                }
            }
        }
    }
}

@Composable
private fun ProfileMenuItem(
    modifier: Modifier,
    icon: ImageVector,
    label: String,
    onClick: () -> Unit,
) {
    Surface(
        modifier = modifier.shadow(22.dp, spotColor = Color(0x0D000000)),
        color = Color.White,
        shape = RoundedCornerShape(8.dp),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { onClick() }
                .padding(14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(100))
                    .background(Color(0XFFF1F8FE), RoundedCornerShape(100))
                    .padding(10.dp)
            ) {
                Icon(
                    modifier = Modifier.size(24.dp),
                    imageVector = icon,
                    contentDescription = null,
                    tint = Secondary100
                )
            }
            Spacer(modifier = Modifier.padding(end = 14.dp))
            Text(
                label,
                modifier = Modifier.weight(1f),
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = GreyScale200
            )
            Icon(
                modifier = Modifier
                    .size(29.dp)
                    .aspectRatio(1f),
                imageVector = Icons.Default.ChevronRight,
                contentDescription = null,
                tint = GreyScale700,
            )
        }
    }
}