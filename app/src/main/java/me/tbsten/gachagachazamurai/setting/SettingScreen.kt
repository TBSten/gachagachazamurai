package me.tbsten.gachagachazamurai.setting

import androidx.compose.foundation.clickable
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun SettingScreenContent(
    settingViewModel: SettingViewModel = hiltViewModel(),
    gotoTopImageEditScreen: () -> Unit,
) {

    LazyColumn {

        item {
            ListItem(
                headlineContent = {
                    Text("トップ画面の画像")
                },
                modifier = Modifier.clickable {
                    gotoTopImageEditScreen()
                },
            )
        }

    }
}

