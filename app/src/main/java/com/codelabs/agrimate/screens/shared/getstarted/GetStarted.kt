package com.codelabs.agrimate.screens.shared.getstarted

import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.navigation.NavController
import com.codelabs.agrimate.R
import com.codelabs.agrimate.ui.components.AGButton
import com.codelabs.agrimate.ui.navigation.AGRoute
import com.codelabs.agrimate.ui.theme.Green100
import com.codelabs.agrimate.ui.theme.Green500
import com.codelabs.agrimate.ui.theme.GreyScale600
import kotlinx.coroutines.launch

internal data class GetStartedData(val image: Int, val title: String, val desc: String)

private val getStartedData = listOf(
    GetStartedData(
        R.drawable.onboard_1_picture,
        "Asisten terbaik untuk pertanian Anda",
        "Kelola aktivitas tani jadi lebih mudah dengan fitur penunjang yang otomatis"
    ),
    GetStartedData(
        R.drawable.onboard_2_picture,
        "Dapatkan hasil tani yang berkualitas",
        "Fitur rekomendasi tanam dan panen dapat mendukung profitabilitas dan produktivitas"
    ),
)

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun GetStartedScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
) {
    val coroutineScope = rememberCoroutineScope()
    val pagerState = rememberPagerState(pageCount = { getStartedData.size })

    val handleChangeActivePage: (Int) -> Unit = {
        coroutineScope.launch {
            pagerState.animateScrollToPage(it)
        }
    }

    Scaffold(
        modifier = modifier,
        containerColor = Green500,
        contentWindowInsets = WindowInsets(top = 0, bottom = 0),
        bottomBar = {
            BottomAction(pagerState, navController, handleChangeActivePage = {
                handleChangeActivePage(it)
            })
        }) { contentPadding ->
        Column(
            modifier = Modifier
                .padding(contentPadding)
                .fillMaxSize()
                .background(Green500)
                .verticalScroll(rememberScrollState())
        ) {
            Box(modifier = Modifier.fillMaxWidth()) {
                Image(
                    modifier = Modifier
                        .fillMaxWidth()
                        .zIndex(1f),
                    painter = painterResource(id = R.drawable.onboard_ellipse_bg),
                    contentDescription = null
                )
                Crossfade(
                    modifier = Modifier
                        .fillMaxWidth()
                        .zIndex(2f),
                    targetState = pagerState.currentPage, animationSpec = tween(1000),
                    label = "page_image"
                ) { targetState ->
                    Image(
                        modifier = Modifier
                            .fillMaxWidth()
                            .zIndex(2f),
                        painter = painterResource(getStartedData[targetState].image),
                        contentDescription = null
                    )
                }
            }
            Spacer(modifier = Modifier.padding(bottom = 39.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(
                    space = 12.dp,
                    alignment = Alignment.CenterHorizontally
                )
            ) {
                repeat(2) { index ->
                    Box(
                        modifier = Modifier
                            .width(40.dp)
                            .height(5.dp)
                            .background(
                                color = if (index == pagerState.currentPage) Color.White else Green100,
                                shape = RoundedCornerShape(size = 20.dp)
                            )
                            .clickable {
                                coroutineScope.launch {
                                    handleChangeActivePage(index)
                                }
                            }
                    )
                }
            }
            Spacer(modifier = Modifier.padding(bottom = 38.dp))
            HorizontalPager(
                modifier = Modifier.fillMaxWidth(),
                state = pagerState,
                verticalAlignment = Alignment.Top
            ) { index ->
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 18.dp)
                ) {
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        fontSize = 30.sp,
                        lineHeight = 38.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White,
                        textAlign = TextAlign.Center,
                        text = getStartedData[index].title
                    )
                    Spacer(modifier = Modifier.padding(bottom = 21.dp))
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        fontSize = 14.sp,
                        lineHeight = (27.3).sp,
                        fontWeight = FontWeight.Medium,
                        color = GreyScale600,
                        textAlign = TextAlign.Center,
                        text = getStartedData[index].desc
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun BottomAction(
    pagerState: PagerState,
    navController: NavController,
    handleChangeActivePage: (Int) -> Unit
) {
    Box(modifier = Modifier.padding(horizontal = 18.dp, vertical = 12.dp)) {
        AGButton(
            modifier = Modifier.fillMaxWidth(),
            onClick = {
                if (pagerState.currentPage < (getStartedData.size - 1)) {
                    handleChangeActivePage(pagerState.currentPage + 1)
                } else {
                    navController.currentDestination?.id?.let {
                        navController.popBackStack(
                            it,
                            true
                        )
                    }
                    navController.navigate(AGRoute.Auth.SignIn.route)
                }
            },
            contentPadding = PaddingValues(horizontal = 40.dp, vertical = 20.dp)
        ) {
            Text(
                text = if (pagerState.currentPage < (getStartedData.size - 1)) "Selanjutnya" else "Mulai",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}