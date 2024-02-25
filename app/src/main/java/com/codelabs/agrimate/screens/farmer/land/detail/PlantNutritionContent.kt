package com.codelabs.agrimate.screens.farmer.land.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import com.codelabs.agrimate.R
import com.codelabs.agrimate.ui.components.AGCardWarning
import com.codelabs.agrimate.ui.components.AGDivider
import com.codelabs.agrimate.ui.components.AGTab
import com.codelabs.agrimate.ui.components.AGTabBar
import com.codelabs.agrimate.ui.components.htmltext.HtmlText
import com.codelabs.agrimate.ui.theme.AgrimateTheme
import com.codelabs.core.domain.model.PlantNutritionModel

private val nutritionTab = listOf("Kalium", "Nitrogen", "Fosforus", "Kelembaban")

private enum class NutritionTabContent {
    POTASSIUM, NITROGEN, PHOSPHORUS, MOISTURE
}

@Composable
fun PlantNutritionContent(
    modifier: Modifier = Modifier,
    plantNutritionData: PlantNutritionModel? = null
) {
    val nutritionTabContentValues = NutritionTabContent.values()
    var selectedTNutritionTab by rememberSaveable {
        mutableStateOf(NutritionTabContent.POTASSIUM)
    }

    Column(modifier = modifier) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 24.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            Column(
                modifier = Modifier,
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(
                    modifier = Modifier.size(32.dp),
                    painter = painterResource(id = R.drawable.ic_ph_plant),
                    contentDescription = null,
                    tint = Color(0xFFFF7171)
                )
                Spacer(modifier = Modifier.padding(bottom = 8.dp))
                Text(
                    text = "Berikut kondisi nutrisi tanaman pada lahan saat ini:",
                    textAlign = TextAlign.Center,
                    color = Color(0XFF212121),
                    fontWeight = FontWeight.Medium
                )
            }
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .border(1.dp, Color(0xFFE7E7E7), RoundedCornerShape(8.dp))
                    .clip(RoundedCornerShape(8.dp))
            ) {
                Row(
                    modifier = Modifier.height(intrinsicSize = IntrinsicSize.Min),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f)
                            .padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .background(
                                    Color(0xFFF1FCF3), RoundedCornerShape(999.dp)
                                )
                                .padding(12.dp)
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_kalium),
                                contentDescription = null,
                                tint = Color(0xFF52B69A)
                            )
                        }
                        Column(
                            modifier = Modifier
                                .padding(start = 8.dp)
                                .height(intrinsicSize = IntrinsicSize.Max)
                        ) {
                            Text(
                                text = buildAnnotatedString {
                                    withStyle(
                                        style = SpanStyle(
                                            fontSize = 24.sp,
                                            fontWeight = FontWeight.Bold
                                        )
                                    ) {
                                        append("${plantNutritionData?.kalium ?: 0}")
                                    }
                                    append(" ")
                                    withStyle(style = SpanStyle(fontSize = 12.sp)) {
                                        append("mg/kg")
                                    }
                                },
                                color = Color(0xFF424242),
                                lineHeight = 1.em
                            )
                            Text(
                                text = "Kalium",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Medium,
                                color = Color(0XFFAFAFAF),
                                lineHeight = 1.em
                            )
                        }
                    }
                    Divider(
                        color = Color(0XFFE7E7E7), modifier = Modifier
                            .fillMaxHeight()
                            .width(1.dp)
                    )
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f)
                            .padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .background(
                                    Color(0XFFFCFBF1), RoundedCornerShape(999.dp)
                                )
                                .padding(12.dp)
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_nitrogen),
                                contentDescription = null,
                                tint = Color(0XFFECE200)
                            )
                        }
                        Column(
                            modifier = Modifier
                                .padding(start = 8.dp)
                                .height(intrinsicSize = IntrinsicSize.Max)
                        ) {
                            Text(
                                text = buildAnnotatedString {
                                    withStyle(
                                        style = SpanStyle(
                                            fontSize = 24.sp,
                                            fontWeight = FontWeight.Bold
                                        )
                                    ) {
                                        append("${plantNutritionData?.nitrogen ?: 0}")
                                    }
                                    append(" ")
                                    withStyle(style = SpanStyle(fontSize = 12.sp)) {
                                        append("mg/kg")
                                    }
                                },
                                color = Color(0xFF424242),
                                lineHeight = 1.em
                            )
                            Text(
                                text = "Nitrogen",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Medium,
                                color = Color(0XFFAFAFAF),
                                lineHeight = 1.em
                            )
                        }
                    }
                }
                Divider(color = Color(0XFFE7E7E7), thickness = 1.dp)
                Row(
                    modifier = Modifier.height(intrinsicSize = IntrinsicSize.Min),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f)
                            .padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .background(
                                    Color(0XFFFCF8F1), RoundedCornerShape(999.dp)
                                )
                                .padding(12.dp)
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_phosphor),
                                contentDescription = null,
                                tint = Color(0xFFFFA506)
                            )
                        }
                        Column(
                            modifier = Modifier
                                .padding(start = 8.dp)
                                .height(intrinsicSize = IntrinsicSize.Max)
                        ) {
                            Text(
                                text = buildAnnotatedString {
                                    withStyle(
                                        style = SpanStyle(
                                            fontSize = 24.sp,
                                            fontWeight = FontWeight.Bold
                                        )
                                    ) {
                                        append("${plantNutritionData?.phosphorus ?: 0}")
                                    }
                                    append(" ")
                                    withStyle(style = SpanStyle(fontSize = 12.sp)) {
                                        append("mg/kg")
                                    }
                                },
                                color = Color(0xFF424242),
                                lineHeight = 1.em
                            )
                            Text(
                                text = "Fosforus",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Medium,
                                color = Color(0XFFAFAFAF),
                                lineHeight = 1.em
                            )
                        }
                    }
                    Divider(
                        color = Color(0XFFE7E7E7), modifier = Modifier
                            .fillMaxHeight()
                            .width(1.dp)
                    )
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f)
                            .padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .background(
                                    Color(0XFFF1FCFA), RoundedCornerShape(999.dp)
                                )
                                .padding(12.dp)
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_moisture),
                                contentDescription = null,
                                tint = Color(0xFF59A7FF)
                            )
                        }
                        Column(
                            modifier = Modifier
                                .padding(start = 8.dp)
                                .height(intrinsicSize = IntrinsicSize.Max)
                        ) {
                            Text(
                                text = buildAnnotatedString {
                                    withStyle(
                                        style = SpanStyle(
                                            fontSize = 24.sp,
                                            fontWeight = FontWeight.Bold
                                        )
                                    ) {
                                        append("${plantNutritionData?.soilMoisture ?: 0}")
                                    }
                                },
                                color = Color(0xFF424242),
                                lineHeight = 1.em
                            )
                            Text(
                                text = "Kelembaban Tanah",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Medium,
                                color = Color(0XFFAFAFAF),
                                lineHeight = 1.em
                            )
                        }
                    }
                }
            }
        }
        AGDivider()
        Column(
            modifier = Modifier.padding(vertical = 24.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            AGCardWarning(message = "Pahami kebutuhan tanaman dan kondisi tanah dengan  memberikan nutrisi serta air yang sesuai!")
            AGTabBar {
                AGTab(titles = nutritionTab,
                    enumValues = nutritionTabContentValues,
                    tabSelected = selectedTNutritionTab,
                    onTabSelected = {
                        selectedTNutritionTab = it
                    })
            }

            when (selectedTNutritionTab) {
                NutritionTabContent.POTASSIUM -> {
                    HtmlText(text = stringResource(id = R.string.html_potassium_about))
                }

                NutritionTabContent.NITROGEN -> {
                    HtmlText(text = stringResource(id = R.string.html_nitrogen_about))
                }

                NutritionTabContent.PHOSPHORUS -> {
                    HtmlText(text = stringResource(id = R.string.html_phosphorus_about))
                }

                NutritionTabContent.MOISTURE -> {
                    HtmlText(text = stringResource(id = R.string.html_soil_moisture_about))
                }
            }
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun PlantNutritionContentPreview() {
    AgrimateTheme {
        PlantNutritionContent()
    }
}

