package com.codelabs.agrimate.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import com.codelabs.agrimate.R
import com.codelabs.agrimate.ui.theme.AgrimateTheme
import com.codelabs.agrimate.ui.theme.Red2

@Composable
fun AGDisinfectantRecommendationCard(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .background(
                color = Color(0XFFFCEDEA),
                shape = RoundedCornerShape(size = 12.dp)
            )
            .clip(RoundedCornerShape(12.dp))
    ) {
        Image(
            modifier = Modifier
                .align(Alignment.TopEnd)
                .zIndex(-1f),
            painter = painterResource(id = R.drawable.quarter_ellipse_layer),
            contentDescription = null,
            colorFilter = ColorFilter.tint(Color(0xFFFADEDB))
        )
        Image(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .zIndex(-1f),
            painter = painterResource(id = R.drawable.dot_layer),
            contentDescription = null,
            colorFilter = ColorFilter.tint(Color(0xFFFADEDB))
        )
        Row(
            modifier = Modifier
                .padding(start = 16.dp, top = 16.dp, bottom = 16.dp, end = 24.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_weather_heavy_showers_line),
                contentDescription = null,
                tint = Red2
            )
            Column {
                Text(
                    "Tidak disarankan memberi disinfektan pada hari ini", style = TextStyle(
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF201C1C),
                    )
                )
                Spacer(Modifier.padding(bottom = 2.dp))
                Text(
                    "Aktivitas bisa dimu", style = TextStyle(
                        fontSize = 12.sp,
                        lineHeight = 21.3.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color(0xFF494343),
                    )
                )
            }
        }
    }
}

@Composable
@Preview
fun AGDisinfectantRecommendationCardPreview() {
    AgrimateTheme {
        AGDisinfectantRecommendationCard()
    }
}