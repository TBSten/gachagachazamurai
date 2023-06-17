package me.tbsten.gachagachazamurai.prize.list

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import me.tbsten.gachagachazamurai.prize.PrizeListViewModel

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun PrizeListScreenContent(
    prizeListViewModel: PrizeListViewModel = hiltViewModel(),
    gotoEditPrize: () -> Unit,
) {
    LaunchedEffect(Unit) {
        prizeListViewModel.refreshPrizeItems()
    }

    val prizeList by prizeListViewModel.items.collectAsState()
    val isLoadingItems by prizeListViewModel.isLoadingItems.collectAsState()
    if (prizeList == null) {
        Box(Modifier.fillMaxSize()) {
            CircularProgressIndicator()
        }
        return
    }
    val sortBy by prizeListViewModel.itemsSortBy.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("ガチャの中身") },
            )
        },
        floatingActionButton = {
            PrizeListFab(onClick = { gotoEditPrize() })
        },
    ) { paddingValues ->
        Column(Modifier.padding(paddingValues)) {
            OptionMenus(
                sortBy = sortBy,
                onSort = { prizeListViewModel.updateSortBy(it) },
            )
            LazyColumn(Modifier.fillMaxSize()) {
                val list = prizeList
                if (list != null) {
                    items(list, key = { it.id }) { item ->
                        PrizeListItem(
                            item,
                            modifier = Modifier.animateItemPlacement(),
                        )
                    }
                }
            }
        }
    }

}

@Composable
fun OptionMenus(
    sortBy: String,
    onSort: (by: String) -> Unit
) {
    val sortByMap = mapOf(
        "id" to "デフォルトの並び順",
        "name" to "名前で並べ替え",
        "stock" to "ストックで並べ替え",
        "purchase" to "仕入れ数で並べ替え",
        "rarity" to "レアリティで並べ替え",
    )
    LazyRow(
        contentPadding = PaddingValues(horizontal = 32.dp),
        horizontalArrangement = Arrangement.spacedBy(4.dp),
    ) {
        items(sortByMap.toList()) { (sortCol, displayLabel) ->

            AssistChip(
                colors = if (sortBy == sortCol)
                    AssistChipDefaults.assistChipColors(
                        containerColor = MaterialTheme.colorScheme.tertiary,
                        labelColor = MaterialTheme.colorScheme.onTertiary,
                    )
                else
                    AssistChipDefaults.assistChipColors(),
                onClick = { onSort(sortCol) },
                label = { Text(displayLabel) },
            )
        }
    }
}