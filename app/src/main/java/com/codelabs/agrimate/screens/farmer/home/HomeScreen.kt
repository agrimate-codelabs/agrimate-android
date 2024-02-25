package com.codelabs.agrimate.screens.farmer.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import com.codelabs.agrimate.R
import com.codelabs.agrimate.ui.common.menu.farmerNavBarMenu
import com.codelabs.agrimate.ui.common.state.UiState
import com.codelabs.agrimate.ui.components.AGDivider
import com.codelabs.agrimate.ui.components.AGExploreMenu
import com.codelabs.agrimate.ui.components.AGNavBarMenuItem
import com.codelabs.agrimate.ui.components.AGNavbar
import com.codelabs.agrimate.ui.components.AGNewsCard
import com.codelabs.agrimate.ui.components.AGWeatherInformation
import com.codelabs.agrimate.ui.components.model.ExploreMenuModel
import com.codelabs.agrimate.ui.components.shimmer.shimmerBrush
import com.codelabs.agrimate.ui.navigation.AGRoute
import com.codelabs.agrimate.ui.navigation.DestinationsArg
import com.codelabs.agrimate.ui.theme.Green300
import com.codelabs.agrimate.ui.theme.GreyScale100
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

private val exploreMenu = listOf(
    ExploreMenuModel(
        R.drawable.ag_plan_icon,
        "Bantu Tanam",
        AGRoute.Farmer.Main.Home.Explorer.HelpPlant.route
    ),
    ExploreMenuModel(
        R.drawable.ag_harvest_icon,
        "Bantu Panen",
        AGRoute.Farmer.Main.Home.Explorer.HelpHarvest.route
    ),
    ExploreMenuModel(
        R.drawable.ag_money_icon,
        "Pemodalan",
        AGRoute.Farmer.Main.Home.Explorer.Capital.route
    ),
    ExploreMenuModel(
        R.drawable.ag_discussion_icon,
        "Diskusi",
        AGRoute.Farmer.Main.Home.Explorer.Discussion.route
    ),
)

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val scrollState = rememberScrollState()

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    val uiState = viewModel.uiState.collectAsStateWithLifecycle()

    Scaffold(
        modifier = modifier,
        contentWindowInsets = WindowInsets.systemBars.only(WindowInsetsSides.Horizontal + WindowInsetsSides.Bottom),
        bottomBar = {
            AGNavbar(modifier = Modifier.fillMaxWidth()) {
                farmerNavBarMenu.forEach { menu ->
                    AGNavBarMenuItem(
                        modifier = Modifier.weight(1f),
                        data = menu,
                        onClick = {
                            navController.navigate(menu.link) {
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        },
                        selected = currentDestination?.hierarchy?.any { it.route == menu.link } == true)
                }
            }
        }
    ) { paddingValues ->
        HomeContent(
            modifier = Modifier
                .fillMaxWidth()
                .padding(paddingValues)
                .verticalScroll(scrollState),
            navigateToNotification = { navController.navigate(AGRoute.Farmer.Main.Notification.route) },
            navigateToNews = { navController.navigate(AGRoute.Farmer.Main.News.route) },
            navigateToWeatherForecast = { navController.navigate(AGRoute.Farmer.Main.WeatherForecast.route) },
            navigateToNewsDetail = { url, title ->
                navController.navigate(
                    AGRoute.Farmer.Main.News.Detail.route.replace(
                        "{${DestinationsArg.NEWS_URL_ARG}}",
                        URLEncoder.encode(url, StandardCharsets.UTF_8.toString())
                    ).replace(
                        "{${DestinationsArg.NEWS_TITLE_ARG}}",
                        URLEncoder.encode(title, StandardCharsets.UTF_8.toString())
                    )
                )
            },
            navigateToOther = { link -> navController.navigate(link) },
            uiState = uiState.value
        )
    }

}

