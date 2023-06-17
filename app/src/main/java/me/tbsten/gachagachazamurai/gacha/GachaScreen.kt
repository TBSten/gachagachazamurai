package me.tbsten.gachagachazamurai.gacha

import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import me.tbsten.gachagachazamurai.component.clickableNoRipple

@Composable
fun GachaScreenContent(
    backToTop: () -> Unit,
    renavigateGacha: () -> Unit,
    gachaViewModel: GachaViewModel = hiltViewModel(),
) {
    var step by remember { mutableStateOf(GachaStep.BEFORE_START) }
    val enableBackScreen = step == GachaStep.BEFORE_START

    val targetPrizeItem = gachaViewModel.targetPrizeItem.collectAsState().value

    BackHandler(enabled = !enableBackScreen) {}

    Box(
        Modifier
            .clickableNoRipple(
                enabled = step in GachaStep.STARTED..GachaStep.TURNED_FULL.prev!!
            ) {
                step = step.next!!
            }
            .fillMaxSize()
    ) {

        IconButton(
            enabled = enableBackScreen,
            modifier = Modifier.padding(4.dp),
            onClick = { backToTop() },
        ) {
            Icon(Icons.Default.ArrowBack, contentDescription = "back to top")
        }

        Gacha(
            step = step,
            onChangeStep = { step = it },
            onCompleteGachaFullRotate = {
                step = GachaStep.UNOPENED_CAPSULE
            },
        )

        StartButton(
            step = step,
            modifier = Modifier
                .align(Alignment.BottomCenter),
            onChangeStep = { step = it }
        )

    }

    if (targetPrizeItem != null) {
        Capsule(
            step,
            prizeItem = targetPrizeItem,
            onChangeStep = { step = it },
            prizeContent = {
                GachaResult(
                    prizeItem = targetPrizeItem,
                    actions = {
                        TextButton(onClick = backToTop) {
                            Text("TOPに戻る")
                        }
                        Button(onClick = renavigateGacha) {
                            Text("もう一度引く")
                        }
                    },
                )
            },
        )
    }

}

@Composable
fun StartButton(
    step: GachaStep,
    onChangeStep: (step: GachaStep) -> Unit,
    modifier: Modifier,
) {
    AnimatedVisibility(
        visible = step == GachaStep.BEFORE_START,
        modifier = modifier
            .offset(y = (-8).dp),
    ) {
        Button(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            contentPadding = PaddingValues(vertical = 16.dp),
            onClick = {
                val next = step.next
                if (next != null) {
                    onChangeStep(next)
                }
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.secondary,
                contentColor = MaterialTheme.colorScheme.onSecondary,
            ),
        ) {
            Text(
                text = "ガチャを引く",
                style = MaterialTheme.typography.displaySmall,
            )
        }
    }
}
