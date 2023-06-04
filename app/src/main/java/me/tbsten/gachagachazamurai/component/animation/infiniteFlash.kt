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
import androidx.compose.ui.draw.alpha

fun Modifier.infiniteFlash(
    delayMillis: Int = 0,
    durationMillis: Int = 400,
    minAlpha: Float = 0.5f,
    maxAlpha: Float = 1.0f,
): Modifier = composed {
    val label = "infinite flash"
    val infiniteTransition = rememberInfiniteTransition(label = label)
    val flash by infiniteTransition.animateFloat(
        label = label,
        initialValue = minAlpha,
        targetValue = maxAlpha,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = durationMillis,
                delayMillis = delayMillis,
                easing = LinearEasing,
            ),
            repeatMode = RepeatMode.Reverse,
        ),
    )
    return@composed this.alpha(flash)
}