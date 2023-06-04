package me.tbsten.gachagachazamurai.gacha

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import me.tbsten.gachagachazamurai.screens.Screen
import me.tbsten.gachagachazamurai.screens.composable

fun NavGraphBuilder.gachaScreen(navController: NavController) {
    composable(Screen.GachaScreen) {
        GachaScreenContent(
            backToTop = {
                navController.popBackStack(Screen.TopScreen.route, false)
            },
            renavigateGacha = {
                navController.navigate(Screen.GachaScreen.route)
            },
        )
    }
}
