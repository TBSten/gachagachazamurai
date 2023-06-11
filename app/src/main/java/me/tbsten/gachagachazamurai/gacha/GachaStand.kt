package me.tbsten.gachagachazamurai.gacha

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.keyframes
import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.res.painterResource
import me.tbsten.gachagachazamurai.R

@Composable
fun GachaStand(
    step: GachaStep,
    modifier: Modifier = Modifier,
    onChangeStep: (step: GachaStep) -> Unit,
    onCompleteGachaFullRotate: () -> Unit,
) {
    val gachaPainter = painterResource(R.drawable.gacha)
    val animateState = animateGachaStand(
        step = step,
        onCompleteGachaFullRotate = onCompleteGachaFullRotate,
    )

    Image(
        modifier = Modifier
            .scale(animateState.scale)
            .then(modifier),
        painter = gachaPainter,
        contentDescription = "gacha stand",
    )
}

@Composable
private fun animateGachaStand(
    step: GachaStep,
    onCompleteGachaFullRotate: () -> Unit
): GachaStandAnimateState {
    val scale by animateFloatAsState(
        label = "scale gacha effect animation",
        targetValue = when (step) {
            GachaStep.TURNED_HALF -> 1.001f
            GachaStep.TURNED_FULL -> 1.002f
            else -> 1f
        },
        animationSpec = keyframes {
            val base = 0.1f
            durationMillis = if (
                step == GachaStep.TURNED_HALF ||
                step == GachaStep.TURNED_FULL
            ) 900 else 0
            1.00f at durationMillis * 0 / 1
            1 + base at durationMillis * 1 / 4
            1 - base / 2 at durationMillis * 3 / 4
            1.00f at durationMillis * 1 / 1
        },
        finishedListener = {
            if (step == GachaStep.TURNED_FULL) {
                onCompleteGachaFullRotate()
            }
        }
    )
    return object : GachaStandAnimateState {
        override val scale = scale
    }
}

private interface GachaStandAnimateState {
    val scale: Float
}
