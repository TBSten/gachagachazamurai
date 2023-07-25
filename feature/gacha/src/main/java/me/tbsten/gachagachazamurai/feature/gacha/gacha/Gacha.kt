package me.tbsten.gachagachazamurai.feature.gacha.gacha

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.absoluteOffset
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
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

@Composable
internal fun Gacha(
    state: GachaState,
    modifier: Modifier = Modifier,
    onRotate: (Float) -> Unit,
    onTapHandle: () -> Unit,
) {
    val standPainter = painterResource(R.drawable.gacha_stand)
    val handlePainter = painterResource(R.drawable.gacha_handle)
    val standAspect = standPainter.intrinsicSize.let { it.width / it.height }

    val scale by animateFloatAsState(
        label = "gacha scale",
        targetValue = state.scale,
        animationSpec = tween(durationMillis = 1000),
    )
    val handleRotate by animateFloatAsState(
        label = "gacha handle rotate",
        targetValue = state.handleRotate,
        animationSpec = tween(durationMillis = 800),
        finishedListener = {
            onRotate(it)
        },
    )
    val isRotating = handleRotate != state.handleRotate

    BoxWithConstraints(
        modifier
            .scale(scale)
    ) {
        val standRect = calcInsideBoxRect(DpSize(maxWidth, maxHeight), standAspect)
        Image(
            modifier = Modifier
                .size(standRect.width, standRect.height)
                .absoluteOffset(standRect.left, standRect.top),
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
                .clickable(enabled = state.enableRotate && !isRotating, onClick = onTapHandle)
                .rotate(handleRotate),
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
