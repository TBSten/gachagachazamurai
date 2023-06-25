package me.tbsten.gachagachazamurai.gacha.openAnimation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.window.Popup
import me.tbsten.gachagachazamurai.domain.PrizeItem
import me.tbsten.gachagachazamurai.gacha.GachaStep

@Composable
fun OpenAnimation(
    step: GachaStep,
    prizeItem: PrizeItem,
    onChangeStep: (step: GachaStep) -> Unit,
    prizeContent: @Composable () -> Unit,
) {
    val open = GachaStep.UNOPENED_CAPSULE <= step
    val backdropAlpha by animateFloatAsState(
        label = "popup backdrop effect",
        targetValue = if (open) 0.4f else 0f,
        animationSpec = tween(durationMillis = 300),
    )

    AnimatedVisibility(
        visible = open,
        enter = fadeIn(),
        exit = fadeOut(),
    ) {
        Popup {
            Box(
                Modifier
                    .background(Color.Black.copy(alpha = backdropAlpha))
                    .fillMaxSize()
            ) {

                InsideJokeCheck(
                    step = step,
                    prizeItem = prizeItem,
                    onChangeStep = onChangeStep,
                    prizeContent = prizeContent,
                )

            }
        }
    }
}
