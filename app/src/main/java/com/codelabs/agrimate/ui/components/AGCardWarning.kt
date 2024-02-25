package com.codelabs.agrimate.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
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

@Composable
fun AGCardWarning(modifier: Modifier = Modifier, message: String) {
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
            Text(
                modifier = Modifier.width(278.dp),
                text = message,
                style = TextStyle(
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF201C1C),
                )
            )
        }
    }
}

@Composable
@Preview
fun AGCardWarningPreview() {
    AgrimateTheme {
        AGCardWarning(message = "Pahami kebutuhan tanaman dan kondisi tanah dengan  memberikan nutrisi serta air yang sesuai!")
    }
}