package me.tbsten.gachagachazamurai.top

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.animateScrollBy
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import me.tbsten.gachagachazamurai.R

val images = listOf(
    R.drawable.milabo1,
    R.drawable.dna,
    R.drawable.ham1,
    R.drawable.bakajanainoni1,
    R.drawable.kirakiller1,
    R.drawable.mirrotune1,
    R.drawable.mouhu2,
)

@Composable
fun TopScreenContent(
    gotoGachaScreen: () -> Unit,
    gotoPrizeScreen: () -> Unit,
) {
    BoxWithConstraints(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        val lazyRowState = rememberLazyListState(Int.MAX_VALUE / 2)
        LaunchedEffect(Unit) {
            while (true) {
                lazyRowState.animateScrollBy(
                    75f * 60,
                    tween(durationMillis = 1000 * 60, easing = LinearEasing)
                )
            }
        }

        LazyRow(
            state = lazyRowState,
            modifier = Modifier
                .wrapContentWidth()
                .fillMaxHeight(),
            userScrollEnabled = false,
        ) {
            items(count = Int.MAX_VALUE) {
                Image(
                    painter = painterResource(images[(Math.random() * images.size).toInt()]),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .wrapContentWidth()
                        .fillMaxHeight(),
                )
            }
        }
        TitlePane(
            modifier = Modifier
                .padding(8.dp)
                .align(Alignment.TopCenter),
        )
        MenuPane(
            modifier = Modifier
                .width(maxWidth - 16.dp * 2)
                .align(Alignment.BottomCenter)
                .padding(vertical = 16.dp * 1),
            gotoGachaScreen = gotoGachaScreen,
            gotoPrizeScreen = gotoPrizeScreen,
        )
    }
}

@Composable
fun TitlePane(
    modifier: Modifier = Modifier,
) {
    var isLaunched by rememberSaveable { mutableStateOf(false) }
    val scale by animateFloatAsState(
        targetValue = if (isLaunched) 1f else 3f,
        animationSpec = tween(durationMillis = 1500),
        label = "launch scale animation",
    )
    val alpha by animateFloatAsState(
        targetValue = if (isLaunched) 1f else 0.3f,
        animationSpec = tween(durationMillis = 1000),
        label = "launch alpha animation",
    )
    LaunchedEffect(Unit) {
        isLaunched = true
    }

    Box(
        modifier = modifier
//            .scale(scale)
            .graphicsLayer(
                transformOrigin = TransformOrigin(0.5f, 0f),
                scaleX = scale,
                scaleY = scale,
                alpha = alpha,
            )
            .background(
                MaterialTheme.colorScheme.primaryContainer,
                shape = MaterialTheme.shapes.large,
            )
            .padding(vertical = 8.dp, horizontal = 8.dp + 32.dp),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = "ガチャガチャ⭐️ザムライ",
            color = MaterialTheme.colorScheme.onPrimaryContainer,
        )
    }
}

@Composable
fun MenuPane(
    modifier: Modifier = Modifier,
    gotoGachaScreen: () -> Unit,
    gotoPrizeScreen: () -> Unit,
) {
    val scope = rememberCoroutineScope()

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(16.dp * 2)
    ) {
        Button(
            modifier = Modifier.fillMaxWidth(),
            contentPadding = PaddingValues(vertical = 16.dp),
            onClick = {
                scope.launch {
                    delay(100)
                    gotoGachaScreen()
                }
            },
        ) {
            Text(
                text = "ガチャを引く",
                style = MaterialTheme.typography.displaySmall,
            )
        }
        TextButton(
            modifier = Modifier.fillMaxWidth(0.5f)
                .align(Alignment.CenterHorizontally),
            onClick = {
                gotoPrizeScreen()
            },
        ) {
            Text(text = "中身を見る")
        }
    }
}