package me.tbsten.gachagachazamurai.feature.settings

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import me.tbsten.gachagachazamurai.domain.PrizeItem
import me.tbsten.gachagachazamurai.ui.component.ListItem
import me.tbsten.gachagachazamurai.ui.component.ScreenTitle

@Composable
fun EditPrizeItemListScreen(
    editViewModel: EditPrizeItemListViewModel = hiltViewModel(),
    gotoAddPrizeItemScreen: () -> Unit,
) {
    val prizeItems = editViewModel.prizeItems.collectAsState().value

    LazyColumn {
        item {
            ScreenTitle { Text("景品") }
        }
        if (prizeItems != null) {
            items(prizeItems) { prizeItem ->
                PrizeItemListItem(prizeItem)
            }
        } else {
            item {
                NonPrizeItems()
            }
        }
        item {
            Box(Modifier.fillMaxWidth().padding(8.dp), contentAlignment = Alignment.Center) {
                AddButton(onAdd = gotoAddPrizeItemScreen)
            }
        }
    }
}

@Composable
private fun PrizeItemListItem(prizeItem: PrizeItem) {
    ListItem {
        Text(prizeItem.name)
    }

}

@Composable
private fun NonPrizeItems() {
    Box(
        Modifier.fillMaxWidth().heightIn(min = 300.dp),
        contentAlignment = Alignment.Center
    ) {
        Text("景品がありません")
    }
}

@Composable
private fun AddButton(
    onAdd: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Button(
        modifier = modifier,
        onClick = onAdd,
    ) {
        Icon(
            Icons.Filled.Add,
            contentDescription = "Favorite",
            modifier = Modifier.size(ButtonDefaults.IconSize)
        )
        Spacer(Modifier.size(ButtonDefaults.IconSpacing))
        Text("追加")
    }
}
