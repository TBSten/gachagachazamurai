package me.tbsten.gachagachazamurai.prize.edit

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import me.tbsten.gachagachazamurai.domain.PrizeItem

@Composable
fun PrizeEditDialog(
    default: PrizeItem,
    onClose: () -> Unit,
    onUpdate: (PrizeItem) -> Unit,
    onDelete: () -> Unit,
) {
    Dialog(onDismissRequest = { onClose() }) {
        Surface(shape = MaterialTheme.shapes.large) {
            Box(
                Modifier.padding(24.dp)
                    .verticalScroll(state = rememberScrollState())
            ) {

                PrizeEdit(
                    default = default,
                    title = {
                        Text(
                            "${
                                default.name.substring(
                                    0, 10.coerceAtMost(default.name.length)
                                )
                            }の編集"
                        )
                    },
                    actions = { editing ->
                        Button(
                            onClick = {
                                onDelete()
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
                            onUpdate(editing)
                            onClose()
                        }) { Text("保存") }
                    },
                )

            }
        }
    }

}
