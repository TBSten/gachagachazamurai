package me.tbsten.gachagachazamurai.gacha

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.offset
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import me.tbsten.gachagachazamurai.R

@Composable
fun GachaStand(
    step: GachaStep,
    modifier: Modifier = Modifier,
    onChangeStep: (step: GachaStep) -> Unit,
    onCompleteGachaHalfRotate: () -> Unit,
    onCompleteGachaFullRotate: () -> Unit,
) {
    val gachaPainter = painterResource(R.drawable.gacha)
    val animateState = animateGachaStand(
        step = step,
        onCompleteGachaHalfRotate = onCompleteGachaHalfRotate,
        onCompleteGachaFullRotate = onCompleteGachaFullRotate,
    )

    Image(
        modifier = Modifier
            .animateGachaStand(animateState)
            .then(modifier),
        painter = gachaPainter,
        contentDescription = "gacha stand",
    )
}

@Composable
private fun animateGachaStand(
    step: GachaStep,
    onCompleteGachaHalfRotate: () -> Unit,
    onCompleteGachaFullRotate: () -> Unit,
): GachaStandAnimateState {
    // ガチャスタンドが左右に揺れて若干膨らんだり縮んだりするアニメーション
    val offsetX = remember { Animatable(0f) }
    LaunchedEffect(step) {
        when (step) {
            GachaStep.TURNED_HALF, GachaStep.TURNED_FULL -> {
                val offsetDelta = 5f
                val durationMillis = 600
                val count = 9
                repeat((count - 1) / 2) {
                    offsetX.animateTo(offsetDelta, tween(durationMillis / count))
                    offsetX.animateTo(-offsetDelta, tween(durationMillis / count))
                }
                offsetX.animateTo(0f, tween(durationMillis / count))
            }

            else -> {}
        }
        if (step == GachaStep.TURNED_HALF) onCompleteGachaHalfRotate()
        if (step == GachaStep.TURNED_FULL) onCompleteGachaFullRotate()
    }
    val offset = Offset(offsetX.value, 1f)

    return object : GachaStandAnimateState {
        override val offset = offset
    }
}

private interface GachaStandAnimateState {
    val offset: Offset
}

private fun Modifier.animateGachaStand(state: GachaStandAnimateState) =
    composed {
        offset(x = state.offset.x.dp, y = state.offset.y.dp)
    }

