package me.tbsten.gachagachazamurai.gacha

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.keyframes
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.scale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import me.tbsten.gachagachazamurai.R

@Composable
fun Gacha(
    step: GachaStep,
    onChangeStep: (step: GachaStep) -> Unit,
    onCompleteGachaFullRotate: () -> Unit,
) {
    val gachaScale by animateFloatAsState(
        label = "gacha scale animation",
        targetValue = if (step.stepIndex >= GachaStep.STARTED.stepIndex) 1.2f else 0.8f,
        animationSpec = tween(delayMillis = 400, durationMillis = 500)
    )
    ConstraintLayout(
        Modifier.scale(gachaScale).fillMaxSize().padding(48.dp),
    ) {
        val (gacha, handle) = createRefs()

        GachaStand(
            step,
            modifier = Modifier.constrainAs(gacha) {
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                width = Dimension.fillToConstraints
                top.linkTo(parent.top)
                bottom.linkTo(parent.bottom)
                height = Dimension.fillToConstraints
            },
            onChangeStep = onChangeStep,
            onCompleteGachaFullRotate = onCompleteGachaFullRotate,
        )

        GachaHandle(
            step,
            modifier = Modifier.constrainAs(handle) {
                start.linkTo(gacha.start)
                end.linkTo(gacha.end)
                width = Dimension.percent(0.30f)
                top.linkTo(gacha.top, 210.dp)
                bottom.linkTo(gacha.bottom, 58.dp)
            }
        )

    }
}

@Composable
fun GachaStand(
    step: GachaStep,
    modifier: Modifier = Modifier,
    onChangeStep: (step: GachaStep) -> Unit,
    onCompleteGachaFullRotate: () -> Unit,
) {
    val gachaPainter = painterResource(R.drawable.gacha)
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

    Image(
        modifier = Modifier
            .scale(scale)
            .then(modifier),
        painter = gachaPainter,
        contentDescription = "gacha stand",
    )
}

@Composable
fun GachaHandle(
    step: GachaStep,
    modifier: Modifier = Modifier,
) {
    val handlePainter = painterResource(R.drawable.gacha_handle)
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

    Image(
        modifier = Modifier
            .scale(scale)
            .rotate(rotate)
            .aspectRatio(handlePainter.intrinsicSize.width / handlePainter.intrinsicSize.height)
            .then(modifier),
        painter = handlePainter,
        contentDescription = "gacha handle",
    )
}

