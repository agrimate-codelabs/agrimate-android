package com.codelabs.agrimate.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.codelabs.agrimate.ui.theme.GreyScale1000
import com.codelabs.agrimate.ui.theme.GreyScale200
import com.codelabs.agrimate.ui.theme.GreyScale600
import com.codelabs.agrimate.ui.theme.GreyScale700

@Composable
fun AGFilledCardWithAction(
    modifier: Modifier = Modifier,
    title: String,
    description: String,
    trailing: @Composable (() -> Unit)? = null,
    onClick: () -> Unit,
) {
    Surface(
        modifier = modifier,
        border = BorderStroke(1.dp, GreyScale700),
        shape = RoundedCornerShape(12.dp),
        onClick = onClick,
        color = GreyScale1000,
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp, vertical = 8.5.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(Modifier.weight(1f)) {
                Text(
                    title, style = TextStyle(
                        fontSize = 18.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = GreyScale200,
                    ),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(Modifier.padding(bottom = 8.dp))
                Text(
                    description, style = TextStyle(
                        fontSize = 11.sp,
                        lineHeight = 19.3.sp,
                        fontWeight = FontWeight.Medium,
                        color = GreyScale600,
                    ),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
            if (trailing != null) {
                trailing()
            }
        }
    }
}