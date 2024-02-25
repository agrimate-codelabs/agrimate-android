package com.codelabs.agrimate.ui.components.maps

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun AGMapsMarkerController(
    modifier: Modifier = Modifier,
    onAddClick: () -> Unit,
    onClearClick: () -> Unit,
    addLabel: String = "Tambah Titik",
    clearLabel: String = "Bersihkan Titik"
) {
    Row(
        modifier = modifier
            .padding(8.dp)
            .shadow(8.dp, RoundedCornerShape(12.dp))
            .background(Color.White),
        horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        TextButton(
            onClick = onAddClick,
            modifier = Modifier
                .padding(4.dp)
                .weight(1f),
            shape = RoundedCornerShape(12.dp)
        ) {
            Text(text = addLabel, fontSize = 12.sp, color = Color.Black)
        }
        TextButton(
            onClick = onClearClick,
            modifier = Modifier
                .padding(4.dp)
                .weight(1f),
            shape = RoundedCornerShape(12.dp)
        ) {
            Text(text = clearLabel, fontSize = 12.sp, color = Color.Black)
        }
    }
}