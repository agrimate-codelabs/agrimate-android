package com.codelabs.agrimate.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBackIosNew
import androidx.compose.material.icons.outlined.Tune
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.codelabs.agrimate.R
import com.codelabs.agrimate.ui.theme.AgrimateTheme
import com.codelabs.agrimate.ui.theme.Green300
import com.codelabs.agrimate.ui.theme.Green400
import com.codelabs.agrimate.ui.theme.Green500

@Composable
fun AGTopAppBar(
    modifier: Modifier = Modifier,
    containerColor: Color = Green500,
    navigationIcon: @Composable () -> Unit = {},
    trailingContent: @Composable RowScope.() -> Unit = {},
    titleContent: @Composable () -> Unit,
) {
    Row(
        modifier
            .fillMaxWidth()
            .background(color = containerColor)
            .windowInsetsPadding(WindowInsets.statusBars)
            .padding(vertical = 16.dp, horizontal = 18.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            modifier = Modifier.weight(1f),
            horizontalArrangement = Arrangement.spacedBy(20.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            navigationIcon()
            titleContent()
        }
        Row(
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            trailingContent()
        }
    }
}

@Composable
fun AGTopAppBarDefaultNavigationIcon(modifier: Modifier = Modifier, onClick: () -> Unit) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(size = 8.dp))
            .border(
                width = 2.dp,
                color = Green300,
                shape = RoundedCornerShape(size = 8.dp)
            )
            .clickable { onClick() }
            .padding(8.dp)
    ) {
        Icon(
            modifier = Modifier.size(24.dp),
            imageVector = Icons.Outlined.ArrowBackIosNew,
            contentDescription = "Back",
            tint = Color.White
        )
    }
}

@Composable
fun AGTopAppBarDefaultTitle(modifier: Modifier = Modifier, text: String) {
    Text(
        modifier = modifier,
        text = text, style = TextStyle(
            color = Color.White,
            fontSize = 18.sp,
            fontWeight = FontWeight.SemiBold
        ),
        maxLines = 1,
        overflow = TextOverflow.Ellipsis
    )
}

@Composable
fun AGTopAppBarUserAvatarTitle(
    modifier: Modifier = Modifier,
    user: String,
    role: String
) {
    Row(modifier = modifier, horizontalArrangement = Arrangement.spacedBy(12.dp)) {
        Image(
            modifier = Modifier
                .size(40.dp)
                .clip(RoundedCornerShape(100)),
            painter = painterResource(id = R.drawable.profile_dummy),
            contentDescription = null,
            contentScale = ContentScale.Crop
        )
        Column(modifier = Modifier) {
            Text(
                modifier = Modifier,
                text = user, style = TextStyle(
                    color = Color.White,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.SemiBold
                ),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                text = role,
                fontSize = 12.sp,
                fontWeight = FontWeight.Medium,
                color = Color(0xFFBDBDBD)
            )
        }
    }
}

@Composable
fun AGTopAppBarDefaultActionButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    content: @Composable (modifier: Modifier) -> Unit,
) {
    Box(
        modifier = modifier
            .background(color = Green400, shape = RoundedCornerShape(size = 8.dp))
            .clickable { onClick() }
            .padding(8.dp)
    ) {
        content(Modifier.size(24.dp))
    }
}

@Composable
@Preview
fun AGDeepTopBarPreview() {
    AgrimateTheme {
        AGTopAppBar(
            navigationIcon = { AGTopAppBarDefaultNavigationIcon(onClick = {}) },
            titleContent = { AGTopAppBarDefaultTitle(text = "Title") },
            trailingContent = {
                AGTopAppBarDefaultActionButton(onClick = {}) {
                    Icon(
                        imageVector = Icons.Outlined.Tune,
                        contentDescription = "Filter",
                        tint = Color.White
                    )
                }
            }
        )
    }
}