package com.codelabs.agrimate.ui.components.card

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Delete
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.codelabs.agrimate.R
import com.codelabs.agrimate.ui.components.AGActionDropdown
import com.codelabs.agrimate.ui.components.AGDropdownMenuItem
import com.codelabs.agrimate.ui.components.AGLabelChip
import com.codelabs.agrimate.ui.theme.GreyScale200
import com.codelabs.agrimate.ui.theme.GreyScale500
import com.codelabs.agrimate.ui.theme.RedStatus

@Composable
fun AGCapitalCard(
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
    capitalSubject: String = "Pengajuan",
    date: String = "23 Mei 2023",
    onDeleteClick: () -> Unit = {},
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
        Row(modifier = Modifier.padding(horizontal = 22.dp, vertical = 19.dp)) {
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                AGLabelChip(text = "Disetujui")
                Text(
                    text = capitalSubject,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    color = GreyScale200
                )
                Column {
                    DetailLabel(
                        label = "Pengajuan pada $date",
                        icon = R.drawable.ag_calendar_icon,
                        contentDescription = null
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
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
                color = GreyScale200,
            )
        )
    }
}