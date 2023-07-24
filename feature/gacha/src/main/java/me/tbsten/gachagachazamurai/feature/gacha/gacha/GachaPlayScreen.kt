package me.tbsten.gachagachazamurai.feature.gacha.gacha

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.unit.dp

@Composable
fun GachaPlayScreen() {
    val gachaStepState = rememberGachaStepState(steps = steps)

    Box(Modifier.fillMaxSize()) {
        Text("${gachaStepState.current}", modifier = Modifier.align(Alignment.TopCenter))
        Gacha(
            modifier = Modifier
                .padding(48.dp)
                .align(Alignment.Center)
                .fillMaxSize(),
        )
        StartButton(
            modifier = Modifier.align(Alignment.BottomCenter),
            onStart = { gachaStepState.next() },
        )
    }

    OpenActionPopup()

}

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

/**
 * ガチャのステップは以下の通り
 *  BeforeStart
 *      ↓ ガチャを引くボタン
 *  Spinning(0.0)
 *      ↓ タップ
 *  Spinning(0.5)
 *      ↓ タップ
 *  RunningOpenAction
 *      ↓ 開封時のアクション（タップやチェックをなぞるなど）
 *  Opened
 */
private val steps = listOf(
    GachaStep.beforeStart,
    GachaStep.spinning(0 / 2.0),
    GachaStep.spinning(1 / 2.0),
    GachaStep.runningAction(0.0),
    GachaStep.opened,
)
