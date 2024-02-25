package com.codelabs.agrimate.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ChevronRight
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.codelabs.agrimate.ui.common.state.UiState
import com.codelabs.agrimate.ui.components.shimmer.shimmerBrush
import com.codelabs.agrimate.ui.theme.GreyScale200
import com.codelabs.agrimate.ui.theme.GreyScale300
import com.codelabs.agrimate.ui.theme.GreyScale500
import com.codelabs.agrimate.ui.theme.GreyScale700
import com.codelabs.core.BuildConfig
import com.codelabs.core.domain.model.PlantDiseaseDetectionModel

@Composable
fun AGNotificationFilterSheetContent(modifier: Modifier = Modifier, onClose: () -> Unit) {
    Box(modifier = modifier) {
        Column {
            Column(modifier = Modifier.padding(horizontal = 25.dp)) {
                FilterHeading(
                    modifier = Modifier,
                    title = "Filter Notifikasi",
                    onReset = {/*TODO*/ })
                Spacer(Modifier.padding(bottom = 24.dp))
                Column {
                    FilterTitleCategory("Terakhir Update")
                    Spacer(Modifier.padding(bottom = 8.dp))
                    Column(modifier = Modifier, verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        RadioFilter(label = "Semua", selected = false, onClick = { /*TODO*/ })
                        RadioFilter(label = "Belum Dibaca", selected = true, onClick = { /*TODO*/ })
                        RadioFilter(
                            label = "Sudah Dibaca",
                            selected = false,
                            onClick = { /*TODO*/ })
                    }
                }
                Spacer(Modifier.padding(bottom = 16.dp))
                Column {
                    FilterTitleCategory("Lokasi")
                    Spacer(Modifier.padding(bottom = 8.dp))
                    Column(modifier = Modifier, verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        CheckBoxFilter(
                            label = "Informasi/Berita",
                            checked = false,
                            onChange = { /*TODO*/ })
                        CheckBoxFilter(
                            label = "Aktivitas Pertanian",
                            checked = true,
                            onChange = { /*TODO*/ })
                        CheckBoxFilter(
                            label = "Aktivitas Komunitas",
                            checked = false,
                            onChange = { /*TODO*/ })
                    }
                }
            }
            FilterActions(onClose = onClose)
        }
    }
}

@Composable
fun AGLandDetailFilterSheet(modifier: Modifier = Modifier, onClose: () -> Unit) {
    Box(modifier = modifier) {
        Column {
            Column(modifier = Modifier.padding(horizontal = 25.dp)) {
                FilterHeading(title = "Filter Aktivitas Tani", onReset = {/*TODO*/ })
                Spacer(Modifier.padding(bottom = 24.dp))
                Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                    Column(Modifier.weight(1f)) {
                        FilterTitleCategory(text = "Periode Awal")
                        Spacer(Modifier.padding(bottom = 16.dp))
                        AGInputDate(
                            modifier = Modifier.fillMaxWidth(),
                            value = null,
                            onValueChange = {})
                    }
                    Column(Modifier.weight(1f)) {
                        FilterTitleCategory(text = "Periode Akhir")
                        Spacer(Modifier.padding(bottom = 16.dp))
                        AGInputDate(
                            modifier = Modifier.fillMaxWidth(),
                            value = null,
                            onValueChange = {})
                    }
                }
                Spacer(Modifier.padding(bottom = 16.dp))
                FilterTitleCategory("Status Aktivitas")
                Spacer(Modifier.padding(bottom = 16.dp))
                Column(modifier = Modifier, verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    CheckBoxFilter(
                        label = "Belum Panen",
                        checked = false,
                        onChange = { /*TODO*/ })
                    CheckBoxFilter(
                        label = "Panen",
                        checked = false,
                        onChange = { /*TODO*/ })
                    CheckBoxFilter(
                        label = "Gagal Panen",
                        checked = true,
                        onChange = { /*TODO*/ })
                }
            }
            FilterActions(onClose = onClose)
        }
    }
}

