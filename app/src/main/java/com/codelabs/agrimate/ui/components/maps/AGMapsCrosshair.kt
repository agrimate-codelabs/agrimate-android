package com.codelabs.agrimate.ui.components.maps

import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.codelabs.agrimate.R

@Composable
fun AGMapsCrosshair(modifier: Modifier) {
    Icon(
        modifier = modifier
            .size(16.dp),
        painter = painterResource(id = R.drawable.control_point_ic),
        contentDescription = null,
        tint = Color.Black,
    )
}