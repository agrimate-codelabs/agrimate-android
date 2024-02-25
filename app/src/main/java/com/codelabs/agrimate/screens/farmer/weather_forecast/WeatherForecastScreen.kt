package com.codelabs.agrimate.screens.farmer.weather_forecast

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.navigation.NavController
import com.codelabs.agrimate.R
import com.codelabs.agrimate.ui.components.AGDisinfectantRecommendationCard
import com.codelabs.agrimate.ui.components.AGDivider
import com.codelabs.agrimate.ui.components.AGTopAppBar
import com.codelabs.agrimate.ui.components.AGTopAppBarDefaultNavigationIcon
import com.codelabs.agrimate.ui.components.AGTopAppBarDefaultTitle
import com.codelabs.agrimate.ui.components.AGWeatherDayCard
import com.codelabs.agrimate.ui.components.AGWeatherHourCard
import com.codelabs.agrimate.ui.components.AGWeatherInfo
import com.codelabs.agrimate.ui.theme.Green500
import com.codelabs.agrimate.ui.theme.GreyScale100

@Composable
fun WeatherForecastScreen(modifier: Modifier = Modifier, navController: NavController) {
    val scrollState = rememberScrollState()

    val topAppBarColor by remember { derivedStateOf { if (scrollState.value > 10) Green500 else Color.Transparent } }

    Box(modifier = modifier) {
        TopAppBar(
            modifier
                .zIndex(999f),
            containerColor = topAppBarColor,
            onNavigationClick = { navController.popBackStack() })
        Column(modifier = Modifier.verticalScroll(scrollState)) {
            Box(modifier = Modifier) {
                TopBanner()
                Box(
                    Modifier
                        .padding(start = 25.dp, end = 25.dp, top = 100.dp)
                        .align(Alignment.Center)
                ) {
                    AGWeatherInfo(onRefresh = { /*TODO*/ })
                }
            }
            Column(
                modifier = Modifier.padding(vertical = 16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Box(modifier = Modifier.padding(horizontal = 24.dp)) {
                    AGDisinfectantRecommendationCard()
                }
                Column {
                    Text(
                        modifier = Modifier.padding(horizontal = 24.dp), text = "Berdasarkan Jam",
                        style = TextStyle(
                            fontSize = 18.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = GreyScale100
                        )
                    )
                    Spacer(Modifier.padding(bottom = 16.dp))
                    LazyRow(
                        contentPadding = PaddingValues(horizontal = 24.dp),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        items(24) {
                            AGWeatherHourCard()
                        }
                    }
                }
                AGDivider()
                Column(
                    modifier = Modifier.padding(horizontal = 24.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    repeat(6) {
                        AGWeatherDayCard()
                    }
                }
            }
        }
    }
}

@Composable
fun TopAppBar(modifier: Modifier = Modifier, containerColor: Color, onNavigationClick: () -> Unit) {
    AGTopAppBar(
        modifier = modifier,
        containerColor = containerColor,
        navigationIcon = { AGTopAppBarDefaultNavigationIcon(onClick = onNavigationClick) }) {
        AGTopAppBarDefaultTitle(text = "Perkiraan Cuaca")
    }
}

@Composable
fun TopBanner(modifier: Modifier = Modifier) {
    Image(
        modifier = modifier
            .fillMaxWidth()
            .zIndex(-1f),
        painter = painterResource(id = R.drawable.weather_forecast_bg_layer),
        contentDescription = null,
    )
}