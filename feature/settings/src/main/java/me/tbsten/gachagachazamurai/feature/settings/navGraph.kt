package me.tbsten.gachagachazamurai.feature.settings

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import me.tbsten.gachagachazamurai.feature.settings.prizeItem.AddPrizeItemScreen
import me.tbsten.gachagachazamurai.feature.settings.prizeItem.EditPrizeItemListScreen
import me.tbsten.gachagachazamurai.feature.settings.thanks.EditThanksScreen
import me.tbsten.gachagachazamurai.feature.settings.topImages.EditTopImagesScreen

const val settingsRoute = "settings"
const val editPrizeItemListRoute = "settings/editPrizeItemList"
const val addPrizeItemRoute = "settings/addPrizeItem"
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
    editPrizeItem(navController)
    composable(editTopImagesRoute) {
        EditTopImagesScreen()
    }
    composable(editThanksRoute) {
        EditThanksScreen()
    }
}

private fun NavGraphBuilder.editPrizeItem(navController: NavController) {
    composable(editPrizeItemListRoute) {
        EditPrizeItemListScreen(
            gotoAddPrizeItemScreen = { navController.navigate(addPrizeItemRoute) },
        )
    }
    composable(addPrizeItemRoute) {
        AddPrizeItemScreen()
    }
}
