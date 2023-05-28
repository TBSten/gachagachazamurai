package me.tbsten.gachagachazamurai

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import me.tbsten.gachagachazamurai.gacha.GachaScreen
import me.tbsten.gachagachazamurai.prize.PrizeListScreen
import me.tbsten.gachagachazamurai.qr.TwitterQrScreen
import me.tbsten.gachagachazamurai.top.TopScreen
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

    NavHost(navController = navController, startDestination = "top") {
        composable("top") {
            TopScreen()
        }
        composable("gacha") {
            GachaScreen()
        }
        composable("prize") {
            PrizeListScreen()
        }
        composable("qr") {
            TwitterQrScreen()
        }
    }

}
