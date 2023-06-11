package me.tbsten.gachagachazamurai.gacha

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.keyframes
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
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
            .scale(animateState.scale)
            .rotate(animateState.rotate)
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
    val scale by animateFloatAsState(
        label = "scale gacha handle animation",
        targetValue = when (step) {
            GachaStep.TURNED_HALF -> 1.001f
            GachaStep.TURNED_FULL -> 1.002f
            else -> 1f
        },
        animationSpec = keyframes {
            val base = 0.3f
            durationMillis = if (
                step == GachaStep.TURNED_HALF ||
                step == GachaStep.TURNED_FULL
            ) 900 else 0
            1.00f at durationMillis * 0 / 1
            1 - base at durationMillis * 1 / 4
            1 + base / 2 at durationMillis * 3 / 4
            1.00f at durationMillis * 1 / 1
        },
    )

    return object : GachaHandleAnimateState {
        override val scale = scale
        override val rotate = rotate
    }
}

private interface GachaHandleAnimateState {
    val scale: Float
    val rotate: Float
}
