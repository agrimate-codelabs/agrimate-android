package com.codelabs.agrimate.screens.shared.signup

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.codelabs.agrimate.ui.components.AGButton
import com.codelabs.agrimate.ui.components.AGTopAppBar
import com.codelabs.agrimate.ui.components.AGTopAppBarDefaultNavigationIcon
import com.codelabs.agrimate.ui.components.AGTopAppBarDefaultTitle
import com.codelabs.agrimate.ui.navigation.AGRoute
import com.codelabs.agrimate.ui.theme.GreyScale200
import com.codelabs.agrimate.ui.theme.GreyScale500

@Deprecated("The SigunpRoleChooserScreen not used")
@Composable
fun SignupRoleChooserSccreen(modifier: Modifier = Modifier, navController: NavController) {
    Scaffold(
        modifier = modifier,
        topBar = { TopAppBar(onNavigationClick = { navController.popBackStack() }) }) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .padding(horizontal = 18.dp, vertical = 14.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(21.dp)
        ) {
            CardRole(
                title = "Daftar sebagai petani",
                description = "Fitur rekomendasi untuk bantu tanam panen",
                onClick = {
                    navController.navigate(AGRoute.Auth.SignUp.Farmer.route)
                })
            CardRole(
                title = "Daftar sebagai pengepul",
                description = "Fitur komoditas, harga jual, dan negosiasi",
                onClick = {
                    navController.navigate(AGRoute.Auth.SignUp.Collector.route)
                })
        }
    }
}

@Composable
private fun CardRole(
    modifier: Modifier = Modifier,
    title: String,
    description: String,
    onClick: () -> Unit,
) {
    Surface(
        modifier = modifier.shadow(22.dp, spotColor = Color(0x0D000000)),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp)
        ) {
            Text(
                text = title,
                fontWeight = FontWeight.Bold,
                color = GreyScale200,
                fontSize = 16.sp,
                lineHeight = 27.2.sp
            )
            Text(
                text = description,
                fontWeight = FontWeight.Medium,
                color = GreyScale500,
                fontSize = 14.sp,
                lineHeight = 27.3.sp
            )
            Spacer(modifier = Modifier.padding(bottom = 30.dp))
            AGButton(onClick = { onClick() }, modifier = Modifier.fillMaxWidth()) {
                Text("Daftar")
            }
        }
    }
}

@Composable
private fun TopAppBar(modifier: Modifier = Modifier, onNavigationClick: () -> Unit) {
    AGTopAppBar(
        modifier = modifier,
        navigationIcon = { AGTopAppBarDefaultNavigationIcon(onClick = onNavigationClick) }) {
        AGTopAppBarDefaultTitle(text = "Pilih Role")
    }
}