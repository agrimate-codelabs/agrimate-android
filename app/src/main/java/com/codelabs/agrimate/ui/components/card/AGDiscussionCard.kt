package com.codelabs.agrimate.ui.components.card

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.MoreVert
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.codelabs.agrimate.R
import com.codelabs.agrimate.ui.components.AGActionDropdown
import com.codelabs.agrimate.ui.components.AGDropdownMenuItem
import com.codelabs.agrimate.ui.theme.AgrimateTheme
import com.codelabs.agrimate.ui.theme.GreyScale200
import com.codelabs.agrimate.ui.theme.GreyScale500
import com.codelabs.agrimate.ui.theme.GreyScale800
import com.codelabs.agrimate.ui.theme.RedStatus

@Composable
fun AGDiscussionCard(
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
    name: String = "",
    createdAt: String = "",
    postTitle: String = "",
    postDescription: String = "",
    postLikeCount: Int = 0,
    postCommentCount: Int = 0,
    bookmarked: Boolean = false
) {
    Surface(
        modifier = modifier
            .fillMaxWidth(),
        color = Color.White,
        shape = RoundedCornerShape(12.dp),
        shadowElevation = 1.dp,
        onClick = onClick
    ) {
        var actionExpanded by remember { mutableStateOf(false) }
        Surface(
            modifier = modifier
                .fillMaxWidth(),
            color = Color.White,
            shape = RoundedCornerShape(12.dp),
            shadowElevation = 1.dp,
            onClick = onClick
        ) {
            Column(modifier = Modifier.padding(horizontal = 22.dp, vertical = 12.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Row(
                        modifier = Modifier.weight(1f),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Image(
                            modifier = Modifier
                                .size(43.dp)
                                .aspectRatio(1f)
                                .clip(RoundedCornerShape(12.dp)),
                            painter = painterResource(id = R.drawable.profile_dummy),
                            contentDescription = null,
                            contentScale = ContentScale.Crop
                        )
                        Spacer(modifier = Modifier.padding(end = 10.dp))
                        Column {
                            Text(
                                text = name,
                                fontWeight = FontWeight.SemiBold,
                                color = GreyScale200,
                                fontSize = 14.sp
                            )
                            Text(
                                text = createdAt,
                                fontWeight = FontWeight.Medium,
                                fontSize = 11.sp,
                                color = GreyScale500
                            )
                        }
                    }
                    Box {
                        IconButton(onClick = { actionExpanded = !actionExpanded }) {
                            Icon(
                                imageVector = Icons.Outlined.MoreVert,
                                contentDescription = "aksi",
                                tint = GreyScale500
                            )
                        }
                        AGActionDropdown(
                            expanded = actionExpanded,
                            onDismissRequest = { actionExpanded = false }) {
                            AGDropdownMenuItem(
                                text = "Hapus",
                                onClick = {
                                    actionExpanded = false
                                },
                                leadingIcon = {
                                    Icon(
                                        imageVector = Icons.Outlined.Delete,
                                        contentDescription = null,
                                    )
                                },
                                contentColor = RedStatus
                            )
                        }
                    }

                }
                Spacer(modifier = Modifier.padding(bottom = 10.dp))
                Column {
                    Text(
                        modifier = Modifier.padding(bottom = 10.dp),
                        text = postTitle,
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,
                        color = GreyScale200
                    )
                    Text(
                        text = postDescription,
                        fontWeight = FontWeight.Medium,
                        fontSize = 14.sp,
                        color = GreyScale500
                    )
                }
                Spacer(modifier = Modifier.padding(bottom = 18.dp))
                Divider(thickness = 1.dp, color = GreyScale800)
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 4.dp)
                        .padding(horizontal = 36.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    ActionIcon(
                        modifier = modifier.weight(1f),
                        icon = ImageVector.vectorResource(R.drawable.ag_like_icon),
                        count = "$postLikeCount"
                    )
                    ActionIcon(
                        modifier = modifier.weight(1f),
                        icon = ImageVector.vectorResource(R.drawable.ag_comment_icon),
                        count = "$postCommentCount"
                    )
                    ActionIcon(
                        modifier = modifier.weight(1f),
                        icon = if (bookmarked) ImageVector.vectorResource(R.drawable.ag_bookmark_icon)
                        else ImageVector.vectorResource(R.drawable.ag_bookmark_icon)
                    )
                }
            }
        }
    }
}

@Composable
private fun ActionIcon(modifier: Modifier = Modifier, icon: ImageVector, count: String? = null) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp),
        horizontalArrangement = Arrangement.spacedBy(
            8.dp,
            alignment = Alignment.CenterHorizontally
        ),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Icon(
            modifier = Modifier.size(18.dp),
            imageVector = icon,
            contentDescription = "Suka",
            tint = GreyScale200
        )
        if (count != null) {
            Text(
                text = count,
                color = GreyScale200,
                fontSize = 11.sp,
                fontWeight = FontWeight.Medium
            )
        }
    }
}

@Preview
@Composable
fun AGDiscussionCardPreview() {
    AgrimateTheme {
        AGDiscussionCard()
    }
}
