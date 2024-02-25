package com.codelabs.agrimate.ui.components

import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.MenuDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.codelabs.agrimate.ui.theme.GreyScale500

@Composable
fun AGDropdownMenuItem(
    modifier: Modifier = Modifier,
    text: String,
    onClick: () -> Unit,
    leadingIcon: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
    enabled: Boolean = true,
    contentColor: Color = GreyScale500
) {
    DropdownMenuItem(
        modifier = modifier,
        text = { Text(text = text) },
        onClick = onClick,
        leadingIcon = leadingIcon,
        trailingIcon = trailingIcon,
        enabled = enabled,
        colors = MenuDefaults.itemColors(
            textColor = contentColor,
            leadingIconColor = contentColor
        )
    )
}