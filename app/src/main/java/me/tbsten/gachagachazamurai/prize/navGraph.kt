package me.tbsten.gachagachazamurai.prize

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import me.tbsten.gachagachazamurai.prize.create.NewPrizeDialog
import me.tbsten.gachagachazamurai.prize.edit.PrizeEditScreenContent
import me.tbsten.gachagachazamurai.prize.list.PrizeListScreenContent
import me.tbsten.gachagachazamurai.screens.Screen

fun NavGraphBuilder.prizeScreen(navController: NavController) {
    navigation(route = Screen.PrizeScreen.route, startDestination = "list") {
        composable(route = "list") {
            PrizeListScreenContent(
                gotoEditPrize = {
                    navController.navigate("edit")
                },
            )
        }
        composable(route = "edit") {
            PrizeEditScreenContent(
                gotoNewPrize = {
                    navController.navigate("create")
                },
            )
        }
        composable(route = "create") {
            NewPrizeDialog(
                backPrevScreen = { navController.popBackStack("list", false) },
            )
        }
    }
}
