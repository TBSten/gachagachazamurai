package me.tbsten.gachagachazamurai.ui.screen

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder

interface Screen {
    val route: String
    fun NavGraphBuilder.navGraph(navController: NavController)
}

fun NavGraphBuilder.screen(
    navController: NavController,
    screen: Screen,
) {
    with(screen) { navGraph(navController) }
}
