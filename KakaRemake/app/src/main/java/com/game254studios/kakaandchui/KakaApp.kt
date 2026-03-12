package com.game254studios.kakaandchui

import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import com.game254studios.kakaandchui.navigation.NavGraph

@Composable
fun KakaApp() {
    val navController = rememberNavController()
    NavGraph(navController = navController)
}
