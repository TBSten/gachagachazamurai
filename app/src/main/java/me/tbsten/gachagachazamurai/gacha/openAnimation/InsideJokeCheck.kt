package me.tbsten.gachagachazamurai.gacha.openAnimation

import android.util.Log
import androidx.compose.animation.Animatable
import androidx.compose.animation.core.EaseInBack
import androidx.compose.animation.core.EaseOut
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.zIndex
import kotlinx.coroutines.launch
import me.tbsten.gachagachazamurai.component.clickableNoRipple
import me.tbsten.gachagachazamurai.domain.PrizeItem
import me.tbsten.gachagachazamurai.gacha.GachaStep
import kotlin.math.abs
import kotlin.math.min

@Composable
fun InsideJokeCheck(
    step: GachaStep,
    prizeItem: PrizeItem,
    onChangeStep: (step: GachaStep) -> Unit,
    prizeContent: @Composable () -> Unit,
) {
    val scope = rememberCoroutineScope()
    var launched by remember { mutableStateOf(false) }
    LaunchedEffect(Unit) { launched = true }
    val transition = updateTransition(launched, label = "check transtion")
    val checkRotate by transition.animateFloat(
        label = "check rotate",
        transitionSpec = { tween(delayMillis = 200, durationMillis = 500) }
    ) {
        if (!it) -90f
        else 0f
    }
    val checkAlpha by transition.animateFloat(
        label = "check alpha",
        transitionSpec = { tween(delayMillis = 0, durationMillis = 400) },
    ) {
        if (it) 1f
        else 0f
    }
    val checkScale by transition.animateFloat(
        label = "check scale",
        transitionSpec = { tween(delayMillis = 0, durationMillis = 400) },
    ) {
        if (!it) 0f
        else 1f
    }

    val history = remember { mutableStateListOf<Offset>() }
    val path by remember {
        derivedStateOf {
            Path().apply {
                history.forEachIndexed { index, point ->
                    if (index == 0) moveTo(point.x, point.y)
                    else lineTo(point.x, point.y)
                }
            }
        }
    }


    val checkColor = remember(prizeItem.rarity) {
        Animatable(
            when (prizeItem.rarity) {
                PrizeItem.Rarity.NORMAL -> Color.Red
                else -> Color.Green
            }
        )
    }
    val enableDraw = !checkColor.isRunning

    BoxWithConstraints(
        Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {

        val animationDelay = 500
        Canvas(
            Modifier
                .rotate(checkRotate)
                .alpha(
                    checkAlpha * animateFloatAsState(
                        label = "canvas checkAlpha animation",
                        targetValue = if (step === GachaStep.OPENED_CAPSULE) 0f else 1f,
                        animationSpec = tween(durationMillis = animationDelay, easing = EaseOut),
                    ).value
                )
                .scale(
                    checkScale * animateFloatAsState(
                        label = "canvas checkScale animation",
                        targetValue = if (step === GachaStep.OPENED_CAPSULE) 3f else 1f,
                        animationSpec = tween(
                            durationMillis = animationDelay - 100,
                            easing = EaseInBack
                        ),
                    ).value
                )
                .fillMaxSize()
                .pointerInput(Unit) {
                    detectDragGestures(
                        onDragStart = {
                            if (!enableDraw) return@detectDragGestures
                            scope.launch {
                                checkColor.animateTo(
                                    checkColor.value.copy(alpha = 1f),
                                    tween(durationMillis = 0),
                                )
                                history.clear()
                                history.add(it)
                                history.add(Offset(it.x + 1, it.y + 1))
                                Log.d("debug-draw", "start:$it")
                            }
                        },
                        onDrag = { change, _ ->
                            if (!enableDraw) return@detectDragGestures
                            change.position.let { pos ->
                                history.add(Offset(pos.x, pos.y))
                            }
                        },
                        onDragEnd = {
                            val checked = isValidChecked(
                                history = history,
                                containerSize = Size(size.width.toFloat(), size.height.toFloat()),
                            )
                            if (checked) {
                                // goto next step
                                val nextStep = step.next
                                    ?: throw IllegalStateException("inside joke checked . but $step has not next step")
                                onChangeStep(nextStep)
                            } else {
                                // try again draw check
                                scope.launch {
                                    checkColor.animateTo(
                                        checkColor.value.copy(alpha = 0f),
                                        tween(durationMillis = 500),
                                    )
                                }.invokeOnCompletion { history.clear() }
                            }
                        },
                    )
                }
        ) {

            drawPath(
                path = exampleCheckPath(size),
                color = Color.Black.copy(alpha = 0.2f),
                style = Stroke(120f, cap = StrokeCap.Round, miter = 1f),
            )

            drawPath(
                path = path,
                color = checkColor.value,
                style = Stroke(120f, cap = StrokeCap.Round, miter = 1f),
            )

        }

        if (step === GachaStep.OPENED_CAPSULE) {
            Box(Modifier.zIndex(999f).clickableNoRipple { }.fillMaxSize()) {
                prizeContent()
            }
        }

    }

}

fun exampleCheckPath(size: Size) = Path().apply {
    // size内にチェックマークを書く
    val exampleSize = min(size.width * 0.6f, size.height * 0.6f)
    val exampleBounds = Rect(
        offset = Offset((size.width - exampleSize) / 2, (size.height - exampleSize) / 2),
        size = Size(exampleSize, exampleSize),
    )
    moveTo(0f, exampleBounds.height / 2)
    lineTo(exampleBounds.width / 3, exampleBounds.height)
    lineTo(exampleBounds.width, 0f)

    translate(exampleBounds.topLeft)
}

fun isValidChecked(
    history: List<Offset>,
    containerSize: Size,
): Boolean {
    val exampleSize = min(containerSize.width * 0.6f, containerSize.height * 0.6f)
    val exampleBounds = Rect(
        offset = Offset(
            (containerSize.width - exampleSize) / 2,
            (containerSize.height - exampleSize) / 2
        ),
        size = Size(exampleSize, exampleSize),
    )
    val validCircleRadius = min(containerSize.width, containerSize.height) * 0.15f
    // 条件1 startがexampleStart付近
    val exampleStart = Offset(exampleBounds.left, exampleBounds.top + exampleSize / 2)
    if (!history.first().inCircle(exampleStart, validCircleRadius)) {
        Log.d("isValidChecked", "invalid start")
        return false
    }
    // 条件2 endがexampleLast付近
    val exampleLast = Offset(exampleBounds.right, exampleBounds.top)
    if (!history.last().inCircle(exampleLast, validCircleRadius)) {
        Log.d("isValidChecked", "invalid end")
        return false
    }
    // 条件3 折り返しがexampleStart付近
    val exampleFlap = Offset(exampleBounds.left + exampleBounds.width / 3, exampleBounds.bottom)
    val historyFlap: Offset? = history.let {
        val prev = it.first()
        it.forEach { offset ->
            if (prev.y < offset.y) return@let offset
        }
        return@let null
    }
    if (!(historyFlap == null || !historyFlap.inCircle(exampleFlap, validCircleRadius))) {
        Log.d("isValidChecked", "invalid flap")
        return false
    }
    return true
}

private fun Offset.inCircle(centerOffset: Offset, r: Float): Boolean {
    return abs(this.x - centerOffset.x) <= r && abs(this.y - centerOffset.y) <= r
}
