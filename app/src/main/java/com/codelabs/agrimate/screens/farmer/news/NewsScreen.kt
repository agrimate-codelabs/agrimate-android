package com.codelabs.agrimate.screens.farmer.news

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.codelabs.agrimate.ui.components.AGInputSearch
import com.codelabs.agrimate.ui.components.AGNewsCard
import com.codelabs.agrimate.ui.components.AGTopAppBar
import com.codelabs.agrimate.ui.components.AGTopAppBarDefaultNavigationIcon
import com.codelabs.agrimate.ui.components.AGTopAppBarDefaultTitle
import com.codelabs.agrimate.ui.components.shimmer.shimmerBrush
import com.codelabs.agrimate.ui.navigation.AGRoute
import com.codelabs.agrimate.ui.navigation.DestinationsArg
import com.codelabs.core.domain.model.NewsModel
import com.codelabs.core.utils.Resource
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

@Composable
fun NewsScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    viewModel: NewsViewModel = hiltViewModel()
) {
    val news by viewModel.news.observeAsState()

    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(onNavigationClick = {
                navController.popBackStack()
            })
        },
        contentWindowInsets = WindowInsets(bottom = 0.dp)
    ) { paddingValues ->
        NewsContent(
            modifier = Modifier.padding(paddingValues),
            navigateToDetail = { url, title ->
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
            news = news
        )
    }
}

@Composable
private fun NewsContent(
    modifier: Modifier = Modifier,
    navigateToDetail: (String, String) -> Unit,
    news: Resource<List<NewsModel>>?
) {
    Column(modifier = modifier) {
        Box(
            modifier = Modifier.padding(
                start = 18.dp,
                end = 18.dp,
                bottom = 18.dp,
                top = 24.dp
            )
        ) {
            AGInputSearch(
                modifier = Modifier.fillMaxWidth(),
                value = "",
                onValueChange = {},
                placeholder = "Cari berdasarkan desa"
            )
        }
        LazyColumn(
            contentPadding = PaddingValues(start = 18.dp, end = 18.dp, bottom = 18.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            when (news) {
                is Resource.Loading -> {
                    items(3) {
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

                is Resource.Success -> {
                    items(news.data ?: emptyList(), key = { it.url }) {
                        AGNewsCard(
                            onClick = {
                                navigateToDetail(it.url, it.title)
                            },
                            title = it.title,
                            date = it.date,
                            source = it.source,
                            image = it.image
                        )
                    }
                }

                else -> {}
            }
        }
    }
}

@Composable
fun TopAppBar(
    modifier: Modifier = Modifier,
    onNavigationClick: () -> Unit,
) {
    AGTopAppBar(
        modifier = modifier,
        navigationIcon = { AGTopAppBarDefaultNavigationIcon(onClick = onNavigationClick) }
    ) {
        AGTopAppBarDefaultTitle(text = "Berita")
    }
}