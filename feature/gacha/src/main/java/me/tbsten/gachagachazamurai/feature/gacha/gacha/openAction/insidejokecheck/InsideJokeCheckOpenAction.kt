package me.tbsten.gachagachazamurai.feature.gacha.gacha.openAction.insidejokecheck

import android.util.Log
import androidx.compose.animation.core.EaseInOutQuad
import androidx.compose.animation.core.EaseOutBounce
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.geometry.toRect
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.graphics.drawscope.scale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import me.tbsten.gachagachazamurai.feature.gacha.R

@Composable
internal fun InsideJokeCheckOpenAction(
    onComplete: () -> Unit,
    onShowNavigationText: (drawable: Int, String) -> Unit,
    onClearNavigationText: () -> Unit,
) {
    var completed by remember { mutableStateOf(false) }
    fun handleComplete() {
        completed = true
        onClearNavigationText()
        onComplete()
    }
    LaunchedEffect(Unit) {
        onClearNavigationText()
        onShowNavigationText(R.drawable.nazore_1, "なぞれ")
    }

    BoxWithConstraints {
        val canvasSize = DpSize(maxWidth, maxHeight)
        val density = LocalDensity.current
        val padding = 60.dp
        val checkRect = with(density) {
            val canvasSizeAsPx = with(density) {
                Size(canvasSize.width.toPx(), canvasSize.height.toPx())
            }
            centerRect(
                container = canvasSizeAsPx.toRect(),
                Size(
                    canvasSizeAsPx.minDimension - padding.toPx() * 2,
                    canvasSizeAsPx.minDimension - padding.toPx() * 2,
                ),
            )
        }

        var checkStep by remember { mutableStateOf<CheckStep>(CheckStep.Start) }
        val animatedProgress by animateFloatAsState(
            label = "gacha background progress",
            targetValue = checkStep.progress,
            animationSpec = if (checkStep == CheckStep.Failure)
                tween(1200, easing = EaseOutBounce)
            else
                tween(400, easing = EaseInOutQuad),
        )

        var inView by remember {
            mutableStateOf(false)
        }
        LaunchedEffect(Unit) {
            inView = true
        }
        val inViewTransition = updateTransition(
            targetState = inView,
            label = "in view transition",
        )
        val checkScale by inViewTransition.animateFloat(
            label = "in view check scale",
            transitionSpec = { tween(delayMillis = 0, durationMillis = 300) },
        ) { if (it) 1f else 0f }
        val checkRotate by inViewTransition.animateFloat(
            label = "in view check rotate",
            transitionSpec = { tween(delayMillis = 250, durationMillis = 700) },
        ) { if (it) 0f else -90f }
        Log.d("check-state", "checkRotate:$checkRotate")

        Spacer(
            modifier = Modifier
                .drawBehind {
                    drawBackground(
                        checkStep = checkStep,
                        progress = animatedProgress,
                    )
                    val strokeWidth = 50.dp
                    rotate(checkRotate) {
                        scale(checkScale) {
                            drawCheck(
                                target = checkRect,
                                width = strokeWidth.toPx(),
                            )
                        }
                    }
                }
                .detectCheck(
                    enabled = !completed,
                    targetRect = checkRect,
                    checkStep = checkStep,
                    onChangeCheckStep = {
                        checkStep = it
                    },
                    onComplete = ::handleComplete,
                )
                .fillMaxSize(),
        )

    }
}
