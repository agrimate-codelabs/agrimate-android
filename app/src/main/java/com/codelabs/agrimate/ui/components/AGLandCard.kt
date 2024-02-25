package com.codelabs.agrimate.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material.icons.outlined.MoreVert
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.codelabs.agrimate.R
import com.codelabs.agrimate.ui.theme.GreyScale200
import com.codelabs.agrimate.ui.theme.GreyScale500
import com.codelabs.agrimate.ui.theme.RedStatus
import com.codelabs.core.BuildConfig

@Composable
fun AGLandCard(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    title: String,
    district: String,
    landArea: Double,
    lastActivity: String,
    imageUrl: String,
    onUpdateClick: () -> Unit,
    onDeleteClick: () -> Unit
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
        Row(modifier = Modifier.padding(horizontal = 10.dp, vertical = 16.dp)) {
            AsyncImage(
                model = "${BuildConfig.API_AGRIMATE_BASE_URL}/farmer-land/thumbnail/$imageUrl",
                contentDescription = title,
                modifier = Modifier
                    .width(91.dp)
                    .height(98.dp)
                    .clip(RoundedCornerShape(8.dp)),
                contentScale = ContentScale.Crop
            )
            Spacer(Modifier.padding(end = 11.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    title, style = TextStyle(
                        fontSize = 16.sp,
                        lineHeight = 27.2.sp,
                        fontWeight = FontWeight.Bold,
                        color = GreyScale200,
                    )
                )
                Spacer(Modifier.padding(bottom = 4.dp))
                Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                    DetailLabel(
                        label = district,
                        icon = R.drawable.location_icon,
                        contentDescription = "lokasi"
                    )
                    DetailLabel(
                        label = "$landArea Hektare",
                        icon = R.drawable.land_area_icon,
                        contentDescription = "luas lahan"
                    )
                    DetailLabel(
                        label = "Aktivitas terakhir pada $lastActivity",
                        icon = R.drawable.calendar_icon,
                        contentDescription = "aktivitas terakhir"
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
                        text = "Ubah",
                        onClick = {
                            actionExpanded = false
                            onUpdateClick()
                        },
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Outlined.Edit,
                                contentDescription = null,
                            )
                        })
                    AGDropdownMenuItem(
                        text = "Hapus",
                        onClick = {
                            actionExpanded = false
                            onDeleteClick()
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
    }
}

@Composable
private fun DetailLabel(
    modifier: Modifier = Modifier,
    label: String,
    icon: Int,
    contentDescription: String?,
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Image(
            painter = painterResource(icon),
            contentDescription = contentDescription,
            modifier = Modifier.size(16.dp),
        )
        Text(
            label, style = TextStyle(
                fontSize = 12.sp,
                lineHeight = 21.3.sp,
                fontWeight = FontWeight.Medium,
                color = GreyScale500,
            )
        )
    }
}