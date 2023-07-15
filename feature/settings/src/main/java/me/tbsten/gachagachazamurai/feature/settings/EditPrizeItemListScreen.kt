package me.tbsten.gachagachazamurai.feature.settings

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.SizeTransform
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.with
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
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
        item {
            Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.End) {
                IconButton(onClick = { editViewModel.refresh() }) {
                    Icon(Icons.Default.Refresh, contentDescription = "リフレッシュ")
                }
            }
        }
        if (prizeItems != null) {
            items(prizeItems, key = { it.id }) { prizeItem ->
                PrizeItemListItem(
                    prizeItem = prizeItem,
                    onChange = { editViewModel.update(it) },
                )
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

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
private fun PrizeItemListItem(
    prizeItem: PrizeItem,
    onChange: (PrizeItem) -> Unit,
) {
    var showDetail by remember { mutableStateOf(false) }

    ListItem(
        onClick = { showDetail = true },
        onLongClick = { },
    ) {
        Text(prizeItem.name)
    }

    if (showDetail) {
        ModalBottomSheet(
            onDismissRequest = { showDetail = false },
        ) {
            Column(
                Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(16.dp),
            ) {
                val itemPaddingValues = PaddingValues(horizontal = 8.dp)
                // 景品名と説明
                Column(Modifier.padding(itemPaddingValues)) {
                    Text(
                        text = prizeItem.name,
                        fontWeight = FontWeight.Bold,
                        fontSize = 24.sp,
                    )
                    Text(
                        prizeItem.detail,
                    )
                }
                // ストック
                StockEdit(
                    modifier = Modifier,
                    label = { Text("ストック") },
                    stock = prizeItem.stock,
                    purchase = prizeItem.purchase,
                    onStockChange = { onChange(prizeItem.copy(stock = it)) },
                    onPurchaseChange = { onChange(prizeItem.copy(purchase = it)) },
                )
                // アクション
                FlowRow(
                    modifier = Modifier.fillMaxWidth().padding(itemPaddingValues),
                    horizontalArrangement = Arrangement.spacedBy(4.dp, Alignment.End),
                ) {
                    Button(
                        onClick = {},
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.error,
                            contentColor = MaterialTheme.colorScheme.onError,
                        ),
                    ) {
                        Text("削除")
                    }
                }
            }
        }
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

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun StockEdit(
    modifier: Modifier = Modifier,
    stock: Int,
    purchase: Int,
    onStockChange: (Int) -> Unit,
    onPurchaseChange: (Int) -> Unit,
    label: @Composable () -> Unit = {},
) {
    var showEdit by remember { mutableStateOf(false) }

    Row(
        modifier = modifier
            .clickable { showEdit = true }
            .fillMaxWidth()
            .padding(12.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        Box {
            label()
        }
        FlowRow(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp, Alignment.End),
        ) {
            Text(
                text = "$stock",
            )
            Text("/")
            Text(
                text = "$purchase",
            )
        }
    }

    if (showEdit) {
        Dialog(onDismissRequest = { showEdit = false }) {
            Surface(shape = MaterialTheme.shapes.medium) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(32.dp),
                ) {
                    CompositionLocalProvider(
                        LocalTextStyle provides LocalTextStyle.current.let { it.copy(fontSize = it.fontSize * 1.2) },
                    ) {
                        label()
                    }
                    Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                        DrumRoll(
                            modifier = Modifier.border(1.dp, LocalContentColor.current),
                            value = stock,
                            onValueChange = onStockChange,
                        )
                        DrumRoll(
                            modifier = Modifier.border(1.dp, LocalContentColor.current),
                            value = purchase,
                            onValueChange = onPurchaseChange,
                        )
                    }
                    TextButton(
                        onClick = { showEdit = false },
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    ) {
                        Spacer(Modifier.size(24.dp))
                        Text("閉じる")
                        Spacer(Modifier.size(24.dp))
                    }
                }
            }
        }
    }

}

@OptIn(ExperimentalAnimationApi::class)
@Composable
private fun DrumRoll(
    modifier: Modifier = Modifier,
    value: Int,
    onValueChange: (Int) -> Unit,
    min: Int = 0,
    max: Int = Int.MAX_VALUE,
    step: Int = 1,
) {
    Column(
        modifier
            .width(IntrinsicSize.Max),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {

        val enablePlus = value + step <= max
        Text(
            text = if (enablePlus) "+" else "",
            textAlign = TextAlign.Center,
            fontSize = 24.sp,
            modifier = Modifier
                .fillMaxWidth()
                .clickable(enabled = enablePlus) { onValueChange(value + step) }
        )

        AnimatedContent(
            label = "drum roll animate",
            targetState = value,
            transitionSpec = {
                if (targetState > initialState) {
                    slideInVertically { height -> height } + fadeIn() with
                            slideOutVertically { height -> -height } + fadeOut()
                } else {
                    slideInVertically { height -> -height } + fadeIn() with
                            slideOutVertically { height -> height } + fadeOut()
                }.using(
                    SizeTransform(clip = false)
                )
            },
            modifier = Modifier.clipToBounds().width((48 * 2).dp)
                .padding(horizontal = 16.dp),
        ) { targetValue ->
            Text(
                text = "$targetValue",
                fontSize = 48.sp,
                textAlign = TextAlign.Center,
            )
        }

        val enableMinus = min <= value - step
        Text(
            text = if (enableMinus) "-" else "",
            textAlign = TextAlign.Center,
            fontSize = 24.sp,
            modifier = Modifier
                .fillMaxWidth()
                .clickable(enabled = enableMinus) { onValueChange(value - step) }
        )

    }

}

@Composable
private fun TextUnit.toDp(): Dp =
    (this.value * LocalDensity.current.fontScale / LocalDensity.current.density).dp
