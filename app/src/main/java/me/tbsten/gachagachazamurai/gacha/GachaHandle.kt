package me.tbsten.gachagachazamurai.gacha

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.scale
import androidx.compose.ui.res.painterResource
import me.tbsten.gachagachazamurai.R

@Composable
fun GachaHandle(
    step: GachaStep,
    modifier: Modifier = Modifier,
) {
    val handlePainter = painterResource(R.drawable.gacha_handle)
    val animateState = animateGachaHandle(step = step)

    Image(
        modifier = Modifier
            .animateGachaHandle(animateState)
            .aspectRatio(handlePainter.intrinsicSize.width / handlePainter.intrinsicSize.height)
            .then(modifier),
        painter = handlePainter,
        contentDescription = "gacha handle",
    )
}

@Composable
private fun animateGachaHandle(
    step: GachaStep,
): GachaHandleAnimateState {
    val rotate by animateFloatAsState(
        label = "rotate handle animation",
        targetValue = when (step) {
            GachaStep.TURNED_HALF -> 180f
            GachaStep.TURNED_FULL -> 360f
            else -> 0f
        },
        animationSpec = tween(durationMillis = if (step == GachaStep.TURNED_HALF || step == GachaStep.TURNED_FULL) 700 else 0),
    )

    val scale = remember { Animatable(1f) }
    LaunchedEffect(step) {
        when (step) {
            GachaStep.TURNED_HALF, GachaStep.TURNED_FULL -> {
                val durationMillis = 700
                val base = 0.2f
                scale.animateTo(1 - base, tween(durationMillis * 2 / 8))
                scale.animateTo(1 + base / 2, tween(durationMillis * 4 / 8))
                scale.animateTo(1f, tween(durationMillis * 2 / 8))
            }

            else -> {}
        }
    }

    return object : GachaHandleAnimateState {
        override val scale = scale.value
        override val rotate = rotate
    }
}

private interface GachaHandleAnimateState {
    val scale: Float
    val rotate: Float
}

private fun Modifier.animateGachaHandle(state: GachaHandleAnimateState) =
    composed {
        scale(state.scale)
            .rotate(state.rotate)
    }
