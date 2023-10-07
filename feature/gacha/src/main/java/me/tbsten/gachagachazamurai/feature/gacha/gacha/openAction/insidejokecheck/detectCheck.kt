package me.tbsten.gachagachazamurai.feature.gacha.gacha.openAction.insidejokecheck

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import kotlin.math.sqrt

internal fun Modifier.detectCheck(
    enabled: Boolean,
    targetRect: Rect,
    checkStep: CheckStep,
    onChangeCheckStep: (CheckStep) -> Unit,
    onComplete: () -> Unit,
) = this.composed {
    val currentCheckStep by rememberUpdatedState(checkStep)
    val startPoint = targetRect.centerLeft
    val turningPoint = targetRect.bottomCenter
    val endPoint = targetRect.topRight

    val density = LocalDensity.current
    val base = with(density) { 100.dp.toPx() }

    fun handleChange(newState: CheckStep) {
        if (newState != currentCheckStep) {
            onChangeCheckStep(newState)
        }
    }

    val touchPoints = rememberTouchPointsState()

    pointerInput(Unit) {
        if (!enabled) return@pointerInput
        detectDragGestures(
            onDragStart = {
                touchPoints.onShowPointsPath()
                touchPoints.addPoint(it)
                handleChange(
                    if (it.inCircle(startPoint, base))
                        CheckStep.Start
                    else
                        CheckStep.Failure
                )
            },
            onDrag = { e, amount ->
                touchPoints.addPoint(e.position)
                if (currentCheckStep === CheckStep.Failure) return@detectDragGestures
                val leftTopToRightBottom = amount.y >= 0
                val (prevState, prevPosition) = if (leftTopToRightBottom)
                    CheckStep.Start to startPoint
                else
                    CheckStep.Turned to turningPoint
                val (nextState, nextPosition) = if (leftTopToRightBottom)
                    CheckStep.Turned to turningPoint
                else
                    CheckStep.End to endPoint

                if (e.position.inCircle(nextPosition, base)) {
                    handleChange(nextState)
                    return@detectDragGestures
                }

                val inCheckMark = lineIntersectCircle(prevPosition, nextPosition, e.position, base)

                handleChange(
                    if (inCheckMark)
                        CheckStep.InTransition(prevState, nextState)
                    else
                        CheckStep.Failure
                )
            },
            onDragEnd = {
                touchPoints.onClosePointsPath()
                val lastPos = touchPoints.points.lastOrNull()
                if (lastPos?.inCircle(endPoint, base) == false) {
                    handleChange(CheckStep.Failure)
                } else {
                    currentCheckStep.let {
                        var nextState = it
                        if (it is CheckStep.InTransition) {
                            nextState = it.to
                        }
                        handleChange(nextState)
                        if (nextState.isComplete) {
                            onComplete()
                        }
                    }
                }
            },
            onDragCancel = {
                touchPoints.onClosePointsPath()
            },
        )
    }
        .drawBehind {
            drawPath(
                path = touchPoints.pointsPath,
                color = Color.Red.copy(alpha = touchPoints.alpha),
                style = Stroke(
                    width = with(density) { 50.dp.toPx() },
                    cap = StrokeCap.Round,
                    miter = 1f,
                    join = StrokeJoin.Round,
                ),
            )
        }
}

private fun lineLength(a: Offset, b: Offset) =
    sqrt((a.x - b.x) * (a.x - b.x) + (a.y - b.y) * (a.y - b.y))

private fun lineIntersectCircle(a: Offset, b: Offset, c: Offset, radius: Float): Boolean {
    val ab = lineLength(a, b)
    val bc = lineLength(b, c)
    val ac = lineLength(a, c)
    val s = (ab + bc + ac) / 2
    val area = sqrt(s * (s - ab) * (s - bc) * (s - ac))
    val h = area / ab * 2
    return h <= radius
}

@Composable
private fun rememberTouchPointsState(): TouchPointsState {
    val points = remember { mutableStateListOf<Offset>() }
    var showPointsPath by remember { mutableStateOf(false) }
    val alpha by animateFloatAsState(
        targetValue = if (showPointsPath) 1f else 0f,
        animationSpec = if (showPointsPath) tween(0) else tween(200),
        label = "points path alpha",
        finishedListener = {
            if (it == 0f) {
                points.clear()
            }
        },
    )
    return TouchPointsState(
        points = points,
        showPointsPath = showPointsPath,
        alpha = alpha,
        onShowPointsPath = { showPointsPath = true },
        onClosePointsPath = { showPointsPath = false },
        addPoint = { points.add(it) },
    )
}

private data class TouchPointsState(
    val points: SnapshotStateList<Offset>,
    val showPointsPath: Boolean,
    val alpha: Float,
    val onShowPointsPath: () -> Unit,
    val onClosePointsPath: () -> Unit,
    val addPoint: (Offset) -> Unit,
) {
    val pointsPath: Path
        get() = Path().apply {
            points.forEachIndexed() { idx, point ->
                if (idx == 0) {
                    moveTo(point.x, point.y)
                } else {
                    lineTo(point.x, point.y)
                }
            }
        }
}
