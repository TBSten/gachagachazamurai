package me.tbsten.gachagachazamurai.feature.gacha.gacha.openAction

import androidx.compose.animation.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import me.tbsten.gachagachazamurai.feature.gacha.gacha.openAction.mirrortunegame.MirrorTuneGameOpenAction

@Composable
internal fun OpenAction(
    state: OpenActionState,
    onShowNavigationText: (drawable: Int, String) -> Unit,
    onClearNavigationText: () -> Unit,
    onComplete: () -> Unit,
) {
    if (state.open) {
        val backdropColor = remember {
            Animatable(Color.Transparent)
        }
        LaunchedEffect(Unit) {
            backdropColor.animateTo(
                Color.Black.copy(alpha = 0.5f),
                animationSpec = tween(durationMillis = 500),
            )
        }

        Box(
            modifier = Modifier
                .background(backdropColor.value)
                .fillMaxSize(),
            contentAlignment = Alignment.Center,
        ) {
//            CapsuleOpenAction(
//            InsideJokeCheckOpenAction(
            MirrorTuneGameOpenAction(
                onShowNavigationText = onShowNavigationText,
                onClearNavigationText = onClearNavigationText,
                onComplete = onComplete,
            )
        }

    }
}

@Stable
class OpenActionState(
    open: Boolean,
) {
    var open by mutableStateOf(open)
    fun show() {
        open = true
    }

    fun hide() {
        open = false
    }
}
