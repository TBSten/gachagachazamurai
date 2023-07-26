package me.tbsten.gachagachazamurai.feature.gacha.gacha.openAction

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import me.tbsten.gachagachazamurai.feature.gacha.R
import me.tbsten.gachagachazamurai.ui.modifier.aspectRatio
import me.tbsten.gachagachazamurai.ui.modifier.clickableNoRipple

@Composable
internal fun CapsuleOpenAction(
    onComplete: () -> Unit,
) {
    var openCapsule by remember { mutableStateOf(false) }

    val capsulePainter = painterResource(R.drawable.capsule)
    val capsuleTopPainter = painterResource(R.drawable.capsule_top)
    val capsuleBottomPainter = painterResource(R.drawable.capsule_bottom)

    BoxWithConstraints(
        modifier = Modifier
            .clickableNoRipple { openCapsule = true }
            .fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        val capsuleWidth = maxWidth * 2 / 3
        val capsuleAnimateState = rememberCapsuleAnimationState(openCapsule, maxHeight / 3)

        // explosion
        if (openCapsule) {
            Explosion(
                modifier = Modifier.fillMaxSize(),
                onExploded = onComplete,
            )
        }

        // capsule
        val visibleState = remember {
            MutableTransitionState(false).apply {
                targetState = true
            }
        }
        AnimatedVisibility(
            visibleState = visibleState,
            modifier = Modifier.wrapContentSize(),
            enter = slideInVertically() + fadeIn(),
            exit = ExitTransition.None,
        ) {
            Box(Modifier.wrapContentSize()) {
                Image(
                    painter = capsulePainter,
                    contentDescription = "カプセル",
                    modifier = Modifier
                        .alpha(0f)
                        .width(capsuleWidth)
                        .aspectRatio(capsulePainter),
                )

                Image(
                    painter = capsuleTopPainter,
                    contentDescription = "カプセル ふた",
                    modifier = Modifier
                        .animateCapsuleTop(capsuleAnimateState)
                        .width(capsuleWidth)
                        .aspectRatio(capsuleTopPainter)
                        .align(Alignment.TopCenter),
                )
                Image(
                    painter = capsuleBottomPainter,
                    contentDescription = "カプセル 底",
                    modifier = Modifier
                        .animateCapsuleBottom(capsuleAnimateState)
                        .width(capsuleWidth)
                        .aspectRatio(capsuleBottomPainter)
                        .align(Alignment.BottomCenter),
                )
            }
        }

    }
}

@Composable
private fun rememberCapsuleAnimationState(
    openCapsule: Boolean,
    translateSize: Dp
): CapsuleAnimationState {
    val openTransition = updateTransition(openCapsule)

    val capsuleAnimationDuration = 700
    val capsuleTopOffsetY by openTransition.animateDp(
        transitionSpec = { tween(durationMillis = capsuleAnimationDuration) },
    ) { if (it) -translateSize else 0.dp }

    val capsuleBottomOffsetY = -capsuleTopOffsetY / 3

    val capsulePartsAlpha by openTransition.animateFloat(
        transitionSpec = { tween(durationMillis = capsuleAnimationDuration) },
    ) { if (it) 0f else 1f }

    return CapsuleAnimationState(
        capsuleTopOffsetY,
        capsuleBottomOffsetY,
        capsulePartsAlpha,
    )
}

private fun Modifier.animateCapsuleTop(state: CapsuleAnimationState) = composed {
    graphicsLayer(
        alpha = state.capsulePartsAlpha,
        translationY = with(LocalDensity.current) { state.capsuleTopOffsetY.toPx() },
    )

}

private fun Modifier.animateCapsuleBottom(state: CapsuleAnimationState) = composed {
    graphicsLayer(
        alpha = state.capsulePartsAlpha,
        translationY = with(LocalDensity.current) { state.capsuleBottomOffsetY.toPx() },
    )
}

private data class CapsuleAnimationState(
    val capsuleTopOffsetY: Dp,
    val capsuleBottomOffsetY: Dp,
    val capsulePartsAlpha: Float,
)

private val explosions = listOf(
    R.drawable.explosion1,
    R.drawable.explosion2,
    R.drawable.explosion3,
    R.drawable.explosion4,
    R.drawable.explosion5,
    R.drawable.explosion6,
)

@Composable
private fun Explosion(
    modifier: Modifier = Modifier,
    onExploded: () -> Unit,
) {
    var currentIndex by remember { mutableStateOf(0) }
    LaunchedEffect(Unit) {
        while (currentIndex + 1 < explosions.size) {
            delay(1000 / 6)
            currentIndex++
        }
        onExploded()
    }

    val explosionPainter = painterResource(explosions[currentIndex])

    Image(
        modifier = modifier,
        painter = explosionPainter,
        contentDescription = "explosion",
        contentScale = ContentScale.Crop,
    )
}
