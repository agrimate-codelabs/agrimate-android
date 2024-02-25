package com.codelabs.agrimate.ui.components.chat

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccessTimeFilled
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.codelabs.agrimate.ui.theme.AgrimateTheme

@Composable
fun AGBubbleMessageRecipient(modifier: Modifier = Modifier) {
    val configuration = LocalConfiguration.current
    val maxWidth by remember { derivedStateOf { configuration.screenWidthDp - (30 * configuration.screenWidthDp / 100) } }

    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Surface(
            modifier = Modifier
                .widthIn(20.dp, maxWidth.dp),
            color = Color(0xFFF5F5F5),
            shape = RoundedCornerShape(
                topStart = 20.dp, topEnd = 20.dp, bottomEnd = 20.dp, bottomStart = 0.dp
            )
        ) {
            Text(
                modifier = Modifier.padding(12.dp),
                text = "Halo, ada yang bisa dibantu?",
                color = Color(0XFF262626),
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium
            )
        }
        AGBubbleMessageTime(modifier = Modifier.widthIn(20.dp, maxWidth.dp))
    }
}

@Composable
fun AGBubbleMessageSender(modifier: Modifier = Modifier) {
    val configuration = LocalConfiguration.current
    val maxWidth by remember { derivedStateOf { configuration.screenWidthDp - (30 * configuration.screenWidthDp / 100) } }

    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.End,
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Surface(
            modifier = Modifier
                .widthIn(20.dp, maxWidth.dp),
            color = Color(0xFF14796C),
            shape = RoundedCornerShape(
                topStart = 20.dp, topEnd = 20.dp, bottomStart = 20.dp, bottomEnd = 0.dp
            )
        ) {
            Text(
                modifier = Modifier.padding(12.dp),
                text = "Saya memiliki beberapa komoditas hasil panen. Untuk jagung, berapa harga jual per kilonya?",
                color = Color.White,
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium
            )
        }
        AGBubbleMessageTime(modifier = Modifier.widthIn(20.dp, maxWidth.dp))
    }
}

@Composable
fun AGBubbleMessageTime(modifier: Modifier = Modifier) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(1.5.dp)
    ) {
        Icon(
            modifier = Modifier.size(12.dp),
            imageVector = Icons.Filled.AccessTimeFilled,
            contentDescription = null,
            tint = Color(0xFF989898)
        )
        Text(
            text = "1 Menit yang lalu",
            color = Color(0XFF989898),
            fontSize = 12.sp,
            fontWeight = FontWeight.Normal
        )
    }
}

@Preview
@Composable
fun AGBubbleMessageRecipientPreview() {
    AGBubbleMessageRecipient()
}

@Preview
@Composable
fun AGBubbleMessageSenderPreview() {
    AGBubbleMessageSender()
}

@Preview(showSystemUi = true)
@Composable
fun BubbleMessageOnChatPreview() {
    AgrimateTheme {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(18.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            AGBubbleMessageRecipient()
            AGBubbleMessageSender()
        }
    }
}