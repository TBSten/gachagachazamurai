package me.tbsten.gachagachazamurai.gacha.openAnimation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import kotlinx.coroutines.delay
import me.tbsten.gachagachazamurai.R
import me.tbsten.gachagachazamurai.component.clickableNoRipple
import me.tbsten.gachagachazamurai.domain.PrizeItem
import me.tbsten.gachagachazamurai.gacha.GachaStep

val explosions = listOf(
    R.drawable.explosion1,
    R.drawable.explosion2,
    R.drawable.explosion3,
    R.drawable.explosion4,
    R.drawable.explosion5,
    R.drawable.explosion6,
)

@Composable
fun Capsule(
    step: GachaStep,
    prizeItem: PrizeItem,
    onChangeStep: (step: GachaStep) -> Unit,
    prizeContent: @Composable () -> Unit,
) {
    var open by remember { mutableStateOf(false) }
    LaunchedEffect(Unit) { open = true }

    val capsuleModifier = animateCapsuleImage(open)
    val explosionState = animateExplosion()

    BoxWithConstraints(
        Modifier
            .clickableNoRipple(
                enabled = !explosionState.isAnimating,
            ) {
                when (step) {
                    in GachaStep.UNOPENED_CAPSULE..GachaStep.OPENED_CAPSULE.prev!! -> {
                        onChangeStep(step.next!!)
                    }

                    GachaStep.OPENED_CAPSULE -> {}
                    else -> throw Error("not implement")
                }
            }
            .fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        val capsuleWidth = (maxWidth.value * 0.5).dp
        val painter = painterResource(R.drawable.capsule)
        val imageSize = capsuleWidth.let {
            val img = painter.intrinsicSize
            DpSize(
                width = it,
                height = widthToHeightByAspectRatio(it, img.width / img.height)
            )
        }

        val openDuration = 300
        val isOpenCapsule = step == GachaStep.OPENED_CAPSULE
        val topOffsetY by animateDpAsState(
            label = "capsule top offset y translate",
            targetValue = if (isOpenCapsule) (-200).dp else 0.dp,
            animationSpec = tween(durationMillis = openDuration),
        )
        val bottomOffsetY by animateDpAsState(
            label = "capsule bottom offset y translate",
            targetValue = if (isOpenCapsule) (+30).dp else 0.dp,
            animationSpec = tween(durationMillis = openDuration),
        )
        val capsulePartsAlpha by animateFloatAsState(
            label = "capsule parts alpha fade out",
            targetValue = if (isOpenCapsule) 0f else 1f,
            animationSpec = tween(durationMillis = openDuration),
        )

        AnimatedVisibility(
            visible = step == GachaStep.UNOPENED_CAPSULE,
            exit = fadeOut(),
            modifier = Modifier.zIndex(1f).padding(top = 32.dp).align(Alignment.TopCenter),
        ) {
            Image(
                painterResource(R.drawable.tap_2),
                contentDescription = "tap",
            )
        }

        when (step) {
            GachaStep.UNOPENED_CAPSULE -> {

                Image(
                    painter,
                    contentDescription = null,
                    modifier = Modifier
                        .size(imageSize)
                        .then(capsuleModifier),
                )
            }

            GachaStep.OPENED_CAPSULE -> {
                Box(
                    Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center,
                ) {
                    // capsule
                    Column {
                        val topPainter = painterResource(R.drawable.capsule_top)
                        val bottomPainter = painterResource(R.drawable.capsule_bottom)
                        Image(
                            topPainter,
                            contentDescription = null,
                            modifier = Modifier
                                .offset(y = topOffsetY)
                                .widthBasedAspectRatio(
                                    capsuleWidth,
                                    topPainter.intrinsicSize
                                )
                                .alpha(capsulePartsAlpha),
                        )
                        Image(
                            bottomPainter,
                            contentDescription = null,
                            modifier = Modifier
                                .offset(y = bottomOffsetY)
                                .widthBasedAspectRatio(
                                    capsuleWidth,
                                    bottomPainter.intrinsicSize
                                )
                                .alpha(capsulePartsAlpha),
                        )
                    }
                    // effect
                    LaunchedEffect(Unit) { explosionState.animate() }
                    Image(
                        explosionState.painter,
                        contentDescription = null,
                        modifier = Modifier
                            .fillMaxSize(),
                        contentScale = ContentScale.Crop,
                    )

                    if (explosionState.isDone) {
                        prizeContent()
                    }
                }
            }

            else -> throw IllegalStateException("not implement")
        }
    }

}

@Composable
private fun animateCapsuleImage(
    show: Boolean,
): Modifier {
    val duration = 500
    val alpha by animateFloatAsState(
        label = "capsule alpha animation",
        targetValue = if (show) 1f else 0f,
        animationSpec = tween(durationMillis = duration),
    )
    val offsetY by animateDpAsState(
        label = "capsule offsetY animation",
        targetValue = if (show) 0.dp else (-300).dp,
        animationSpec = tween(durationMillis = duration)
    )
    return Modifier
        .offset(y = offsetY)
        .alpha(alpha)
}

private fun widthToHeightByAspectRatio(width: Dp, aspectRatio: Float) =
    width / aspectRatio

private fun widthToHeightByAspectRatio(width: Dp, size: Size) =
    widthToHeightByAspectRatio(width, size.width / size.height)

private fun Modifier.widthBasedAspectRatio(width: Dp, aspectRatio: Float) = composed {
    this.width(width).height(widthToHeightByAspectRatio(width, aspectRatio))
}

private fun Modifier.widthBasedAspectRatio(width: Dp, size: Size) = composed {
    this.width(width).height(widthToHeightByAspectRatio(width, size))
}

@Composable
private fun animateExplosion(
    animateOnLaunch: Boolean = false,
    onDone: () -> Unit = {},
): AnimateExplosionState {
    var img by remember { mutableStateOf(explosions[0]) }
    var isAnimating by remember { mutableStateOf(false) }
    var isDone by remember { mutableStateOf(false) }
    suspend fun animateExplosion() {
        isAnimating = true
        explosions.forEachIndexed { idx, it ->
            img = it
            delay((100 - idx * idx / 3).toLong())
        }
        delay(300)
        isAnimating = false
        isDone = true
        onDone()
    }
    if (animateOnLaunch) {
        LaunchedEffect(Unit) {
            animateExplosion()
        }
    }
    return AnimateExplosionState(
        painter = painterResource(img),
        isAnimating = isAnimating,
        isDone = isDone,
        animate = ::animateExplosion,
    )
}

@Stable
data class AnimateExplosionState(
    val painter: Painter,
    val isAnimating: Boolean,
    val isDone: Boolean,
    val animate: suspend () -> Unit,
)
