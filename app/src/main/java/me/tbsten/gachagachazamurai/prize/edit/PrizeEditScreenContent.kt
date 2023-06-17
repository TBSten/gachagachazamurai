package me.tbsten.gachagachazamurai.prize.edit

import android.util.Log
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import me.tbsten.gachagachazamurai.component.DndLazyColumn
import me.tbsten.gachagachazamurai.component.applyIf
import me.tbsten.gachagachazamurai.prize.PrizeListViewModel
import me.tbsten.gachagachazamurai.prize.list.PrizeListItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PrizeEditScreenContent(
    prizeListViewModel: PrizeListViewModel = hiltViewModel(),
    gotoNewPrize: () -> Unit,
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
                title = { Text("中身の編集") },
            )
        },
        floatingActionButton = {
            PrizeEditFab(onClick = { gotoNewPrize() })
        },
    ) { paddingValues ->
        Box(Modifier.padding(paddingValues)) {

//            val lazyColumnState = rememberLazyListState()
//            var targetIndex by remember { mutableStateOf<Int?>(null) }
//            var targetOffsetY by remember { mutableStateOf<Float?>(null) }
//            LazyColumn(
//                modifier = Modifier
//                    .pointerInput(Unit) {
//                        detectDragGesturesAfterLongPress(
//                            onDragStart = { offset ->
//                                val layout = lazyColumnState.layoutInfo.visibleItemsInfo
//                                val startOffsetY = offset.y.toInt()
//                                targetIndex =
//                                    layout.indexOfFirst { startOffsetY < it.offset + it.size }
//                                targetOffsetY = 0f
//                                Log.d("start-drag", "$targetIndex")
//                            },
//                            onDrag = { change, dragAmount ->
//                                val beforeTargetOffsetY =
//                                    targetOffsetY ?: return@detectDragGesturesAfterLongPress
//                                targetOffsetY = beforeTargetOffsetY + dragAmount.y
//                                Log.d("drag", "$beforeTargetOffsetY -> $targetOffsetY")
//                            },
//                            onDragEnd = {
//                                targetIndex = null
//                                targetOffsetY = null
//                            },
//                            onDragCancel = {
//                                targetIndex = null
//                                targetOffsetY = null
//                            },
//                        )
//                    }
//                    .fillMaxSize(),
//                state = lazyColumnState,
//            ) {
//                itemsIndexed(prizeList) { index, item ->
//
//                    var openDialog by remember { mutableStateOf(false) }
//
//                    PrizeListItem(
//                        item,
//                        onDoubleClick = { openDialog = true },
//                        showDetail = true,
//                        modifier = if (targetIndex == index) Modifier.offset {
//                            IntOffset(
//                                x = 0,
//                                y = targetOffsetY!!.toInt()
//                            )
//                        } else Modifier,
//                    )
//
//                    if (openDialog) {
//                        PrizeEditDialog(
//                            item,
//                            onClose = { openDialog = false },
//                            onUpdate = { newPrize -> prizeListViewModel.updatePrizeItem(newPrize) },
//                            onDelete = { prizeListViewModel.deletePrizeItem(item) },
//                        )
//                    }
//
//                }
//            }
            DndLazyColumn(onMove = { from, to -> Log.d("onMove", "$from $to") }) {
                items(prizeList) { info ->
                    var openDialog by remember { mutableStateOf(false) }

                    Box(
                        Modifier
                            .applyIf(info.isDragging) { border(2.dp, Color.Red) }
                            .applyIf(info.isDropTarget) { border(2.dp, Color.Blue) }
                    ) {
                        PrizeListItem(
                            info.item,
                            onDoubleClick = { openDialog = true },
                            showDetail = true,
                        )
                    }
                    if (openDialog) {
                        PrizeEditDialog(
                            info.item,
                            onClose = { openDialog = false },
                            onUpdate = { newPrize -> prizeListViewModel.updatePrizeItem(newPrize) },
                            onDelete = { prizeListViewModel.deletePrizeItem(info.item) },
                        )
                    }
                }
            }

        }
    }
}
