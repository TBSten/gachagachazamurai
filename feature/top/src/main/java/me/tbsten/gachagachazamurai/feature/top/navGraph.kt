package me.tbsten.gachagachazamurai.feature.top

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import me.tbsten.gachagachazamurai.feature.gacha.gachaRoute

const val topRoute = "top"

fun NavGraphBuilder.top(navController: NavController) {
    composable(topRoute) {
        TopScreen(
            gotoGachaScreen = {
                navController.navigate(gachaRoute)
            },
        )
    }
}
