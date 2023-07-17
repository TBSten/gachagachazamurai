package me.tbsten.gachagachazamurai.ui.component

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomSheet(
    bottomSheetState: BottomSheetState,
    content: @Composable () -> Unit,
) {
    val scope = rememberCoroutineScope()
    if (bottomSheetState.open) {
        ModalBottomSheet(
            onDismissRequest = {
                bottomSheetState.hide()
            },
            sheetState = bottomSheetState.sheetState,
        ) {
            content()
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun rememberBottomSheetState(): BottomSheetState {
    val sheetState = rememberModalBottomSheetState()
    var open by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()

    return object : BottomSheetState {
        override val open = open
        override val sheetState = sheetState

        override fun show() {
            open = true
        }

        override fun hide(): Job {
            return scope.launch {
                sheetState.hide()
            }.apply {
                invokeOnCompletion {
                    open = false
                }
            }
        }
    }
}

interface BottomSheetState {
    val open: Boolean

    @OptIn(ExperimentalMaterial3Api::class)
    val sheetState: SheetState
    fun show()
    fun hide(): Job
}
