package me.tbsten.gachagachazamurai

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import me.tbsten.gachagachazamurai.feature.settings.settings
import me.tbsten.gachagachazamurai.feature.settings.settingsRoute
import me.tbsten.gachagachazamurai.ui.theme.GachaGachaZamuraiTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GachaGachaZamuraiTheme {
                Surface {
                    AppRoutes()
                }
            }
        }
    }
}

@Composable
fun AppRoutes() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = settingsRoute,
    ) {
        settings(navController)
    }

}
