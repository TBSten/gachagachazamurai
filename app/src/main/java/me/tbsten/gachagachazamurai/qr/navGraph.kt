package me.tbsten.gachagachazamurai.qr

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import me.tbsten.gachagachazamurai.screens.Screen
import me.tbsten.gachagachazamurai.screens.composable

fun NavGraphBuilder.qrScreen(navController: NavController) {
    composable(Screen.QrScreen) {
        TwitterQrScreenContent()
    }
}