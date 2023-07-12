package me.tbsten.gachagachazamurai.feature.settings

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import me.tbsten.gachagachazamurai.ui.component.ListItem
import me.tbsten.gachagachazamurai.ui.component.ScreenTitle
import me.tbsten.gachagachazamurai.ui.theme.GachaGachaZamuraiTheme

@Composable
fun SettingsScreen(
    gotoEditPrizeItemListScreen: () -> Unit,
    gotoEditTopImagesScreen: () -> Unit,
    gotoEditThanksScreen: () -> Unit,
) {
    LazyColumn(
        Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(4.dp, Alignment.Top)
    ) {
        item {
            ScreenTitle { Text("設定") }
        }

        item {
            ListItem(onClick = gotoEditPrizeItemListScreen) {
                Text("景品")
            }
        }
        item {
            ListItem(onClick = gotoEditTopImagesScreen) {
                Text("トップの背景画像")
            }
        }
        item {
            ListItem(onClick = gotoEditThanksScreen) {
                Text("Thanks")
            }
        }
    }
}

@Preview
@Composable
fun SettingScreenPreview() {
    GachaGachaZamuraiTheme {
        Surface {
            SettingsScreen(
                gotoEditPrizeItemListScreen = {},
                gotoEditTopImagesScreen = {},
                gotoEditThanksScreen = {},
            )
        }
    }
}
