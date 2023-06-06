package me.tbsten.gachagachazamurai.screens

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import me.tbsten.gachagachazamurai.setting.SettingScreenContent


fun NavGraphBuilder.settingScreen(navController: NavController) {
    composable(Screen.QrScreen) {
        SettingScreenContent()
    }
}