package me.tbsten.gachagachazamurai.component.animation

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.rotate

fun Modifier.infiniteRotate(
    delayMillis: Int = 0,
    durationMillis: Int = 5000,
): Modifier = composed {
    val label = "infinite rotate"
    val infiniteTransition = rememberInfiniteTransition(label = label)
    val rotate by infiniteTransition.animateFloat(
        label = label,
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = durationMillis,
                delayMillis = delayMillis,
                easing = LinearEasing,
            ),
            repeatMode = RepeatMode.Restart,
        ),
    )
    return@composed this.rotate(rotate)
}
