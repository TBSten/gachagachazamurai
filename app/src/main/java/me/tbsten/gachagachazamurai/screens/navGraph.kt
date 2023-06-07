package me.tbsten.gachagachazamurai.screens

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import me.tbsten.gachagachazamurai.setting.SettingScreenContent
import me.tbsten.gachagachazamurai.setting.thanksEdit.ThanksEditContent
import me.tbsten.gachagachazamurai.setting.topImageEdit.TopImageEditContent


fun NavGraphBuilder.settingScreen(navController: NavController) {
    fun backToSetting() {
        navController.popBackStack(Screen.SettingScreen.route, false)
    }

    composable(Screen.SettingScreen) {
        SettingScreenContent(
            gotoTopImageEditScreen = {
                navController.navigate(Screen.SettingScreen.TopImageEdit.route)
            },
            gotoThanksEditScreen = {
                navController.navigate(Screen.SettingScreen.ThanksEdit.route)
            },
        )
    }
    composable(Screen.SettingScreen.TopImageEdit) {
        TopImageEditContent(
            backToSetting = ::backToSetting,
        )
    }
    composable(Screen.SettingScreen.ThanksEdit) {
        ThanksEditContent(
            backToSetting = ::backToSetting,
        )
    }
}