package me.tbsten.gachagachazamurai.prize.list

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import me.tbsten.gachagachazamurai.prize.PrizeListViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PrizeListScreenContent(
    prizeListViewModel: PrizeListViewModel = hiltViewModel(),
    gotoEditPrize: () -> Unit,
) {
    LaunchedEffect(Unit) {
        prizeListViewModel.refreshPrizeItems()
    }

    val prizeList = prizeListViewModel.items.collectAsState().value
    val isLoadingItems = prizeListViewModel.isLoadingItems.collectAsState().value
    if (isLoadingItems || (prizeList == null)) {
        Box(Modifier.fillMaxSize()) {
            CircularProgressIndicator()
        }
        return
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("ガチャの中身") },
            )
        },
        floatingActionButton = {
            PrizeListFab(onClick = { gotoEditPrize() })
        },
    ) {
        LazyColumn(Modifier.padding(it).fillMaxSize()) {
            items(prizeList) { item ->

                PrizeListItem(
                    item,
                )

            }
        }
    }

}
