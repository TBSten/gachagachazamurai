package me.tbsten.gachagachazamurai.ui.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import me.tbsten.gachagachazamurai.ui.theme.GachaGachaZamuraiTheme

@Composable
fun ScreenTitle(
    title: @Composable () -> Unit,
) {
    Box(
        modifier = Modifier.padding(16.dp).fillMaxWidth().heightIn(min = 200.dp),
        contentAlignment = Alignment.CenterStart,
    ) {
        CompositionLocalProvider(
            LocalTextStyle provides MaterialTheme.typography.displayLarge,
        ) {
            title()
        }
    }
}

@Preview
@Composable
fun ScreenTitlePreview() {
    GachaGachaZamuraiTheme {
        Surface {
            ScreenTitle(
                title = { Text("設定") }
            )
        }
    }
}
