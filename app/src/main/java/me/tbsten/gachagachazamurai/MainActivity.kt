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
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import me.tbsten.gachagachazamurai.component.BottomBar
import me.tbsten.gachagachazamurai.gacha.gachaScreen
import me.tbsten.gachagachazamurai.prize.prizeScreen
import me.tbsten.gachagachazamurai.qr.qrScreen
import me.tbsten.gachagachazamurai.screens.Screen
import me.tbsten.gachagachazamurai.screens.settingScreen
import me.tbsten.gachagachazamurai.top.topScreen
import me.tbsten.gachagachazamurai.ui.theme.GachaGachaZamuraiTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GachaGachaZamuraiTheme {
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

    Scaffold(
        bottomBar = {
            BottomBar(navController)
        },
    ) {
        Box(Modifier.padding(it)) {
            NavHost(navController, startDestination = Screen.TopScreen.route) {
                topScreen(navController)
                gachaScreen(navController)
                prizeScreen(navController)
                qrScreen(navController)
                settingScreen(navController)
            }
        }
    }

}


