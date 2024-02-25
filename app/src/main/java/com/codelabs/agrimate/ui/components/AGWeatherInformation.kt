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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import com.codelabs.agrimate.R
import com.codelabs.agrimate.ui.theme.AgrimateTheme
import com.codelabs.agrimate.ui.theme.Green300
import com.codelabs.agrimate.ui.theme.Green400

@Composable
fun AGWeatherInformation(modifier: Modifier = Modifier, onClick: () -> Unit) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .border(
                width = 1.dp,
                color = Green300,
                shape = RoundedCornerShape(20.dp)
            )
            .background(color = Green400, shape = RoundedCornerShape(20.dp))
    ) {
        Icon(
            modifier = Modifier
                .zIndex(2f)
                .align(Alignment.TopEnd)
                .padding(end = 14.dp, top = 16.dp)
                .clickable { onClick() }
                ,
            painter = painterResource(id = R.drawable.arrow_right_circle),
            contentDescription = null,
            tint = Color.White
        )
        Row(
            modifier = Modifier.padding(vertical = 17.dp, horizontal = 21.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                modifier = Modifier
                    .width(50.72.dp)
                    .height(61.78.dp),
                painter = painterResource(id = R.drawable.weather_partly_cloudy),
                contentDescription = null,
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.padding(end = 16.dp))
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(
                    text = "18°C",
                    color = Color.White,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "3.30 PM | Hujan Berawan",
                    color = Color.White,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "Tidak disarankan menyemprotkan disinfektan",
                    color = Color.White,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Medium,
                    lineHeight = 21.3.sp
                )
            }
        }
    }
}

@Preview
@Composable
fun WeatherInformationPreview() {
    AgrimateTheme {
        AGWeatherInformation(onClick = {})
    }
}