package me.tbsten.gachagachazamurai.gacha

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension

@Composable
fun Gacha(
    modifier: Modifier = Modifier,
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
        modifier.scale(gachaScale).fillMaxSize().padding(48.dp),
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



