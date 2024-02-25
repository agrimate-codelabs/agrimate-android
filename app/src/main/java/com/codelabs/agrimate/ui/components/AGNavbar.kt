package com.codelabs.agrimate.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.CompositingStrategy
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.codelabs.agrimate.ui.components.model.NavbarMenuModel
import com.codelabs.agrimate.ui.theme.Green300
import com.codelabs.agrimate.ui.theme.GreyScale500

private val activeIconBrush = listOf(
    Color(0xFF18B6A1),
    Color(0XFF00BCA4),
    Color(0xFF006B5D)
)

private val inactiveIconBrush = listOf(
    Color(0xFF8A8A8A),
    Color(0xFFB8B8B8)
)

@Composable
fun AGNavbar(
    modifier: Modifier = Modifier,
    navBarMenuItem: @Composable RowScope.() -> Unit,
) {
    Surface(
        modifier = modifier.windowInsetsPadding(WindowInsets.navigationBars),
        color = Color.White,
        shadowElevation = 10.dp,
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 28.dp, end = 28.dp, top = 12.dp, bottom = 12.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            navBarMenuItem()
        }
    }
}

@Composable
fun AGNavBarMenuItem(
    modifier: Modifier = Modifier,
    data: NavbarMenuModel,
    onClick: () -> Unit,
    selected: Boolean,
) {
    val interactionSource = remember { MutableInteractionSource() }
    Column(
        modifier = modifier
            .fillMaxWidth()
            .clickable(indication = null, interactionSource = interactionSource) {
                onClick()
            },
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.padding(top = 12.dp))
        Box(
            modifier = Modifier
                .fillMaxWidth(),
            contentAlignment = Alignment.TopCenter
        ) {
            Icon(
                modifier = Modifier
                    .height(24.dp)
                    .graphicsLayer {
                        compositingStrategy = CompositingStrategy.Offscreen
                    }
                    .drawWithCache {
                        onDrawWithContent {
                            drawContent()
                            drawRect(
                                Brush.verticalGradient(
                                    colors = if (selected) activeIconBrush else inactiveIconBrush,
                                ), blendMode = BlendMode.SrcAtop
                            )
                        }
                    },
                painter = painterResource(id = data.icon),
                contentDescription = data.label,
            )
        }
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = data.label,
            textAlign = TextAlign.Center,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            color = if (selected) Green300 else GreyScale500,
            fontSize = 11.sp,
            lineHeight = (19.3).sp
        )
    }
}