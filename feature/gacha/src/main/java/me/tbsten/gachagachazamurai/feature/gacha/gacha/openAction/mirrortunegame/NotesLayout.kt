package me.tbsten.gachagachazamurai.feature.gacha.gacha.openAction.mirrortunegame

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.layout.onGloballyPositioned
import me.tbsten.gachagachazamurai.ui.shape.isIntersect
import me.tbsten.gachagachazamurai.ui.shape.toRectInWindow
import kotlin.math.max

@Composable
internal fun NotesLayout(
    notesMap: Map<Float, Notes>,
    intersectionTarget: Rect?,
    intersectionState: NotesIntersectionState,
    modifier: Modifier = Modifier,
) {
    Layout(
        modifier = modifier,
        content = {
            notesMap.forEach { (timing, notes) ->
                NotesLine(
                    notes,
                    modifier = Modifier
                        .layoutId(timing)
                        .onGloballyPositioned {
                            val rect = it.toRectInWindow()
                            val isIntersect = intersectionTarget?.isIntersect(rect) == true
                            intersectionState.apply {
                                if (isIntersect) put(timing, notes)
                                else remove(timing)
                            }
                        },
                    onClick = {},
                )
            }
        },
    ) { measurable, constraints ->
        val placeable = measurable.map { measurable ->
            measurable.layoutId as Float to measurable.measure(constraints)
        }
        val height = placeable.fold(0f) { sum, (offset, placeable) ->
            max(sum, offset + placeable.height.toFloat())
        }
        layout(constraints.maxWidth, height.toInt()) {
            placeable.forEach { (offset, placeable) ->
                placeable.placeRelative(x = 0, y = (height - offset).toInt())
            }
        }
    }
}

@Composable
internal fun rememberNotesIntersectionState(): NotesIntersectionState {
    return NotesIntersectionState()
}

internal class NotesIntersectionState {
    private val _intersectNotes = mutableStateMapOf<Float, Notes>()
    val intersectNotes: Map<Float, Notes>
        get() = _intersectNotes.toMap()

    fun put(timing: Float, rect: Notes) {
        _intersectNotes[timing] = rect
    }

    fun remove(timing: Float) {
        _intersectNotes.remove(timing)
    }

    fun isIntersect(timing: Float) = _intersectNotes.containsKey(timing)
    fun isIntersect(notes: Notes) = _intersectNotes.containsValue(notes)
}
