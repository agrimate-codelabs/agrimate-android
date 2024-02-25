package com.codelabs.agrimate.ui.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.codelabs.agrimate.ui.theme.Green100

@Composable
fun AGLabelChip(
    modifier: Modifier = Modifier,
    text: String,
    color: Color = Green100,
    contentColor: Color = Color.White,
    shape: Shape = RoundedCornerShape(100),
    compact: Boolean = false,
    contentPadding: PaddingValues = PaddingValues(horizontal = 10.dp, vertical = 4.dp),
    fontWeight: FontWeight = FontWeight.Medium,
) {
    Surface(
        modifier = modifier.defaultMinSize(minWidth = if (compact) 0.dp else 113.dp),
        shape = shape,
        color = color
    ) {
        Text(
            modifier = Modifier.padding(contentPadding),
            text = text, style = TextStyle(
                fontSize = 12.sp,
                lineHeight = 21.3.sp,
                fontWeight = fontWeight,
                color = contentColor,
                textAlign = TextAlign.Center,
            )
        )
    }
}