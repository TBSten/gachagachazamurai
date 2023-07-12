package me.tbsten.gachagachazamurai

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import me.tbsten.gachagachazamurai.feature.settings.settings
import me.tbsten.gachagachazamurai.feature.settings.settingsRoute

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppRoutes()
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
