package me.tbsten.gachagachazamurai.prize

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import me.tbsten.gachagachazamurai.screens.Screen

fun NavGraphBuilder.prizeScreen(navController: NavController) {
    navigation(route = Screen.PrizeScreen.route, startDestination = "list") {
        composable(route = "list") {
            PrizeListScreenContent(
                showNewPrizeDialog = { navController.navigate("new") }
            )
        }
        composable(route = "new") {
            NewPrizeDialog(
                backPrevScreen = { navController.popBackStack("list", false) },
            )
        }
    }
}
