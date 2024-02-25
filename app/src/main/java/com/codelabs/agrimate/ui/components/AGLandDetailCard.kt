package com.codelabs.agrimate.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.codelabs.agrimate.R
import com.codelabs.agrimate.ui.theme.Green100
import com.codelabs.agrimate.ui.theme.GreyScale200
import com.codelabs.agrimate.ui.theme.GreyScale500
import com.codelabs.agrimate.utils.DateUtils
import com.codelabs.agrimate.utils.UiUtils

@Composable
fun AGLandDetailCard(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    status: String,
    plantingDate: String,
    harvestingDate: String,
    plantingSize: Double,
    commodity: String,
    lastActivity: String
) {
    Surface(
        modifier = modifier,
        shape = RoundedCornerShape(12.dp),
        shadowElevation = 1.dp,
        color = Color.White,
        onClick = onClick,
    ) {
        Box(Modifier.padding(16.dp)) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                    AGLabelChip(
                        text = status, color = UiUtils.getStatusColor(status)
                    )
                    Row(modifier = Modifier, horizontalArrangement = Arrangement.spacedBy(24.dp)) {
                        MainLabel(
                            label = "Tanam:",
                            value = DateUtils.convertDateFormat(
                                plantingDate,
                                inputPattern = "yyyy-MM-dd",
                                outputPattern = "d MMMM yyyy",
                                errorOutput = "-"
                            )
                        )
                        MainLabel(
                            label = "Panen:",
                            value = DateUtils.convertDateFormat(
                                harvestingDate,
                                inputPattern = "yyyy-MM-dd",
                                outputPattern = "d MMMM yyyy",
                                errorOutput = "-"
                            )
                        )
                    }
                }
                Divider()
                Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                    Row(
                        modifier = Modifier.height(IntrinsicSize.Min),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        LabelDetail(
                            icon = R.drawable.ic_land_fields,
                            contentDescription = null,
                            label = "$plantingSize Hektare"
                        )
                        Divider(
                            modifier = Modifier
                                .width(1.dp)
                                .fillMaxHeight()
                        )
                        LabelDetail(
                            icon = R.drawable.ic_plant_type,
                            contentDescription = null,
                            label = commodity
                        )
                    }
                    LabelDetail(
                        icon = R.drawable.ic_land_last_activity,
                        contentDescription = null,
                        label = "Aktivitas Terakhir pada ${
                            DateUtils.convertDateFormat(
                                lastActivity,
                                inputPattern = "yyyy-MM-dd",
                                outputPattern = "d MMMM yyyy"
                            )
                        }"
                    )
                }
            }
        }
    }
}

@Composable
private fun MainLabel(modifier: Modifier = Modifier, label: String, value: String) {
    Column(modifier.defaultMinSize(minWidth = 112.dp)) {
        Text(
            label, style = TextStyle(
                fontSize = 12.sp,
                lineHeight = 20.4.sp,
                fontWeight = FontWeight.Normal,
                color = GreyScale200,
            )
        )
        Text(
            value, style = TextStyle(
                fontSize = 14.sp,
                lineHeight = 23.8.sp,
                fontWeight = FontWeight.Bold,
                color = GreyScale200,
            )
        )
    }
}

@Composable
private fun LabelDetail(
    modifier: Modifier = Modifier,
    icon: Int,
    contentDescription: String?,
    label: String,
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Icon(
            painter = painterResource(icon),
            contentDescription = contentDescription,
            modifier = Modifier.size(16.dp),
            tint = Green100
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