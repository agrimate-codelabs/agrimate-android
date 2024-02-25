package com.codelabs.agrimate.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowRight
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
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
import com.codelabs.agrimate.ui.theme.Green100

@Composable
fun AGWeatherDayCard(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(Color.White)
            .border(width = 1.dp, color = Color.White)
            .clickable { }
            .shadow(
                elevation = 22.dp,
                spotColor = Color(0x0D000000),
                ambientColor = Color(0x0D000000)
            )
            .padding(16.dp)
    ) {
        Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
            Image(modifier = Modifier.width(40.dp).height(40.dp), painter = painterResource(id = R.drawable.weather_partly_cloudy), contentDescription = null, contentScale = ContentScale.Crop)
            Spacer(Modifier.padding(end = 16.dp))
            Column(modifier = Modifier) {
                Text(
                    text = "Senin", style = TextStyle(
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF201C1C),
                    )
                )
                Text(
                    text = "Hujan Petir", style = TextStyle(
                        fontSize = 12.sp,
                        lineHeight = 21.3.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color(0xFF494343),
                    )
                )
            }
            Spacer(Modifier.weight(1f))
            Text(
                text = "17° C", style = TextStyle(
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF201C1C),
                )
            )
            Spacer(Modifier.padding(end = 15.dp))
            IconButton(onClick = { /*TODO*/ }) {
                Icon(
                    imageVector = Icons.Default.ArrowRight,
                    contentDescription = "lihat detail",
                    tint = Green100
                )
            }
        }
    }
}

@Composable
@Preview
fun AGWeatherDayCardPreview() {
    AgrimateTheme {
        AGWeatherDayCard()
    }
}