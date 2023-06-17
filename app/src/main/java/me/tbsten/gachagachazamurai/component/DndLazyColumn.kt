package me.tbsten.gachagachazamurai.component

import android.util.Log
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
    stopTop: Boolean = true,
    stopBottom: Boolean = true,
    itemsContent: DndLazyColumnScope.() -> Unit,
) {

    val lazyColumnState = rememberLazyListState()
    var draggingStartOffset by remember { mutableStateOf<Offset?>(null) }
    var draggingIndex by remember { mutableStateOf<Int?>(null) }
    var draggingOffsetYDelta by remember { mutableStateOf<Float?>(null) }
    var dropTargetIndex by remember { mutableStateOf<Int?>(null) }

    LazyColumn(
        modifier = Modifier
            .pointerInput(Unit) {
                detectDragGesturesAfterLongPress(
                    onDragStart = { offset ->
                        val layout = lazyColumnState.layoutInfo.visibleItemsInfo
                        val startOffsetY = offset.y.toInt()
                        draggingIndex =
                            layout.indexOfFirst { startOffsetY < it.offset + it.size }
                        draggingOffsetYDelta = 0f
                        draggingStartOffset = offset
                        Log.d("start-drag", "$draggingIndex")
                    },
                    onDrag = { change, dragAmount ->
                        // ドラッグ中の要素の移動
                        val beforeTargetOffsetY =
                            draggingOffsetYDelta
                                ?: throw IllegalStateException("draggingOffsetY is null")
                        val layout = lazyColumnState.layoutInfo.visibleItemsInfo
                        draggingOffsetYDelta = beforeTargetOffsetY + dragAmount.y
                        // ドロップ対象の要素の選択
                        val currentPointerOffsetY = draggingStartOffset!!.y + draggingOffsetYDelta!!
                        val index =
                            layout.indexOfFirst { currentPointerOffsetY < it.offset + it.size }
                        dropTargetIndex = when {
                            currentPointerOffsetY <= 0 -> 0
                            index == -1 -> layout.lastIndex
                            else -> index
                        }
                        Log.d("drag", "$draggingOffsetYDelta")
                    },
                    onDragEnd = {
                        // onMove.invoke()
                        draggingStartOffset = null
                        draggingIndex = null
                        draggingOffsetYDelta = null
                        dropTargetIndex = null
                    },
                    onDragCancel = {
                        draggingStartOffset = null
                        draggingIndex = null
                        draggingOffsetYDelta = null
                        dropTargetIndex = null
                    },
                )
            },
        state = lazyColumnState,
    ) {
        val columnScope = DndLazyColumnScope(
            draggingIndex = draggingIndex,
            draggingOffsetYDelta = draggingOffsetYDelta,
            dropTargetIndex = dropTargetIndex,
            scope = this,
        )
        columnScope.itemsContent()

    }
}

class DndLazyColumnScope(
    val draggingIndex: Int?,
    private val draggingOffsetYDelta: Float?,
    val dropTargetIndex: Int?,
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
                modifier = if (draggingIndex == index) Modifier.offset {
                    IntOffset(
                        x = 0,
                        y = draggingOffsetYDelta!!.toInt()
                    )
                } else Modifier
            ) {
                lazyItemScope.itemContent(
                    DndLazyColumnItemInfo(
                        index = index,
                        item = item,
                        draggingIndex = draggingIndex,
                        dropTargetIndex = dropTargetIndex,
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
