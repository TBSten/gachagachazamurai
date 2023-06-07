package me.tbsten.gachagachazamurai.setting

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ListItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingScreenContent(
    settingViewModel: SettingViewModel = hiltViewModel(),
    gotoTopImageEditScreen: () -> Unit,
    gotoThanksEditScreen: () -> Unit,
) {

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("設定") },
            )
        },
    ) {

        LazyColumn(modifier = Modifier.padding(it)) {

            item {
                ListItem(
                    headlineContent = {
                        Text("トップ画面の画像")
                    },
                    modifier = Modifier.clickable {
                        gotoTopImageEditScreen()
                    },
                )
                ListItem(
                    headlineContent = {
                        Text("Thanks")
                    },
                    modifier = Modifier.clickable {
                        gotoThanksEditScreen()
                    },
                )
            }

        }

    }
}

