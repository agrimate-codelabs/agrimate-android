package com.codelabs.agrimate.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.material3.DropdownMenu
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@Composable
fun AGActionDropdown(
    modifier: Modifier = Modifier,
    expanded: Boolean,
    onDismissRequest: () -> Unit,
    content: @Composable (ColumnScope.() -> Unit),
) {
    DropdownMenu(
        modifier = modifier.background(Color.White),
        expanded = expanded,
        onDismissRequest = onDismissRequest
    ) {
        content()
    }
}