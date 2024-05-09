package com.devx.streaming

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.devx.streaming.ui.screens.HomeScreen
import com.devx.streaming.ui.screens.VideoScreen
import com.devx.streaming.ui.theme.OnlineVideosTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            OnlineVideosTheme {
                OnlineVideosApp()
            }
        }
    }
}

@Composable
fun OnlineVideosApp() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Routes.HOME) {
        composable(Routes.HOME) {
            navController.currentBackStackEntry?.savedStateHandle?.remove<Int>("videoId")

            HomeScreen(onVideoClick = {
                navController.currentBackStackEntry?.savedStateHandle?.set("videoId", it)
                navController.navigate(Routes.VIDEO)
            })
        }

        composable(Routes.VIDEO) {
            val videoId = navController.previousBackStackEntry?.savedStateHandle?.get<Int>("videoId")
            videoId?.let {
                VideoScreen(videoId =  it)
            }
        }
    }
}

object Routes {
    const val HOME = "home_screen"
    const val VIDEO = "video_screen"
}