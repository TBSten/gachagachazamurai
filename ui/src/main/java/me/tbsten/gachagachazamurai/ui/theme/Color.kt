package me.tbsten.gachagachazamurai.ui.theme

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

internal val LightColorScheme = lightColorScheme(
    primary = Color(0xFF7A51EF),
    primaryContainer = Color(0xFFE5DEFA),
    secondary = Color(0xFF0FC357),
    secondaryContainer = Color(0xFFF2FFF7),
    tertiary = Color(0xFFFB23F3),
    tertiaryContainer = Color(0xFFFFFFED),
    background = Color(0xFFFFFBFE),

    /* Other default colors to override
    background = Color(0xFFFFFBFE),
    surface = Color(0xFFFFFBFE),
    onPrimary = Color.White,
    onSecondary = Color.White,
    onTertiary = Color.White,
    onBackground = Color(0xFF1C1B1F),
    onSurface = Color(0xFF1C1B1F),
    */
)

internal val DarkColorScheme = LightColorScheme


@Preview
@Composable
fun LightColorPreview() {
    GachaGachaZamuraiTheme(darkTheme = false) {
        Surface(
            color = MaterialTheme.colorScheme.background,
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
            ) {
                Column(Modifier.background(LightColorScheme.primaryContainer).padding(32.dp)) {
                    Button(
                        onClick = {},
                        colors = ButtonDefaults.buttonColors(
                            containerColor = LightColorScheme.primary,
                            contentColor = LightColorScheme.onPrimary,
                        ),
                    ) { Text("primary") }
                }

                Column(Modifier.background(LightColorScheme.secondaryContainer).padding(32.dp)) {
                    Button(
                        onClick = {},
                        colors = ButtonDefaults.buttonColors(
                            containerColor = LightColorScheme.secondary,
                            contentColor = LightColorScheme.onSecondary,
                        ),
                    ) { Text("secondary") }
                }

                Column(Modifier.background(LightColorScheme.tertiaryContainer).padding(32.dp)) {
                    Button(
                        onClick = {},
                        colors = ButtonDefaults.buttonColors(
                            containerColor = LightColorScheme.tertiary,
                            contentColor = LightColorScheme.onTertiary,
                        ),
                    ) { Text("tertiary") }
                }

            }

        }

    }
}
