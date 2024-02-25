package com.codelabs.agrimate.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Refresh
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.codelabs.agrimate.R
import com.codelabs.agrimate.ui.theme.AgrimateTheme

@Composable
fun AGWeatherInfo(modifier: Modifier = Modifier, onRefresh: () -> Unit) {
    Box(
        modifier = modifier
            .shadow(
                elevation = 40.dp,
                spotColor = Color(0xFF04332E),
                ambientColor = Color(0xFF04332E)
            )
            .border(
                width = 1.dp,
                color = Color(0xFF0F5D53),
                shape = RoundedCornerShape(size = 12.dp)
            )
            .background(
                brush = Brush.linearGradient(
                    colorStops = arrayOf(
                        Pair(0.1f, Color(0xFF0D4941)),
                        Pair(0.5f, Color(0xFF164943)),
                        Pair(0.9f, Color(0xFF0D564D)),
                    )
                ),
                shape = RoundedCornerShape(size = 12.dp)
            )
            .padding(24.dp)
    ) {
        Column(verticalArrangement = Arrangement.spacedBy(24.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = "Senin, 20 Desember 2021", style = TextStyle(
                        color = Color.White,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold
                    )
                )
                Text(
                    text = "3.30 PM", style = TextStyle(
                        color = Color.White,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold
                    )
                )
            }
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Image(
                    modifier = Modifier
                        .width(64.dp)
                        .height(64.dp),
                    painter = painterResource(R.drawable.weather_partly_cloudy),
                    contentDescription = null,
                    contentScale = ContentScale.Crop
                )
                Column {
                    Text(
                        text = "18°C", style = TextStyle(
                            color = Color.White,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Normal,
                        )
                    )
                    Spacer(Modifier.padding(bottom = 4.dp))
                    Text(
                        text = "Hujan Berawan", style = TextStyle(
                            color = Color.White,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.SemiBold,
                        )
                    )
                }
            }
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = " Terakhir update 3.00 PM", style = TextStyle(
                        color = Color.White,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Normal,
                    )
                )
                Spacer(Modifier.padding(end = 4.dp))
                Icon(
                    modifier = Modifier
                        .size(16.dp)
                        .clickable { onRefresh() },
                    imageVector = Icons.Outlined.Refresh,
                    contentDescription = "Refresh",
                    tint = Color.White
                )
            }
        }
    }
}

@Composable
@Preview
fun AGWeatherInfoPreview() {
    AgrimateTheme {
        AGWeatherInfo(onRefresh = {})
    }
}