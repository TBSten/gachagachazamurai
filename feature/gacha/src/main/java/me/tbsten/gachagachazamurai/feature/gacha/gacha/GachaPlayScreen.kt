package me.tbsten.gachagachazamurai.feature.gacha.gacha

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import me.tbsten.gachagachazamurai.feature.gacha.gacha.openAction.OpenAction
import me.tbsten.gachagachazamurai.feature.gacha.gacha.result.GachaResult


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

@Composable
fun GachaPlayScreen(
    gachaPlayViewModel: GachaPlayViewModel = hiltViewModel(),
    renavigate: () -> Unit,
    backTop: () -> Unit,
) {
    val prizeItem = gachaPlayViewModel.prizeItem.collectAsState().value

    val gachaState = gachaPlayViewModel.gachaState
    val openActionState = gachaPlayViewModel.openActionState
    val gachaResultState = gachaPlayViewModel.gachaResultState

    Box {
        Box(Modifier.fillMaxSize()) {
            Gacha(
                modifier = Modifier
                    .padding(48.dp)
                    .align(Alignment.Center)
                    .fillMaxSize(),
                gachaState = gachaState,
                onRotate = {
                    gachaPlayViewModel.rotate()
                },
                onRotateFinished = {
                    if (it == 360f) {
                        gachaPlayViewModel.startOpenAction()
                    }
                },
            )
            StartButton(
                modifier = Modifier.padding(bottom = 40.dp).align(Alignment.BottomCenter),
                onStart = {
                    gachaPlayViewModel.startGacha()
                },
            )
        }

        OpenAction(
            state = openActionState,
            onComplete = gachaPlayViewModel::showResult,
        )

        if (prizeItem != null) {
            GachaResult(
                state = gachaResultState,
                prizeItem = prizeItem,
                onRestart = renavigate,
                onBackTop = backTop,
            )
        }

    }

}
