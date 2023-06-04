package me.tbsten.gachagachazamurai.top

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import me.tbsten.gachagachazamurai.screens.Screen
import me.tbsten.gachagachazamurai.screens.composable

fun NavGraphBuilder.topScreen(navController: NavController) {
    composable(Screen.TopScreen) {
        TopScreenContent(
            gotoGachaScreen = { navController.navigate(Screen.GachaScreen.route) },
            gotoPrizeScreen = { navController.navigate(Screen.PrizeScreen.route) },
        )
    }
}
