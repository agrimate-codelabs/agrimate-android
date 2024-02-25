package com.codelabs.agrimate

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.compose.rememberNavController
import com.codelabs.agrimate.ui.navigation.AgrimateNavGraph
import com.codelabs.agrimate.ui.theme.AgrimateTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.dark(android.graphics.Color.TRANSPARENT)
        )

        super.onCreate(savedInstanceState)

        setContent {
            val navController = rememberNavController()

            AgrimateTheme {
                Surface(modifier = Modifier.fillMaxSize(), color = Color.White) {
                    AgrimateNavGraph(
                        modifier = Modifier.navigationBarsPadding(),
                        navController = navController
                    )
                }
            }
        }
    }
}