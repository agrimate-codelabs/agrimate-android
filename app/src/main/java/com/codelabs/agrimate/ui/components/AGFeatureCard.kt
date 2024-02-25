package com.codelabs.agrimate.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.codelabs.agrimate.ui.theme.GreyScale100
import com.codelabs.agrimate.ui.theme.GreyScale500
import com.codelabs.agrimate.ui.theme.GreyScale900

@Composable
fun AGFeatureCard(modifier: Modifier = Modifier, onClick: () -> Unit, icon: Int, title: String, description: String, iconSpace: Dp = 24.dp) {
    Column(
        modifier = modifier
            .shadow(
                elevation = 22.dp,
                spotColor = Color(0x0D000000),
                ambientColor = Color(0x0D000000)
            )
            .border(
                width = 1.dp,
                color = GreyScale900,
                shape = RoundedCornerShape(size = 8.dp)
            )
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(11.dp),
    ) {
        Image(
            painter = painterResource(id = icon),
            contentDescription = null
        )
        Spacer(modifier = Modifier.padding(bottom = iconSpace))
        Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
            Text(
                text = title, style = TextStyle(
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = GreyScale100,
                )
            )
            Text(
                text = description,
                maxLines = 3,
                overflow = TextOverflow.Ellipsis,
                style = TextStyle(
                    fontSize = 12.sp,
                    lineHeight = 21.3.sp,
                    fontWeight = FontWeight.Medium,
                    color = GreyScale500,
                ),
            )
        }
    }
}