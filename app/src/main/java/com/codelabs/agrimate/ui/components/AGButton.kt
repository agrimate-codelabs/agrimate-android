package com.codelabs.agrimate.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Tune
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.codelabs.agrimate.ui.theme.AgrimateTheme
import com.codelabs.agrimate.ui.theme.Green100
import com.codelabs.agrimate.ui.theme.Green500
import com.codelabs.agrimate.ui.theme.GreyScale400

enum class AGButtonVariant {
    Primary,
    Outlined
}

@Composable
fun AGButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    enabled: Boolean = true,
    containerColor: Color = Green100,
    contentColor: Color = Color.White,
    contentPadding: PaddingValues = PaddingValues(horizontal = 16.dp, vertical = 12.dp),
    shape: Shape = RoundedCornerShape(12.dp),
    content: @Composable (RowScope.() -> Unit),
) {
    Button(
        modifier = modifier,
        onClick = onClick,
        enabled = enabled,
        contentPadding = contentPadding,
        colors = ButtonDefaults.buttonColors(
            containerColor = containerColor,
            contentColor = contentColor
        ),
        shape = shape,
        content = content
    )
}

@Composable
fun AGButtonOutlined(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    enabled: Boolean = true,
    containerColor: Color = GreyScale400,
    borderColor: Color = GreyScale400,
    contentPadding: PaddingValues = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
    content: @Composable (RowScope.() -> Unit),
) {
    OutlinedButton(
        modifier = modifier,
        onClick = onClick,
        enabled = enabled,
        contentPadding = contentPadding,
        colors = ButtonDefaults.outlinedButtonColors(contentColor = containerColor),
        border = BorderStroke(1.dp, borderColor),
        shape = RoundedCornerShape(8.dp),
        content = content,
    )
}

@Composable
fun AGFilterButton(modifier: Modifier = Modifier, onClick: () -> Unit, label: String = "Filter") {
    Surface(
        modifier = modifier,
        shape = RoundedCornerShape(8.dp),
        color = Green500,
        contentColor = Color.White,
        onClick = onClick
    ) {
        Row(
            modifier = Modifier.padding(10.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(imageVector = Icons.Outlined.Tune, contentDescription = null)
            Text(label)
        }
    }
}

@Deprecated(
    "use AGButton/AGButtonOutlined with containerColor, contentColor and without variant params instead",
    ReplaceWith("AGButton, AGButtonOutlined")
)
@Composable
fun AGButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    enabled: Boolean = true,
    contentPadding: PaddingValues = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
    variant: AGButtonVariant,
    content: @Composable (RowScope.() -> Unit),
) {
    when (variant) {
        AGButtonVariant.Primary -> {
            Button(
                modifier = modifier,
                onClick = onClick,
                enabled = enabled,
                contentPadding = contentPadding,
                colors = ButtonDefaults.buttonColors(containerColor = Green100),
                shape = RoundedCornerShape(8.dp),
                content = content
            )
        }

        AGButtonVariant.Outlined -> {
            OutlinedButton(
                modifier = modifier,
                onClick = onClick,
                enabled = enabled,
                contentPadding = contentPadding,
                colors = ButtonDefaults.outlinedButtonColors(contentColor = GreyScale400),
                border = BorderStroke(1.dp, GreyScale400),
                shape = RoundedCornerShape(8.dp),
                content = content,
            )
        }
    }
}

@Composable
@Preview
fun AGButtonPrimaryPreview() {
    AgrimateTheme {
        AGButton(onClick = {}, enabled = true, content = { Text(text = "Button") })
    }
}

@Composable
@Preview
fun AGButtonOutlinedPreview() {
    AgrimateTheme {
        AGButtonOutlined(
            onClick = {},
            enabled = true,
            content = { Text(text = "Button") }
        )
    }
}

@Composable
@Preview
fun AGFilterButtonPreview() {
    AgrimateTheme {
        AGFilterButton(onClick = {})
    }
}