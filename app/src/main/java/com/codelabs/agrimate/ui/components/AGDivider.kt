package com.codelabs.agrimate.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.codelabs.agrimate.ui.theme.GreyScale900

@Composable
fun AGDivider(modifier: Modifier = Modifier) {
    Divider(modifier = modifier.fillMaxWidth(), thickness = 8.dp, color = GreyScale900)
}