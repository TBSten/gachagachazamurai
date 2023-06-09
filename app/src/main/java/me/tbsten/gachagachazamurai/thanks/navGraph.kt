package me.tbsten.gachagachazamurai.thanks

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import me.tbsten.gachagachazamurai.screens.Screen
import me.tbsten.gachagachazamurai.screens.composable

fun NavGraphBuilder.thanksScreen(navController: NavController) {
    composable(Screen.ThanksScreen) {
        ThanksScreenContent()
    }
}