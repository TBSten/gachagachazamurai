package me.tbsten.gachagachazamurai.screens

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBalanceWallet
import androidx.compose.material.icons.filled.Checklist
import androidx.compose.material.icons.filled.SwipeDown
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import me.tbsten.gachagachazamurai.gacha.GachaScreenContent
import me.tbsten.gachagachazamurai.prize.PrizeListScreenContent
import me.tbsten.gachagachazamurai.qr.TwitterQrScreenContent
import me.tbsten.gachagachazamurai.top.TopScreenContent

sealed class Screen(
    val route: String,
    val icon: (@Composable () -> Unit)?,
    val name: (@Composable () -> Unit)?,
    val content: @Composable () -> Unit,
) {
    object TopScreen : Screen(
        route = "top",
        icon = null,
        name = null,
        content = {
            TopScreenContent()
        },
    )

    object GachaScreen : Screen(
        route = "gacha",
        icon = { Icon(Icons.Default.SwipeDown, contentDescription = "gacha") },
        name = { Text("ガチャ") },
        content = {
            GachaScreenContent()
        },
    )

    object PrizeScreen : Screen(
        route = "prize",
        icon = { Icon(Icons.Default.Checklist, contentDescription = "prize") },
        name = { Text("中身") },
        content = {
            PrizeListScreenContent()
        },
    )

    object QrScreen : Screen(
        route = "qr",
        icon = { Icon(Icons.Default.AccountBalanceWallet, contentDescription = "qr") },
        name = { Text("Twitter") },
        content = {
            TwitterQrScreenContent()
        },
    )

}

fun NavGraphBuilder.screenNavigation(screen: Screen) {
    this.composable(screen.route) {
        screen.content()
    }
}
