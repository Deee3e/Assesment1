package com.desijihanf607062300071.assesment1.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.desijihanf607062300071.assesment1.ui.screen.AboutScreen
import com.desijihanf607062300071.assesment1.ui.screen.MainScreen
import com.desijihanf607062300071.assesment1.ui.screen.ScreenContent

@Composable
fun SetupNavGraph(navController : NavHostController = rememberNavController()) {
    NavHost(
        navController = navController,
        startDestination = Screen.Home.route
    ) {
        composable(route = Screen.Home.route) {
            MainScreen(navController)
        }
        composable(route = Screen.About.route) {
            AboutScreen(navController)
        }
    }

}


