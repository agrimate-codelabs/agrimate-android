package com.codelabs.agrimate.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.codelabs.agrimate.ui.components.model.ExploreMenuModel
import com.codelabs.agrimate.ui.theme.GreyScale400
import com.codelabs.agrimate.ui.theme.GreyScale900

@Composable
fun AGExploreMenu(modifier: Modifier = Modifier, menu: ExploreMenuModel, onClick: () -> Unit) {
    Column(modifier = modifier, horizontalAlignment = Alignment.CenterHorizontally) {
        AGExploreMenuIcon(icon = menu.icon, onClick = onClick)
        Spacer(modifier = Modifier.padding(bottom = 5.16.dp))
        AGExploreMenuLabel(label = menu.label)
    }
}

@Composable
fun AGExploreMenuIcon(modifier: Modifier = Modifier, icon: Int, onClick: () -> Unit) {
    Box(
        modifier = modifier
            .width(68.dp)
            .aspectRatio(1f)
            .border(
                color = GreyScale900,
                width = 0.92.dp,
                shape = RoundedCornerShape(14.65.dp)
            )
            .background(
                Brush.radialGradient(
                    colorStops = arrayOf(
                        Pair(0f, Color(0xFFBFFFF6)),
                        Pair(0.2f, Color(0xFFF5FFCC).copy(alpha = 0.7938f)),
                        Pair(1f, Color.White.copy(alpha = 0f))
                    )
                )
            )
            .clip(RoundedCornerShape(14.65.dp))
            .clickable {
                onClick()
            },
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = icon),
            contentDescription = null
        )
    }
}

@Composable
fun AGExploreMenuLabel(modifier: Modifier = Modifier, label: String) {
    Text(
        modifier = modifier,
        text = label,
        fontSize = 11.sp,
        lineHeight = 19.3.sp,
        color = GreyScale400,
        fontWeight = FontWeight.Medium
    )
}
