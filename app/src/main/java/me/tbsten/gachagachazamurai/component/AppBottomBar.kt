package me.tbsten.gachagachazamurai.component

import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.runtime.Composable
import me.tbsten.gachagachazamurai.screens.Screen

private val items = listOf(
    Screen.GachaScreen,
    Screen.PrizeScreen,
    Screen.QrScreen,
)

@Composable
fun AppBottomBar(
    route: String?,
    onChangeRoute: (route: String) -> Unit,
) {
    NavigationBar {
        items.forEach {
            NavigationBarItem(
                icon = { it.icon?.invoke() },
                label = { it.name?.invoke() },
                selected = it.route === route,
                onClick = { onChangeRoute(it.route) },
            )
        }
    }
}