package com.codelabs.agrimate.screens.farmer.land.detail.activity

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.codelabs.agrimate.ui.components.AGButton
import com.codelabs.agrimate.ui.components.AGInputDate
import com.codelabs.agrimate.ui.components.AGInputImage
import com.codelabs.agrimate.ui.components.AGInputLayout
import com.codelabs.agrimate.ui.components.AGInputTime
import com.codelabs.agrimate.ui.components.AGInputWithSuffix
import com.codelabs.agrimate.ui.components.AGSelectInputProvider
import com.codelabs.agrimate.ui.components.AGSelectInputWithSearch
import com.codelabs.agrimate.ui.components.AGTopAppBar
import com.codelabs.agrimate.ui.components.AGTopAppBarDefaultNavigationIcon
import com.codelabs.agrimate.ui.components.AGTopAppBarDefaultTitle
import com.codelabs.agrimate.ui.theme.Green100

private enum class LandActivityType {
    GIVE_FERTILIZER, GIVE_PESTICIDE
}

private data class ActivityTypeState(
    override val label: String,
    override val value: String,
    val type: LandActivityType,
) : AGSelectInputProvider

private val typeActivityDefault = listOf(
    ActivityTypeState("Pemberian Pupuk", "0", LandActivityType.GIVE_FERTILIZER),
    ActivityTypeState("Pemberian Pestisida", "1", LandActivityType.GIVE_PESTICIDE),
)

@Composable
fun LandActivityAddScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
) {
    Scaffold(
        modifier = modifier,
        contentWindowInsets = WindowInsets(bottom = 0),
        topBar = { TopAppBar(onNavigationClick = { navController.popBackStack() }) },
        bottomBar = { ActionBottomBar(onClick = {}) }
    ) { paddingValues ->
        LandActivityAddContent(
            Modifier
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
        )
    }
}

@Composable
private fun LandActivityAddContent(modifier: Modifier = Modifier) {
    var tanggalAktivitas by remember { mutableStateOf<Long?>(null) }
    var pukul by remember { mutableStateOf("") }

    var selectedType by remember { mutableStateOf<ActivityTypeState?>(null) }

    Box(modifier) {
        Column(
            modifier = Modifier.padding(horizontal = 18.dp, vertical = 19.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            AGInputLayout(label = "Tanggal Aktivitas", isRequired = true) {
                AGInputDate(value = tanggalAktivitas, onValueChange = { tanggalAktivitas = it })
            }
            AGInputLayout(label = "Pukul", isRequired = true) {
                AGInputTime(value = pukul, onValueChange = { pukul = it })
            }
            AGInputLayout(label = "Jenis Aktivitas", isRequired = true) {
                AGSelectInputWithSearch(
                    value = selectedType?.label ?: "",
                    onValueChange = { selectedType = it },
                    label = "Jenis Aktivitas",
                    placeholder = "Pilih Jenis Aktivitas",
                    options = typeActivityDefault
                )
            }
            if (selectedType != null) {
                when (selectedType!!.type) {
                    LandActivityType.GIVE_FERTILIZER -> {
                        AGInputLayout(label = "Natrium", isRequired = true) {
                            AGInputWithSuffix(
                                value = "",
                                onValueChange = {},
                                placeholder = "0",
                                suffix = "N"
                            )
                        }
                        AGInputLayout(label = "Fosfor", isRequired = true) {
                            AGInputWithSuffix(
                                value = "",
                                onValueChange = {},
                                placeholder = "0",
                                suffix = "P"
                            )
                        }
                        AGInputLayout(label = "Kalium", isRequired = true) {
                            AGInputWithSuffix(
                                value = "",
                                onValueChange = {},
                                placeholder = "0",
                                suffix = "Kl"
                            )
                        }
                    }

                    LandActivityType.GIVE_PESTICIDE -> {
                        AGInputLayout(label = "Dosis", isRequired = true) {
                            AGInputWithSuffix(
                                value = "",
                                onValueChange = {},
                                placeholder = "0",
                                suffix = "g/ha"
                            )
                        }
                        AGInputLayout(label = "Kapasitas Tangki", isRequired = true) {
                            AGInputWithSuffix(
                                value = "",
                                onValueChange = {},
                                placeholder = "0",
                                suffix = "liter"
                            )
                        }
                    }
                }
            }
            AGInputLayout(label = "Gambar Aktivitas", isRequired = true) {
                AGInputImage(onClick = {})
            }
        }
    }
}

@Composable
private fun TopAppBar(modifier: Modifier = Modifier, onNavigationClick: () -> Unit) {
    AGTopAppBar(
        modifier = modifier,
        navigationIcon = { AGTopAppBarDefaultNavigationIcon(onClick = onNavigationClick) }) {
        AGTopAppBarDefaultTitle(text = "Tambah Aktivitas")
    }
}

@Composable
private fun ActionBottomBar(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
) {
    Surface(
        modifier = modifier.windowInsetsPadding(WindowInsets(bottom = 0.dp)),
        color = Color.White,
        shadowElevation = 10.dp
    ) {
        Box(
            Modifier.padding(horizontal = 18.dp, vertical = 16.dp),
        ) {
            AGButton(
                modifier = Modifier.fillMaxWidth(),
                onClick = onClick,
                containerColor = Green100,
                shape = RoundedCornerShape(12.dp),
                contentPadding = PaddingValues(horizontal = 8.dp, vertical = 20.dp)
            ) {
                Text(
                    text = "Simpan",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}