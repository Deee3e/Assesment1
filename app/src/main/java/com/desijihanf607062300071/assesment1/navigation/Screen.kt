package com.desijihanf607062300071.assesment1.navigation

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object About : Screen("about")
}
