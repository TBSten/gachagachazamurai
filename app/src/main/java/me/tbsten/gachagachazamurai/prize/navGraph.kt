package me.tbsten.gachagachazamurai.prize

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import me.tbsten.gachagachazamurai.screens.Screen
import me.tbsten.gachagachazamurai.screens.composable

fun NavGraphBuilder.prizeScreen(navController: NavController) {
    composable(Screen.PrizeScreen) {
        PrizeListScreenContent()
    }
}
