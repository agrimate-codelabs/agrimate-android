package com.codelabs.agrimate.ui.components

import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.codelabs.agrimate.ui.theme.GreyScale100
import com.codelabs.agrimate.ui.theme.GreyScale1000

@Composable
fun AGTextOverview(
    modifier: Modifier = Modifier,
    text: String,
    shape: RoundedCornerShape = RoundedCornerShape(12.dp),
    contentPadding: PaddingValues = PaddingValues(horizontal = 12.dp, vertical = 17.dp),
) {
    Surface(
        modifier = modifier,
        shape = shape,
        color = GreyScale1000
    ) {
        Text(
            text,
            modifier = Modifier.padding(contentPadding),
            style = TextStyle(
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = GreyScale100,
            )
        )
    }
}

@Composable
fun AGTextWithPrefix(modifier: Modifier = Modifier, text: String, prefix: String) {
    Row(modifier = modifier.height(IntrinsicSize.Min)) {
        Surface(
            modifier = Modifier
                .fillMaxHeight(),
            color = Color(0xFFEAEAEA),
            shape = RoundedCornerShape(topStart = 12.dp, bottomStart = 12.dp)
        ) {
            Text(
                prefix,
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 17.dp),
                style = TextStyle(
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color(0xFF808080),
                )
            )
        }
        AGTextOverview(
            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth(), text = text,
            contentPadding = PaddingValues(horizontal = 12.dp, vertical = 17.dp),
            shape = RoundedCornerShape(topEnd = 12.dp, bottomEnd = 12.dp)
        )
    }
}