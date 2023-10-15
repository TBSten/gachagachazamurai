package me.tbsten.gachagachazamurai.feature.gacha.gacha.openAction.mirrortunegame

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.core.tween
import androidx.compose.animation.scaleIn
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.LocalOverscrollConfiguration
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import me.tbsten.gachagachazamurai.feature.gacha.R
import me.tbsten.gachagachazamurai.ui.shape.toRectInWindow

private val offsetToNotes = notesMap.mapKeys { (timing, _) -> timing * 1.5f }

@OptIn(ExperimentalFoundationApi::class)
@Composable
internal fun MirrorTuneGameOpenAction(
    onComplete: () -> Unit,
    onShowNavigationText: (drawable: Int, String) -> Unit,
    onClearNavigationText: () -> Unit,
) {
    val playGameState = playGame(
        onShowNavigationText = onShowNavigationText,
        onClearNavigationText = onClearNavigationText,
        onComplete = onComplete,
    )
    var placeholderRect by remember { mutableStateOf<Rect?>(null) }

    Box(
        modifier = Modifier
            .padding(horizontal = 10.dp)
            .fillMaxSize(),
    ) {
        val intersectionState = rememberNotesIntersectionState()
        CompositionLocalProvider(
            LocalOverscrollConfiguration provides null
        ) {
            if (playGameState.gameStep <= GameStep.PLAYING) {
                PointView(
                    point = playGameState.point,
                    modifier = Modifier.offset(y = 180.dp),
                )
            } else {
                val visibleState = remember {
                    MutableTransitionState(false).apply { targetState = true }
                }
                AnimatedVisibility(
                    visibleState = visibleState,
                    modifier = Modifier.offset(y = 150.dp).align(Alignment.TopCenter),
                    enter = scaleIn(
                        animationSpec = tween(delayMillis = 700),
                        transformOrigin = TransformOrigin(0.5f, 1f)
                    ),
                ) {
                    PointView(playGameState.point, size = PointViewSize.LG)
                }
            }
            BoxWithConstraints(
                Modifier
                    .fillMaxWidth()
                    .matchParentSize()
                    .verticalScroll(
                        playGameState.scrollState,
                        enabled = false,
                        reverseScrolling = true,
                    )
            ) {
                val height = minHeight
                Column {
                    Spacer(
                        Modifier
                            .background(Color.Yellow)
                            .height(height)
                    )
                    NotesLayout(
                        notesMap = offsetToNotes,
                        intersectionTarget = placeholderRect,
                        intersectionState = intersectionState,
                    )
                    Spacer(
                        Modifier
                            .background(Color.Yellow)
                            .height(height * 2)
                    )
                }
            }
        }

        // placeholder
        Box(
            Modifier
                .padding(bottom = 50.dp)
                .align(Alignment.BottomCenter)
        ) {
            NotesLine(
                modifier = Modifier
                    .onGloballyPositioned {
                        placeholderRect = it.toRectInWindow()
                    },
                placeholder = true,
                notes = Notes.values(),
                onClick = {
                    val isHit = intersectionState.isIntersect(it)
                    if (isHit) playGameState.point += 10
                },
            )
        }
    }
}


@Composable
private fun playGame(
    onShowNavigationText: (drawable: Int, String) -> Unit,
    onClearNavigationText: () -> Unit,
    onComplete: () -> Unit,
    duration: Int = 4000,
): PlayGameState {
    val scrollState = rememberScrollState()
    val gameStepState = rememberGameStepState()
    val playGameState = remember {
        PlayGameState(
            scrollState = scrollState,
            gameStepState = gameStepState,
        )
    }
    LaunchedEffect(Unit) {
        // tap
        onShowNavigationText(R.drawable.rhythm_tap_2, "リズムよくタップ")
        delay(1000)
        onClearNavigationText()
        gameStepState.onStartPlay()
        // play
        scrollState.animateScrollTo(
            scrollState.maxValue,
            tween(durationMillis = duration, easing = LinearEasing),
        )
        // game result
        val result = gameStepState.onFinishPlay(playGameState.point)
        onShowNavigationText(result.image, result.text)
        delay(3000)
        // gacha result
        gameStepState.onCompleted()
        onComplete()
    }
    return playGameState
}

internal class PlayGameState(
    val scrollState: ScrollState,
    val gameStepState: GameStepState,
) {
    var point by mutableIntStateOf(0)
    val gameStep: GameStep
        get() = gameStepState.step
}
