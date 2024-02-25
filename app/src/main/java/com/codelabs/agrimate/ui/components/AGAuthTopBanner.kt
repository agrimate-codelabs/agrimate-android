package com.codelabs.agrimate.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import com.codelabs.agrimate.R

@Composable
fun AGAuthTopBanner(modifier: Modifier = Modifier, title: String) {
    Box(modifier = modifier.fillMaxWidth(), contentAlignment = Alignment.TopCenter) {
        Image(
            modifier = Modifier
                .fillMaxWidth()
                .zIndex(1f),
            painter = painterResource(id = R.drawable.signup_layer1_bg),
            contentDescription = null
        )
        Image(
            modifier = Modifier
                .fillMaxWidth()
                .zIndex(2f),
            painter = painterResource(id = R.drawable.signup_layer2_bg),
            contentDescription = null
        )
        Text(
            modifier = Modifier
                .align(Alignment.Center)
                .zIndex(3f), text = title,
            color = Color.White,
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp
        )
    }
}