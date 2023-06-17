package me.tbsten.gachagachazamurai.component

import androidx.compose.foundation.gestures.detectDragGesturesAfterLongPress
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.IntOffset

@Composable
fun DndLazyColumn(
    itemsContent: DndLazyColumnScope.() -> Unit,
) {

    val lazyColumnState = rememberLazyListState()
    var dndState by remember { mutableStateOf<DndState>(DndState.NotDragging) }

    LazyColumn(
        modifier = Modifier
            .pointerInput(Unit) {
                detectDragGesturesAfterLongPress(
                    onDragStart = { offset ->
                        val layout = lazyColumnState.layoutInfo.visibleItemsInfo
                        val startOffsetY = offset.y.toInt()
                        val draggingIndex =
                            layout.indexOfFirst { startOffsetY < it.offset + it.size }
                        dndState = DndState.Dragging(
                            draggingIndex = draggingIndex,
                            draggingOffsetYDelta = 0f,
                            draggingStartOffset = offset,
                            dropTargetIndex = draggingIndex,
                        )
                    },
                    onDrag = { change, dragAmount ->
                        val beforeState = dndState
                        if (beforeState !is DndState.Dragging) throw IllegalStateException("when onDrag, dndState must be Dragging but $dndState")

                        val layout = lazyColumnState.layoutInfo.visibleItemsInfo
                        val currentPointerOffsetY =
                            beforeState.draggingStartOffset.y + beforeState.draggingOffsetYDelta
                        val index =
                            layout.indexOfFirst { currentPointerOffsetY < it.offset + it.size }
                        dndState = beforeState.let {
                            it.copy(
                                draggingOffsetYDelta = it.draggingOffsetYDelta + dragAmount.y,
                                dropTargetIndex = when {
                                    currentPointerOffsetY <= 0 -> 0
                                    index == -1 -> layout.lastIndex
                                    else -> index
                                }
                            )
                        }
                    },
                    onDragEnd = {
                        // onMove.invoke()
                        dndState = DndState.NotDragging
                    },
                    onDragCancel = {
                        dndState = DndState.NotDragging
                    },
                )
            },
        state = lazyColumnState,
    ) {
        val columnScope = DndLazyColumnScope(
            dndState = dndState,
            scope = this,
        )
        columnScope.itemsContent()

    }
}

sealed interface DndState {
    object NotDragging : DndState
    data class Dragging(
        val draggingStartOffset: Offset,
        val draggingIndex: Int,
        val draggingOffsetYDelta: Float,
        val dropTargetIndex: Int,
    ) : DndState
}

class DndLazyColumnScope(
    private val dndState: DndState,
    private val scope: LazyListScope,
) {
    fun <T> items(
        items: List<T>,
        key: ((index: Int, item: T) -> Any)? = null,
        contentType: (index: Int, item: T) -> Any? = { _, _ -> null },
        itemContent: @Composable LazyItemScope.(info: DndLazyColumnItemInfo<T>) -> Unit
    ) {
        scope.itemsIndexed(items, key, contentType) { index, item ->
            val lazyItemScope = this
            Box(
                modifier = if (dndState is DndState.Dragging && dndState.draggingIndex == index) Modifier.offset {
                    IntOffset(
                        x = 0,
                        y = dndState.draggingOffsetYDelta.toInt()
                    )
                } else Modifier
            ) {
                lazyItemScope.itemContent(
                    DndLazyColumnItemInfo(
                        index = index,
                        item = item,
                        draggingIndex = if (dndState is DndState.Dragging) dndState.draggingIndex else null,
                        dropTargetIndex = if (dndState is DndState.Dragging) dndState.dropTargetIndex else null,
                    )
                )
            }
        }
    }

}

data class DndLazyColumnItemInfo<T>(
    val index: Int,
    val item: T,
    val draggingIndex: Int?,
    val dropTargetIndex: Int?,
) {
    val isDragging: Boolean
        get() = index == draggingIndex
    val isDropTarget: Boolean
        get() = index == dropTargetIndex
}
