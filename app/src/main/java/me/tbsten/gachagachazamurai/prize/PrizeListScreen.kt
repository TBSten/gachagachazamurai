package me.tbsten.gachagachazamurai.prize

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun PrizeListScreenContent(
    prizeListViewModel: PrizeListViewModel = hiltViewModel(),
    showNewPrizeDialog: () -> Unit,
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
            PrizeListFab(onClick = { showNewPrizeDialog() })
        },
    ) {
        LazyColumn(Modifier.padding(it).fillMaxSize()) {
            items(prizeList) { item ->
                with(item) {
                    var openDialog by remember { mutableStateOf(false) }
                    Row(
                        modifier = Modifier
                            .clickable {
                                openDialog = true
                            }
                            .padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Text(name, modifier = Modifier.weight(1f))
                        Text(
                            text = rarity.displayName,
                            fontSize = 12.sp,
                            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f),
                        )
                        Spacer(Modifier.size(16.dp))
                        Text(
                            text = "$stock / $purchase",
                        )
                    }

                    if (openDialog) {
                        Dialog(onDismissRequest = { openDialog = false }) {
                            Surface(shape = MaterialTheme.shapes.large) {
                                Box(
                                    Modifier.padding(24.dp)
                                        .verticalScroll(state = rememberScrollState())
                                ) {

                                    PrizeEdit(
                                        default = item,
                                        title = {
                                            Text(
                                                "${
                                                    item.name.substring(
                                                        0, 10.coerceAtMost(item.name.length)
                                                    )
                                                }の編集"
                                            )
                                        },
                                        actions = { editing ->
                                            Button(
                                                onClick = {
                                                    prizeListViewModel.deletePrizeItem(editing)
                                                },
                                                colors = with(MaterialTheme.colorScheme) {
                                                    ButtonDefaults.buttonColors(
                                                        containerColor = error,
                                                        contentColor = onError,
                                                    )
                                                }
                                            ) {
                                                Text("削除")
                                            }
                                            Button(onClick = {
                                                prizeListViewModel.updatePrizeItem(editing)
                                                openDialog = false
                                            }) { Text("保存") }
                                        },
                                    )

                                }
                            }
                        }
                    }
                }
            }
        }
    }

}

@Composable
fun EditPrizeModalBottomSheet() {

}
