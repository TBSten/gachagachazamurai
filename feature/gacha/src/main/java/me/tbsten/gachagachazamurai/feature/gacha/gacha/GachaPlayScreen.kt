package me.tbsten.gachagachazamurai.feature.gacha.gacha

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import me.tbsten.gachagachazamurai.feature.gacha.gacha.openAction.OpenActionPopup

@Composable
fun GachaPlayScreen() {
    val gachaStepState = rememberGachaStepState()
    val gachaState = gachaStepState.gachaState
    val openActionState = gachaStepState.openActionState

    Box(Modifier.fillMaxSize()) {
        Text("${gachaStepState.currentStep}", modifier = Modifier.align(Alignment.TopCenter))
        Gacha(
            modifier = Modifier
                .padding(48.dp)
                .align(Alignment.Center)
                .fillMaxSize(),
            gachaState = gachaState,
            onRotate = { gachaState.handleRotate += 180 },
            onRotateFinished = { gachaStepState.next() },
        )
        StartButton(
            modifier = Modifier.padding(bottom = 40.dp).align(Alignment.BottomCenter),
            onStart = {
                gachaStepState.next()
                gachaState.enableRotate = true
            },
        )
    }

    OpenActionPopup(
        state = openActionState,
        onComplete = {
            gachaStepState.currentStep = GachaStep.opened
        },
    )

}