@Composable
private fun HomeContent(
    modifier: Modifier = Modifier,
    navigateToNotification: () -> Unit,
    navigateToNews: () -> Unit,
    navigateToWeatherForecast: () -> Unit,
    navigateToNewsDetail: (String, String) -> Unit,
    navigateToOther: (String) -> Unit,
    uiState: HomeUiState
) {
    Column(modifier) {
        Box {
            Image(
                modifier = Modifier
                    .fillMaxWidth()
                    .zIndex(-1f),
                painter = painterResource(id = R.drawable.home_banner_layer),
                contentDescription = null,
            )
            Column(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 22.dp)
                ) {
                    Spacer(modifier = Modifier.padding(bottom = 53.dp))
                    HomeHeading(
                        modifier = Modifier,
                        navigateToNotification = navigateToNotification,
                        username = uiState.username,
                        currentDate = uiState.currentDate
                    )
                    Spacer(modifier = Modifier.padding(bottom = 21.dp))
                    AGWeatherInformation(onClick = navigateToWeatherForecast)
                    Spacer(modifier = Modifier.padding(bottom = 19.dp))
                }
                HomeMainContent(
                    navigateToNews = navigateToNews,
                    navigateToNewsDetail = navigateToNewsDetail,
                    navigateToOther = navigateToOther,
                    uiState = uiState
                )
            }
        }
    }
}

@Composable
private fun HomeHeading(
    modifier: Modifier = Modifier,
    navigateToNotification: () -> Unit,
    username: String,
    currentDate: String
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        HomeTitle(username = username, currentDate = currentDate)
        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(8.dp))
                .background(Green300)
                .clickable {
                    navigateToNotification()
                }
                .padding(10.dp),
        ) {
            Icon(
                modifier = Modifier.size(20.dp),
                imageVector = Icons.Outlined.Notifications,
                contentDescription = "notifikasi",
                tint = Color.White
            )
        }
    }
}

@Composable
private fun HomeTitle(modifier: Modifier = Modifier, username: String, currentDate: String) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(5.dp)
    ) {
        Text(
            text = "Hallo ${username.split(" ")[0]}",
            color = Color.White,
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp
        )
        Text(
            text = currentDate,
            color = Color.White,
            fontWeight = FontWeight.Medium,
            fontSize = 12.sp
        )
    }
}

@Composable
private fun HomeMainContent(
    modifier: Modifier = Modifier,
    navigateToNews: () -> Unit,
    navigateToNewsDetail: (String, String) -> Unit,
    navigateToOther: (String) -> Unit,
    uiState: HomeUiState
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .defaultMinSize(minHeight = 200.dp)
            .background(
                Color.White,
                RoundedCornerShape(topEnd = 16.dp, topStart = 16.dp)
            )
            .padding(top = 24.dp)
    ) {
        Column(Modifier.fillMaxWidth()) {
            Box(modifier = Modifier.padding(horizontal = 18.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    exploreMenu.forEach { menu ->
                        AGExploreMenu(menu = menu, onClick = {
                            navigateToOther(menu.link)
                        })
                    }
                }
            }
            Spacer(modifier = Modifier.padding(bottom = 24.dp))
            AGDivider()
            Column(modifier = Modifier.padding(horizontal = 18.dp, vertical = 24.dp)) {
                HomeSectionTitle(navigateToNews = navigateToNews)
                Spacer(modifier = Modifier.padding(bottom = 18.dp))
                Column(verticalArrangement = Arrangement.spacedBy(18.dp)) {
                    when (uiState.news) {
                        is UiState.Error -> {/*TODO*/
                        }

                        is UiState.Loading -> {
                            repeat(3) {
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(121.dp)
                                        .background(
                                            shimmerBrush(),
                                            RoundedCornerShape(12.dp)
                                        )
                                )
                            }
                        }

                        is UiState.Success -> {
                            uiState.news.data.forEach {
                                AGNewsCard(
                                    onClick = {
                                        navigateToNewsDetail(it.url, it.title)
                                    },
                                    title = it.title,
                                    date = it.date,
                                    source = it.source,
                                    image = it.image
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
private fun HomeSectionTitle(
    modifier: Modifier = Modifier,
    navigateToNews: () -> Unit
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = "Berita Terkini",
            fontSize = 18.sp,
            fontWeight = FontWeight.SemiBold,
            color = GreyScale100
        )
        Row(modifier = Modifier.clickable {
            navigateToNews()
        }, verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = "Selengkapnya",
                color = Color(0xFF598AD0),
                fontSize = 14.sp,
                lineHeight = 19.6.sp,
                fontWeight = FontWeight.Bold
            )
            Icon(
                modifier = Modifier.size(13.dp),
                imageVector = Icons.Default.ArrowForward,
                contentDescription = null,
                tint = Color(0xFF598AD0)
            )
        }
    }
}