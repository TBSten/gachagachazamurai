package me.tbsten.gachagachazamurai.feature.gacha.gacha

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.absoluteOffset
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.scale
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.DpRect
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.height
import androidx.compose.ui.unit.width
import me.tbsten.gachagachazamurai.feature.gacha.R
import me.tbsten.gachagachazamurai.ui.modifier.clickableNoRipple

@Composable
internal fun Gacha(
    state: GachaState,
    modifier: Modifier = Modifier,
    onRotate: () -> Unit,
    onRotateFinished: (Float) -> Unit,
) {
    val standPainter = painterResource(R.drawable.gacha_stand)
    val handlePainter = painterResource(R.drawable.gacha_handle)
    val standAspect = standPainter.intrinsicSize.let { it.width / it.height }

    BoxWithConstraints(
        modifier.animateGacha(state)
    ) {
        val standRect = calcInsideBoxRect(DpSize(maxWidth, maxHeight), standAspect)

        val rotateState = rememberRotateState(gachaState = state, onRotate = onRotateFinished)
        Image(
            modifier = Modifier
                .size(standRect.width, standRect.height)
                .absoluteOffset(standRect.left, standRect.top)
                .clickableNoRipple(
                    enabled = rotateState.enable,
                    onClick = onRotate,
                )
                .animateGachaStand(gachaState = state),
            painter = standPainter,
            contentDescription = "gacha stand",
            contentScale = ContentScale.FillBounds,
        )
        Image(
            modifier = Modifier
                .absoluteOffset(
                    x = standRect.left + standRect.width * 107 / 273,
                    y = standRect.top + standRect.height * 210 / 373
                )
                .size(width = standRect.width * 60 / 273, height = standRect.height * 58 / 373)
                .clickableNoRipple(
                    enabled = rotateState.enable,
                    onClick = onRotate,
                )
                .animateGachaHandle(
                    state = rotateState,
                ),
            painter = handlePainter,
            contentDescription = "gacha handle",
            contentScale = ContentScale.FillBounds,
        )
    }
}

private fun calcInsideBoxRect(boundSize: DpSize, insideAspect: Float): DpRect {
    val size: DpSize
    val offset: DpOffset
    val boundAspect = boundSize.width / boundSize.height

    if (boundAspect < insideAspect) {
        size = DpSize(
            boundSize.width,
            calcHeightByAspectWidth(insideAspect, boundSize.width),
        )
        offset = DpOffset(
            0.dp,
            (boundSize.height - size.height) / 2,
        )
    } else {
        size = DpSize(
            calcWidthByAspectHeight(insideAspect, boundSize.height),
            boundSize.height,
        )
        offset = DpOffset(
            (boundSize.width - size.width) / 2,
            0.dp,
        )
    }

    return DpRect(
        offset,
        size,
    )
}

private fun calcWidthByAspectHeight(aspect: Float, height: Float) =
    aspect * height

private fun calcWidthByAspectHeight(aspect: Float, height: Dp) =
    height * aspect

private fun calcHeightByAspectWidth(aspect: Float, width: Float) =
    width / aspect

private fun calcHeightByAspectWidth(aspect: Float, width: Dp) =
    width / aspect

@Stable
class GachaState(
    scale: Float,
    enableRotate: Boolean,
    handleRotate: Float,
) {
    var scale by mutableStateOf(scale)
    var enableRotate by mutableStateOf(enableRotate)
    var handleRotate by mutableStateOf(handleRotate)
}

@Composable
private fun Modifier.animateGacha(state: GachaState): Modifier {
    val scale by animateFloatAsState(
        label = "gacha scale",
        targetValue = state.scale,
        animationSpec = tween(durationMillis = 1000),
    )
    return composed {
        scale(scale)
    }
}

@Composable
private fun Modifier.animateGachaHandle(
    state: RotateState,
): Modifier {
    return composed {
        rotate(state.currentRotate)
    }
}

@Composable
private fun Modifier.animateGachaStand(
    gachaState: GachaState,
): Modifier {
    return composed {
        this
    }
}

@Composable
private fun rememberRotateState(
    gachaState: GachaState,
    onRotate: (Float) -> Unit,
): RotateState {
    val handleRotate by animateFloatAsState(
        label = "gacha handle rotate",
        targetValue = gachaState.handleRotate,
        animationSpec = tween(durationMillis = 600, easing = LinearEasing),
        finishedListener = {
            onRotate(it)
        },
    )
    val isRotating = handleRotate != gachaState.handleRotate
    return RotateState(
        currentRotate = handleRotate,
        targetRotate = gachaState.handleRotate,
        enable = gachaState.enableRotate && !isRotating,
        isRotating = isRotating,
    )
}

@Stable
private data class RotateState(
    val currentRotate: Float,
    val targetRotate: Float,
    val enable: Boolean,
    val isRotating: Boolean,
)
