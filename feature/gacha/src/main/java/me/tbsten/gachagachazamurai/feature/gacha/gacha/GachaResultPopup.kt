package me.tbsten.gachagachazamurai.feature.gacha.gacha

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.scaleIn
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Popup
import androidx.compose.ui.zIndex
import me.tbsten.gachagachazamurai.domain.PrizeItem

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun GachaResultPopup(
    state: GachaResultState,
    prizeItem: PrizeItem,
    onClose: () -> Unit,
) {
    if (state.open) {
        val visibleState = remember {
            MutableTransitionState(false)
                .apply { this.targetState = true }
        }


        Popup {
            DisposableEffect(Unit) {
                onDispose { onClose() }
            }

            AnimatedVisibility(
                visibleState = visibleState,
                enter = EnterTransition.None,
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState()),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                ) {

                    Box(
                        Modifier
                            .padding(top = 64.dp, start = 64.dp, end = 64.dp)
                            .zIndex(1f)
                            .animateEnterExit(
                                enter = scaleIn(tween(400))
                                        + fadeIn(tween(500)),
                            )
                    ) {
                        Box(Modifier.background(Color.Red).fillMaxWidth().aspectRatio(1f))
                    }

                    val overlap = 64
                    fun <T> cardAnimSpec() = tween<T>(700, 500)
                    fun <T> moreButtonAnimSpec() = tween<T>(700, 1400)
                    fun <T> backButtonAnimSpec() = tween<T>(700, 1700)
                    Surface(
                        modifier = Modifier
                            .offset(y = (-overlap).dp)
                            .fillMaxWidth()
                            .padding(16.dp)
                            .animateEnterExit(
                                enter = slideInVertically(cardAnimSpec()) { it / 2 }
                                        + fadeIn(cardAnimSpec()),
                            ),
                        shape = MaterialTheme.shapes.medium,
                    ) {
                        Column(
                            modifier = Modifier.padding(
                                top = 16.dp,
                                start = 16.dp,
                                end = 16.dp,
                                bottom = 32.dp
                            ),
                            horizontalAlignment = Alignment.CenterHorizontally,
                        ) {
                            Spacer(Modifier.height(overlap.dp))
                            Text(
                                text = prizeItem.name,
                                fontWeight = FontWeight.Bold,
                                textAlign = TextAlign.Center,
                            )
                            Spacer(Modifier.height(8.dp))
                            Text(
                                text = prizeItem.detail,
                                textAlign = TextAlign.Center,
                            )
                        }
                    }

                    Spacer(Modifier.height(16.dp))

                    OutlinedButton(
                        modifier = Modifier.animateEnterExit(
                            enter = slideInVertically(moreButtonAnimSpec()) { 100 }
                                    + fadeIn(moreButtonAnimSpec()),
                        ),
                        onClick = {},
                        border = BorderStroke(1.dp, MaterialTheme.colorScheme.primary),
                    ) {
                        Text("もう一度引く")
                    }

                    Spacer(Modifier.height(16.dp))

                    TextButton(
                        modifier = Modifier.animateEnterExit(
                            enter = slideInVertically(backButtonAnimSpec()) { 100 }
                                    + fadeIn(backButtonAnimSpec()),
                        ),
                        onClick = {},
                    ) {
                        Text("トップに戻る")
                    }

                }

            }
        }
    }
}

@Stable
data class GachaResultState(
    val open: Boolean,
)