@Composable
fun AGCheckDiseaseResultSheet(
    modifier: Modifier = Modifier,
    onReset: () -> Unit,
    onDetailClick: (name: String) -> Unit,
    diseasePredict: UiState<PlantDiseaseDetectionModel>,
) {
    Box(modifier = modifier) {
        Column(modifier = Modifier.padding(horizontal = 25.dp)) {
            FilterHeading(title = "Hasil Deteksi", onReset = onReset)
            when (diseasePredict) {
                is UiState.Error -> {
                    Box(
                        modifier = Modifier
                            .sizeIn(minHeight = 200.dp, maxHeight = 400.dp)
                            .fillMaxWidth()
                    ) {
                        Text(
                            modifier = Modifier
                                .fillMaxWidth()
                                .align(Alignment.Center),
                            text = diseasePredict.message,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = GreyScale200,
                            textAlign = TextAlign.Center
                        )
                    }
                }

                is UiState.Loading -> {
                    Box(
                        modifier = Modifier
                            .sizeIn(minHeight = 200.dp, maxHeight = 400.dp)
                            .fillMaxWidth()
                    ) {
                        CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                    }
                }

                is UiState.Success -> {
                    Column(
                        modifier = Modifier.padding(vertical = 24.dp),
                        verticalArrangement = Arrangement.spacedBy(24.dp)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(12.dp))
                                .clickable { onDetailClick(diseasePredict.data.name) }
                                .border(
                                    1.dp, GreyScale700, RoundedCornerShape(12.dp)
                                )
                                .padding(4.dp),
                            horizontalArrangement = Arrangement.spacedBy(10.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            AsyncImage(
                                modifier = Modifier
                                    .size(84.dp)
                                    .aspectRatio(1f)
                                    .clip(RoundedCornerShape(12.dp))
                                    .background(shimmerBrush(), RoundedCornerShape(12.dp)),
                                model = "${BuildConfig.API_AGRIMATE_BASE_URL}/diseases-plants/thumbnail/${diseasePredict.data.image}",
                                contentDescription = null,
                                contentScale = ContentScale.Crop
                            )
                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = diseasePredict.data.name,
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = GreyScale200
                                )
                                Spacer(Modifier.padding(bottom = 10.dp))
                                Text(
                                    text = "Lihat detail hasil diagnosis",
                                    fontSize = 14.sp,
                                    lineHeight = 27.3.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = GreyScale500
                                )
                            }
                            IconButton(onClick = { onDetailClick(diseasePredict.data.name) }) {
                                Icon(
                                    imageVector = Icons.Outlined.ChevronRight,
                                    contentDescription = null,
                                    tint = GreyScale500
                                )
                            }
                        }
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .defaultMinSize(minHeight = 94.dp)
                                .clip(RoundedCornerShape(12.dp))
                                .border(
                                    1.dp, GreyScale700, RoundedCornerShape(12.dp)
                                )
                                .clickable { /*TODO*/ }
                                .padding(12.dp),
                            horizontalArrangement = Arrangement.spacedBy(10.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "Ajukan pertanyaan ke forum diskusi",
                                modifier = Modifier.weight(1f)
                            )
                            IconButton(onClick = { /*TODO*/ }) {
                                Icon(
                                    imageVector = Icons.Outlined.ChevronRight,
                                    contentDescription = null,
                                    tint = GreyScale500
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun FilterActions(modifier: Modifier = Modifier, onClose: () -> Unit) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(25.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        AGButtonOutlined(
            modifier = Modifier
                .weight(1f),
            onClick = onClose,
            enabled = true,
            content = { Text(text = "Tutup", fontSize = 16.sp) },
            contentPadding = PaddingValues(16.dp)
        )
        AGButton(
            modifier = Modifier
                .weight(1f),
            onClick = {},
            enabled = true,
            content = { Text(text = "Terapkan", fontSize = 16.sp) },
            contentPadding = PaddingValues(16.dp)
        )
    }
}

@Composable
private fun FilterHeading(modifier: Modifier = Modifier, title: String, onReset: () -> Unit) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            title, modifier = Modifier.weight(1f), style = TextStyle(
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = GreyScale200,
            ),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
        Text(
            "Reset",
            modifier = Modifier.clickable { onReset() },
            style = TextStyle(
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFFF66060),
            )
        )
    }
}

@Composable
private fun FilterTitleCategory(text: String, modifier: Modifier = Modifier) {
    Text(
        text, modifier = modifier, style = TextStyle(
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            color = GreyScale300,
        )
    )
}

@Composable
private fun RadioFilter(
    modifier: Modifier = Modifier,
    label: String,
    selected: Boolean = false,
    onClick: () -> Unit,
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        FilterLabel(label)
        RadioButton(selected = selected, onClick = onClick)
    }
}

@Composable
private fun CheckBoxFilter(
    modifier: Modifier = Modifier,
    label: String,
    checked: Boolean = false,
    onChange: (Boolean) -> Unit,
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        FilterLabel(label)
        Checkbox(checked = checked, onCheckedChange = onChange)
    }
}

@Composable
private fun FilterLabel(label: String, modifier: Modifier = Modifier) {
    Text(
        label, modifier = modifier, fontSize = 14.sp,
        lineHeight = 27.3.sp,
        fontWeight = FontWeight.Medium,
        color = GreyScale300,
    )
}

@Composable
@Preview(backgroundColor = 1)
fun AGNotificationFilterSheetContentPreview() {
    AGNotificationFilterSheetContent(onClose = {})
}