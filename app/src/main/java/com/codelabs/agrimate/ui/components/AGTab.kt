package com.codelabs.agrimate.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabPosition
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import com.codelabs.agrimate.ui.theme.Green300
import com.codelabs.agrimate.ui.theme.GreyScale500
import com.codelabs.agrimate.ui.theme.GreyScale900

@Composable
fun AGTabBar(modifier: Modifier = Modifier, children: @Composable (Modifier) -> Unit) {
    Surface(modifier = modifier, shape = RoundedCornerShape(8.dp), color = GreyScale900) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(4.dp)
        ) {
            children(
                Modifier
                    .weight(1f)
                    .align(Alignment.CenterVertically)
            )
        }
    }
}

@Composable
fun <T : Enum<T>> AGTab(
    modifier: Modifier = Modifier,
    titles: List<String>,
    enumValues: Array<T>,
    tabSelected: T,
    onTabSelected: (T) -> Unit,
) {
    TabRow(
        selectedTabIndex = tabSelected.ordinal,
        modifier = modifier,
        containerColor = Color.Transparent,
        indicator = { tabPositions: List<TabPosition> ->
            Box(
                Modifier
                    .tabIndicatorOffset(tabPositions[tabSelected.ordinal])
                    .fillMaxSize()
                    .clip(RoundedCornerShape(8.dp))
                    .background(color = Green300)
                    .padding(horizontal = 4.dp)
                    .zIndex(-1f)
            )
        },
        divider = {}) {
        titles.forEachIndexed { index, title ->
            val selected = index == tabSelected.ordinal
            Tab(
                selected = selected,
                onClick = { onTabSelected(enumValues[index]) },
                modifier = Modifier
                    .clip(RoundedCornerShape(8.dp)),
                selectedContentColor = Color.Transparent
            ) {

                Text(
                    title,
                    modifier = Modifier.padding(vertical = 8.dp),
                    style = TextStyle(
                        fontSize = 12.sp,
                        lineHeight = 21.3.sp,
                        fontWeight = FontWeight.Medium,
                        color = if (selected) Color.White else GreyScale500,
                        textAlign = TextAlign.Center,
                    )
                )
            }
        }
    }
}