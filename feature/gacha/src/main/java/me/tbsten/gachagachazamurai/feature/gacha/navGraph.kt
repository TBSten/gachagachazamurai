package me.tbsten.gachagachazamurai.feature.gacha

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import me.tbsten.gachagachazamurai.feature.gacha.gacha.GachaPlayScreen
import me.tbsten.gachagachazamurai.feature.gacha.prizeList.PrizeListScreen

const val gachaRoute = "gacha"
const val prizeListRoute = "gacha/prizeList"
const val gachaPlayRoute = "gacha/play"

fun NavGraphBuilder.gacha(
    navController: NavController,
    backTop: () -> Unit,
) {
    navigation(route = gachaRoute, startDestination = prizeListRoute) {
        composable(prizeListRoute) {
            PrizeListScreen(
                gotoGachaPlay = { navController.navigate(gachaPlayRoute) },
            )
        }
        composable(gachaPlayRoute) {
            GachaPlayScreen(
                renavigate = {
                    navController.popBackStack()
                    navController.navigate(gachaPlayRoute)
                },
                backTop = backTop,
            )
        }
    }
}
