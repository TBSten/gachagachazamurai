package me.tbsten.gachagachazamurai

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import me.tbsten.gachagachazamurai.component.AppBottomBar
import me.tbsten.gachagachazamurai.gacha.GachaScreenContent
import me.tbsten.gachagachazamurai.prize.PrizeListScreenContent
import me.tbsten.gachagachazamurai.qr.TwitterQrScreenContent
import me.tbsten.gachagachazamurai.screens.Screen
import me.tbsten.gachagachazamurai.screens.composable
import me.tbsten.gachagachazamurai.top.TopScreenContent
import me.tbsten.gachagachazamurai.ui.theme.GachaGachaZamuraiTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GachaGachaZamuraiTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    AppRoot()
                }
            }
        }
    }
}

@Composable
fun AppRoot() {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    Scaffold(
        bottomBar = {
            AppBottomBar(
                isSelected = { route ->
                    currentDestination?.hierarchy?.any { it.route == route } == true
                },
                onChangeRoute = {
                    navController.navigate(it) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
            )
        }
    ) {
        Box(Modifier.padding(it)) {
            NavHost(navController = navController, startDestination = "top") {
                composable(Screen.TopScreen) {
                    TopScreenContent(
                        gotoGachaScreen = { navController.navigate(Screen.GachaScreen.route) },
                        gotoPrizeScreen = { navController.navigate(Screen.PrizeScreen.route) },
                    )
                }
                composable(Screen.GachaScreen) {
                    GachaScreenContent(
                        backToTop = {
                            navController.popBackStack(Screen.TopScreen.route, false)
                        },
                        renavigateGacha = {
                            navController.navigate(Screen.GachaScreen.route)
                        },
                    )
                }
                composable(Screen.PrizeScreen) {
                    PrizeListScreenContent()
                }
                composable(Screen.QrScreen) {
                    TwitterQrScreenContent()
                }
            }
        }
    }

}
