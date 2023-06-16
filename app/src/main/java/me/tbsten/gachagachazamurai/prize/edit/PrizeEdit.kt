package me.tbsten.gachagachazamurai.prize.edit

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.SuggestionChip
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import kotlinx.coroutines.launch
import me.tbsten.gachagachazamurai.component.clickableSuspend
import me.tbsten.gachagachazamurai.domain.PrizeItem

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun PrizeEdit(
    default: PrizeItem,
    modifier: Modifier = Modifier,
    title: (@Composable (editing: PrizeItem) -> Unit)?,
    actions: (@Composable (editing: PrizeItem) -> Unit)?,
) {
    var editing by remember { mutableStateOf(default) }
    Column(
        modifier,
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        if (title != null) {
            Row(Modifier.padding(8.dp)) {
                title(editing)
            }
        }

        // 景品名
        OutlinedTextField(
            value = editing.name,
            onValueChange = { editing = editing.copy(name = it) },
            label = { Text("景品名") },
            modifier = Modifier.fillMaxWidth(),
            textStyle = TextStyle.Default.copy(fontSize = 14.sp),
            singleLine = true,
        )
        OutlinedTextField(
            value = editing.detail,
            onValueChange = { editing = editing.copy(detail = it) },
            label = { Text("詳細") },
            modifier = Modifier.fillMaxWidth(),
            minLines = 2,
        )
        FlowRow(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.End)
        ) {
            StockEdit(
                stock = editing.stock,
                onChange = { editing = editing.copy(stock = it, purchase = it) },
            )
            SelectRarity(
                rarity = editing.rarity,
                onChange = { editing = editing.copy(rarity = it) },
            )
        }
        if (actions != null) {
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceAround) {
                actions(editing)
            }
        }
    }
}

val stockShortcuts = mapOf(
    "1" to 1,
    "3" to 3,
    "5" to 5,
    "10" to 10,
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StockEdit(
    stock: Int,
    onChange: (Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    var openDialog by remember { mutableStateOf(false) }
    SuggestionChip(onClick = { openDialog = true }, modifier = modifier, label = {
        Text("ストック:$stock")
    })
    if (openDialog) {
        Dialog(
            onDismissRequest = { openDialog = false }
        ) {
            Surface {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                ) {
                    Text("ストック数", fontWeight = FontWeight.Bold)
                    var textFieldValue by remember { mutableStateOf("$stock") }
                    TextField(
                        value = textFieldValue,
                        onValueChange = {
                            textFieldValue = it
                            val number = it.toIntOrNull()
                            if (number != null) onChange(number)
                        },
                        modifier = Modifier.fillMaxWidth(),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        singleLine = true,
                    )
                    Row {
                        stockShortcuts.forEach { (text, stock) ->
                            SuggestionChip(
                                label = { Text(text = text) },
                                onClick = {
                                    onChange(stock)
                                    textFieldValue = "$stock"
                                },
                            )
                        }
                    }
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.End
                    ) {
                        TextButton(onClick = { openDialog = false }) {
                            Text("OK")
                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SelectRarity(
    rarity: PrizeItem.Rarity,
    onChange: (PrizeItem.Rarity) -> Unit,
    modifier: Modifier = Modifier,
) {
    val bottomSheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    var openBottomSheet by rememberSaveable { mutableStateOf(false) }
    val scope = rememberCoroutineScope()

    SuggestionChip(
        onClick = { scope.launch { openBottomSheet = true } },
        modifier = modifier,
        label = {
            Text("レア度:${rarity.displayName}")
        })

    if (openBottomSheet) {

        ModalBottomSheet(
            shape = MaterialTheme.shapes.medium,
            onDismissRequest = {
                openBottomSheet = false
            },
            sheetState = bottomSheetState,
        ) {
            Text("レア度", fontWeight = FontWeight.Bold)
            PrizeItem.Rarity.values().forEach {
                val isSelected = rarity == it
                Row(
                    Modifier
                        .clickableSuspend {
                            launch {
                                onChange(it)
                                bottomSheetState.hide()
                            }.invokeOnCompletion {
                                openBottomSheet = false
                            }
                        }
                        .fillMaxWidth()
                        .padding(16.dp),
                ) {
                    Text(
                        text = it.displayName,
                        modifier = Modifier.weight(1f),
                        color = if (isSelected) MaterialTheme.colorScheme.primary else Color.Unspecified,
                    )
                }
            }
            Spacer(Modifier.size(100.dp))
        }

    }
}
