package me.tbsten.gachagachazamurai.feature.gacha.gacha.result

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.scaleIn
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.absoluteOffset
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import me.tbsten.gachagachazamurai.domain.PrizeItem
import me.tbsten.gachagachazamurai.feature.gacha.R
import me.tbsten.gachagachazamurai.ui.modifier.aspectRatio

@Composable
fun GachaResult(
    state: GachaResultState,
    prizeItem: PrizeItem,
    onRestart: () -> Unit,
    onBackTop: () -> Unit,
) {
    if (state.open) {
        val visibleState = remember {
            MutableTransitionState(false)
                .apply { this.targetState = true }
        }

        fun <T> cardAnimSpec() = tween<T>(700, 500)
        fun <T> moreButtonAnimSpec() = tween<T>(700, 2200)
        fun <T> backButtonAnimSpec() = tween<T>(700, 2600)

        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            GachaResultCard(
                visibleState = visibleState,
                prizeItem = prizeItem,
                imageEnterTransition = Animations.image,
                cardEnterTransition = Animations.card,
                rarityEnterTransition = Animations.rarity,
            )

            Spacer(Modifier.height(16.dp))

            AnimatedVisibility(
                visibleState = visibleState,
                enter = Animations.moreButton,
            ) {
                OutlinedButton(
                    onClick = onRestart,
                    border = BorderStroke(1.dp, MaterialTheme.colorScheme.primary),
                ) {
                    Text(text = "もう一度引く", fontSize = 20.sp)
                }
            }

            Spacer(Modifier.height(16.dp))

            AnimatedVisibility(
                visibleState = visibleState,
                enter = Animations.backButton,
            ) {
                TextButton(
                    onClick = onBackTop,
                ) {
                    Text("トップに戻る")
                }
            }
        }

    }
}

class GachaResultState(
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

@OptIn(ExperimentalAnimationApi::class)
@Composable
private fun GachaResultCard(
    prizeItem: PrizeItem,
    imageEnterTransition: EnterTransition,
    cardEnterTransition: EnterTransition,
    rarityEnterTransition: EnterTransition,
    visibleState: MutableTransitionState<Boolean>,
) {
    AnimatedVisibility(
        visibleState = visibleState,
        enter = EnterTransition.None,
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {

            Box(
                Modifier
                    .zIndex(1f)
                    .animateEnterExit(
                        enter = imageEnterTransition,
                    )
            ) {
                Box(
                    Modifier.padding(start = 64.dp, end = 64.dp)
                ) {
                    Box(Modifier.background(Color.Red).fillMaxWidth().aspectRatio(1f))
                }
                RarityImage(
                    rarity = prizeItem.rarity,
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .animateEnterExit(
                            enter = rarityEnterTransition,
                        ),
                )
            }

            val overlap = 64

            Surface(
                modifier = Modifier
                    .absoluteOffset(y = (-overlap).dp)
                    .fillMaxWidth()
                    .padding(start = 16.dp, end = 16.dp)
                    .animateEnterExit(
                        enter = cardEnterTransition,
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

        }
    }
}

@Composable
private fun RarityImage(
    rarity: PrizeItem.Rarity,
    modifier: Modifier = Modifier,
) {
    val infiniteTransition = rememberInfiniteTransition()
    val rarityOffsetY by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 20f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 3000, delayMillis = 500),
            repeatMode = RepeatMode.Reverse,
        ),
    )

    val rarityPainter = painterResource(
        when (rarity) {
            PrizeItem.Rarity.NORMAL -> R.drawable.rarity_normal
            PrizeItem.Rarity.RARE -> R.drawable.rarity_rare
            PrizeItem.Rarity.SUPER_RARE -> R.drawable.rarity_super_rare
        }
    )
    Image(
        painter = rarityPainter,
        contentDescription = rarity.displayName,
        modifier = Modifier
            .width(100.dp)
            .aspectRatio(rarityPainter)
            .graphicsLayer(
                rotationZ = 20f,
                translationY = rarityOffsetY,
                transformOrigin = TransformOrigin(0f, 0f),
            )
            .then(modifier)
//            .align(Alignment.TopEnd)
//            .animateEnterExit(
//                enter = rarityEnterTransition,
//            ),
    )
}

@OptIn(ExperimentalAnimationApi::class)
private object Animations {
    val image =
        transitions(
            scaleIn(tween(400), initialScale = 0.5f),
            fadeIn(tween(500)),
        )
    val card =
        transitions(
            slideInVertically(
                tween(delayMillis = 500, durationMillis = 700),
                initialOffsetY = { it / 2 },
            ),
            fadeIn(
                tween(delayMillis = 500, durationMillis = 700),
            ),
        )
    val rarity =
        transitions(
            scaleIn(
                tween(100, 1400),
                initialScale = 1.2f,
                transformOrigin = TransformOrigin(0f, 0f),
            ),
            fadeIn(
                tween(100, 1400),
            ),
        )
    val moreButton =
        transitions(
            slideInVertically(
                tween(delayMillis = 2200, durationMillis = 700),
                initialOffsetY = { 100 },
            ),
            fadeIn(
                tween(delayMillis = 2200, durationMillis = 700),
            ),
        )
    val backButton =
        transitions(
            slideInVertically(
                tween(delayMillis = 2600, durationMillis = 700),
                initialOffsetY = { 100 },
            ),
            fadeIn(
                tween(delayMillis = 2600, durationMillis = 700),
            ),
        )
}

fun transitions(vararg transitions: EnterTransition) =
    transitions.reduce { acc, enterTransition -> acc + enterTransition }
