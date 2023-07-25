package me.tbsten.gachagachazamurai.feature.gacha.gacha

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.scaleOut
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.TransformOrigin

@OptIn(ExperimentalAnimationApi::class)
@Composable
internal fun StartButton(
    onStart: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val visibleState = remember { MutableTransitionState(true) }

    AnimatedVisibility(
        modifier = modifier,
        visibleState = visibleState,
        enter = EnterTransition.None,
        exit = scaleOut(transformOrigin = TransformOrigin(0.5f, 1f)),
    ) {
        DisposableEffect(Unit) {
            onDispose { onStart() }
        }
        Button(
            onClick = { visibleState.targetState = false },
        ) {
            Text("ガチャを引く")
        }
    }
}
