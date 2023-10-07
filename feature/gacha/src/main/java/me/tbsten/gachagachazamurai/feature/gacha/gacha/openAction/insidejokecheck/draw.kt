package me.tbsten.gachagachazamurai.feature.gacha.gacha.openAction.insidejokecheck

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke

internal fun DrawScope.drawCheck(
    target: Rect,
    color: Color = Color.Black.copy(alpha = 0.3f),
    width: Float = 30f,
) {
    val startPoint = target.centerLeft
    val turningPoint = target.bottomCenter
    val endPoint = target.topRight

    drawPath(
        path = Path().apply {
            moveTo(startPoint.x, startPoint.y)
            lineTo(turningPoint.x, turningPoint.y)
            lineTo(endPoint.x, endPoint.y)
        },
        color = color,
        style = Stroke(width = width, cap = StrokeCap.Round, miter = 1f),
    )
}

internal fun centerRect(container: Rect, size: Size) =
    Rect(
        Offset(
            x = container.topLeft.x + container.width / 2 - size.width / 2,
            y = container.topLeft.y + container.height / 2 - size.height / 2,
        ),
        size,
    )

internal fun DrawScope.drawBackground(
    checkStep: CheckStep,
    progress: Float,
) {
    val rectSize = Size(size.width, size.height * progress)
    val color =
        when (checkStep) {
            CheckStep.Start,
            CheckStep.InTransition(
                CheckStep.Start,
                CheckStep.Turned,
            ) -> Color.Blue

            CheckStep.Turned,
            CheckStep.InTransition(
                CheckStep.Turned,
                CheckStep.End,
            ) -> Color.Yellow

            CheckStep.End -> Color.Green
            CheckStep.Failure -> Color.Red
            else -> Color.Gray
        }.copy(alpha = 0.5f)
    drawRect(
        color,
        topLeft = Offset(0f, size.height - rectSize.height),
        size = rectSize,
    )
}