package me.tbsten.gachagachazamurai.component.animation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.alpha


fun Modifier.fadeIn(
    _launched: Boolean? = null,
    delayMillis: Int = 0,
    durationMillis: Int = 300,
): Modifier = composed {
    var defaultLaunched by remember { mutableStateOf(false) }
    LaunchedEffect(Unit) {
        defaultLaunched = true
    }
    val launched = _launched ?: defaultLaunched

    val alpha by animateFloatAsState(
        label = "fade in",
        targetValue = if (launched) 1f else 0f,
        animationSpec = tween(durationMillis = durationMillis, delayMillis = delayMillis),
    )
    this.alpha(alpha)
}

@Composable
fun FadeIn(
    modifier: Modifier = Modifier,
    delayMillis: Int = 0,
    durationMillis: Int = 300,
    content: @Composable () -> Unit,
) {
    var launched by remember { mutableStateOf(false) }
    LaunchedEffect(Unit) { launched = true }
    AnimatedVisibility(
        visible = launched,
        enter = fadeInTransition(delayMillis, durationMillis),
        modifier = modifier,
    ) {
        content()
    }
}

fun fadeInTransition(
    delayMillis: Int = 0,
    durationMillis: Int = 300,
) = fadeIn(tween(durationMillis = durationMillis, delayMillis = delayMillis))
