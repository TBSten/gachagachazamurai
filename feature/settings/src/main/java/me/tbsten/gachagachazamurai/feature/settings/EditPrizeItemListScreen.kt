package me.tbsten.gachagachazamurai.feature.settings

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import me.tbsten.gachagachazamurai.ui.component.ListItem
import me.tbsten.gachagachazamurai.ui.component.ScreenTitle

@Composable
fun EditPrizeItemListScreen(
    editViewModel: EditPrizeItemListViewModel = hiltViewModel(),
) {
    val prizeItems = editViewModel.prizeItems.collectAsState().value

    LazyColumn {
        item {
            ScreenTitle { Text("景品") }
        }
        if (prizeItems != null)
            items(prizeItems) { prizeItem ->
                ListItem {
                    Text(prizeItem.name)
                }
            }
    }
}
