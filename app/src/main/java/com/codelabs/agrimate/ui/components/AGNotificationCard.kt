package com.codelabs.agrimate.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.codelabs.agrimate.R
import com.codelabs.agrimate.ui.theme.AgrimateTheme
import com.codelabs.agrimate.ui.theme.GreyScale200
import com.codelabs.agrimate.ui.theme.GreyScale300
import com.codelabs.agrimate.ui.theme.GreyScale400
import com.codelabs.agrimate.ui.theme.Primary100

@Composable
fun AGNotificationCard(modifier: Modifier = Modifier) {
    Box(modifier = modifier
        .fillMaxWidth()
        .clickable {}
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 25.dp, vertical = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Image(
                modifier = Modifier.size(45.dp),
                painter = painterResource(R.drawable.icon_notif_info),
                contentDescription = null
            )
            Column(modifier = Modifier.weight(1f)) {
                Row(
                    modifier = Modifier,
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        modifier = Modifier.weight(1f),
                        text = "Notif Title",
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        style = TextStyle(
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            color = GreyScale200,
                        )
                    )
                    Text(
                        modifier = Modifier, text = "•", style = TextStyle(
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            color = Primary100,
                        )
                    )
                    Text(
                        modifier = Modifier, text = "pukul 23.00",
                        style = TextStyle(
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            color = Primary100,
                        ),
                    )
                }
                Spacer(Modifier.padding(bottom = 4.dp))
                Text(
                    "Lorem ipsum dolor sit amet amet",
                    modifier = Modifier,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    style = TextStyle(
                        fontSize = 12.sp,
                        lineHeight = 21.3.sp,
                        fontWeight = FontWeight.Medium,
                        color = GreyScale400,
                    )
                )
            }
            IconButton(onClick = { /*TODO*/ }) {
                Icon(
                    imageVector = Icons.Default.MoreVert,
                    contentDescription = null,
                    tint = GreyScale300
                )
            }
        }
    }
}

@Composable
fun AGNotificationSectionTitle(text: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 18.dp, vertical = 10.dp)
    ) {
        Text(
            text, style = TextStyle(
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = GreyScale200,
            )
        )
    }
}

@Composable
@Preview
fun AGNotificationCardPreview() {
    AgrimateTheme {
        AGNotificationCard()
    }
}