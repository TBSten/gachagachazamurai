package me.tbsten.gachagachazamurai.feature.settings

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable

const val settingsRoute = "settings"
const val editPrizeItemListRoute = "settings/editPrizeItemList"
const val editTopImagesRoute = "settings/editTopImages"
const val editThanksRoute = "settings/editThanksRoute"

fun NavGraphBuilder.settings(navController: NavController) {
    composable(settingsRoute) {
        SettingsScreen(
            gotoEditPrizeItemListScreen = { navController.navigate(editPrizeItemListRoute) },
            gotoEditTopImagesScreen = { navController.navigate(editTopImagesRoute) },
            gotoEditThanksScreen = { navController.navigate(editThanksRoute) },
        )
    }
    composable(editPrizeItemListRoute) {
        EditPrizeItemListScreen()
    }
    composable(editTopImagesRoute) {
        EditTopImagesScreen()
    }
    composable(editThanksRoute) {
        EditThanksScreen()
    }
}
