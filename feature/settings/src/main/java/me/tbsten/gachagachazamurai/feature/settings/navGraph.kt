package me.tbsten.gachagachazamurai.feature.settings

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable

const val settingsRoute = "settings"

fun NavGraphBuilder.settings(navController: NavController) {
    composable(settingsRoute) {
        SettingsScreen(
            gotoEditPrizeItemListScreen = { TODO("navigate to edit prize item list screen") },
            gotoEditTopImagesScreen = { TODO("navigate to edit top image screen") },
            gotoEditThanksScreen = { TODO("navigate to edit thanks screen") },
        )
    }
}
