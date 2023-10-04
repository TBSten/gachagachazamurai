package me.tbsten.gachagachazamurai.feature.settings.prizeItem

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.SizeTransform
import androidx.compose.animation.animateColor
import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.updateTransition
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
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import me.tbsten.gachagachazamurai.domain.PrizeItem
import me.tbsten.gachagachazamurai.ui.component.BottomSheet
import me.tbsten.gachagachazamurai.ui.component.BottomSheetState
import me.tbsten.gachagachazamurai.ui.component.Confirm
import me.tbsten.gachagachazamurai.ui.component.ImageSelect
import me.tbsten.gachagachazamurai.ui.component.ListItem
import me.tbsten.gachagachazamurai.ui.component.ScreenTitle
import me.tbsten.gachagachazamurai.ui.component.rememberBottomSheetState
import me.tbsten.gachagachazamurai.ui.component.rememberConfirmState

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
                    onDelete = { editViewModel.delete(prizeItem) }
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
    onDelete: () -> Unit,
) {
    val scope = rememberCoroutineScope()
    val bottomSheetState = rememberBottomSheetState()

    val handleDelete: () -> Unit = {
        bottomSheetState.hide().invokeOnCompletion {
            onDelete()
        }
    }

    Column {
        ListItem(
            onClick = { bottomSheetState.show() },
            onLongClick = { },
        ) {
            Text(prizeItem.name)
        }
    }

    PrizeItemBottomSheet(
        prizeItem = prizeItem,
        sheetState = bottomSheetState,
        onChange = onChange,
        onDelete = handleDelete,
    )

}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun PrizeItemBottomSheet(
    prizeItem: PrizeItem,
    sheetState: BottomSheetState,
    onChange: (PrizeItem) -> Unit,
    onDelete: () -> Unit,
) {

    BottomSheet(
        bottomSheetState = sheetState,
    ) {
        Column(
            Modifier.fillMaxSize().verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            val itemPaddingValues = PaddingValues(horizontal = 8.dp)
            // 景品名と説明
            SummaryEdit(
                modifier = Modifier.padding(itemPaddingValues),
                name = prizeItem.name,
                detail = prizeItem.detail,
                onChange = { name, detail ->
                    onChange(
                        prizeItem.copy(
                            name = name,
                            detail = detail
                        )
                    )
                },
            )
            // 画像
            if (prizeItem.image == null) {
                Text("この景品には画像がありません")
            }
            ImageSelect(
                file = prizeItem.image,
                onFileChange = { onChange(prizeItem.copy(image = it)) },
            )
            // ストック
            StockEdit(
                modifier = Modifier,
                label = { Text("ストック") },
                stock = prizeItem.stock,
                purchase = prizeItem.purchase,
                onStockChange = { onChange(prizeItem.copy(stock = it)) },
                onPurchaseChange = { onChange(prizeItem.copy(purchase = it)) },
            )
            // レア度
            RarityEdit(
                modifier = Modifier,
                label = { Text("レア度") },
                rarity = prizeItem.rarity,
                onRarityChange = { onChange(prizeItem.copy(rarity = it)) },
            )
            // アクション
            FlowRow(
                modifier = Modifier.fillMaxWidth().padding(itemPaddingValues),
                horizontalArrangement = Arrangement.spacedBy(4.dp, Alignment.End),
            ) {
                DeleteAction(
                    prizeItem = prizeItem,
                    onDelete = onDelete,
                )
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

@Composable
private fun SummaryEdit(
    name: String,
    detail: String,
    onChange: (String, String) -> Unit,
    modifier: Modifier = Modifier,
) {
    var editMode by remember { mutableStateOf(false) }
    val titleTextStyle = TextStyle(
        fontWeight = FontWeight.Bold,
        fontSize = 24.sp,
    )

    Column(
        Modifier
            .clickable { editMode = true }
            .then(modifier)
            .fillMaxWidth()
    ) {
        if (editMode) {

            var editingName by remember(name) {
                mutableStateOf(
                    TextFieldValue(
                        text = name,
                        selection = TextRange(name.length)
                    )
                )
            }
            var editingDetail by remember(detail) { mutableStateOf(detail) }
            val focusRequester = remember { FocusRequester() }
            DisposableEffect(Unit) {
                focusRequester.requestFocus()
                onDispose {
                    onChange(editingName.text, editingDetail)
                }
            }

            BasicTextField(
                value = editingName,
                onValueChange = { editingName = it },
                textStyle = titleTextStyle,
                modifier = Modifier.fillMaxWidth().focusRequester(focusRequester),
            )
            BasicTextField(
                value = editingDetail,
                onValueChange = { editingDetail = it },
                modifier = Modifier.fillMaxWidth(),
            )
            Button(onClick = { editMode = false }) {
                Text("OK")
            }

        } else {

            Text(
                text = name,
                style = titleTextStyle,
            )
            Text(
                detail,
            )

        }
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
            verticalArrangement = Arrangement.Center,
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
private fun RarityEdit(
    modifier: Modifier,
    label: @Composable () -> Unit,
    rarity: PrizeItem.Rarity,
    onRarityChange: (PrizeItem.Rarity) -> Unit,
) {
    var showDialog by remember { mutableStateOf(false) }
    Row(
        modifier = modifier
            .clickable { showDialog = true }
            .fillMaxWidth()
            .padding(12.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        Box {
            label()
        }
        Text(
            text = rarity.displayName,
        )
    }

    if (showDialog) {
        Dialog(
            onDismissRequest = { showDialog = false },
        ) {
            Surface(shape = MaterialTheme.shapes.large) {
                Column {

                    Text(
                        text = "レア度",
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(16.dp),
                    )

                    Row(
                        Modifier.height(IntrinsicSize.Max).padding(16.dp),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        PrizeItem.Rarity.values().forEach {
                            val transition = updateTransition(
                                label = "rarity select transition",
                                targetState = rarity == it,
                            )
                            val color by transition.animateColor(label = "border color animation") { selected ->
                                if (selected) MaterialTheme.colorScheme.secondary else MaterialTheme.colorScheme.onSurface
                            }
                            val borderWidth by transition.animateDp(label = "border width animation") { selected ->
                                if (selected) 2.dp else 1.dp
                            }
                            Box(
                                Modifier
                                    .clickable { onRarityChange(it) }
                                    .border(borderWidth, color)
                                    .padding(vertical = 48.dp, horizontal = 12.dp)
                                    .weight(1f)
                                    .fillMaxHeight(),
                                contentAlignment = Alignment.Center,
                            ) {
                                Text(
                                    text = it.displayName,
                                    color = color,
                                    textAlign = TextAlign.Center,
                                    fontWeight = if (transition.currentState) FontWeight.Bold else FontWeight.Normal,
                                )
                            }
                        }
                    }

                }
            }
        }
    }

}

// actions

@Composable
private fun DeleteAction(prizeItem: PrizeItem, onDelete: () -> Unit) {
    val confirmState = rememberConfirmState(
        onOk = onDelete,
    )
    Button(
        onClick = confirmState::confirm,
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.error,
            contentColor = MaterialTheme.colorScheme.onError,
        ),
    ) {
        Text("削除")
    }

    Confirm(confirmState) {
        Text("${prizeItem.name}を削除してもいいですか？")
    }
}
