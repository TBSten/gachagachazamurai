package me.tbsten.gachagachazamurai.feature.top

import android.net.Uri
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.animateScrollBy
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage

@Composable
internal fun TopScreen(
    topViewModel: TopViewModel = hiltViewModel(),
) {
    val images = topViewModel.images.collectAsState().value

    Box(Modifier.fillMaxSize()) {
        if (images != null) {
            TopImages(images)
        }

        TopTitle(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .offset(y = 12.dp)
        )

    }
}

@Composable
private fun TopImages(
    images: List<Uri>,
    modifier: Modifier = Modifier,
) {

    val scrollState = rememberScrollState()
    LaunchedEffect(scrollState.maxValue) {
        while (true) {
            scrollState.animateScrollBy(1500f, tween(10_000, easing = LinearEasing))
        }
    }

    Row(modifier.fillMaxSize().horizontalScroll(scrollState, enabled = false)) {
        images.forEach { uri ->
            AsyncImage(
                model = uri,
                contentDescription = null,
                modifier = Modifier.wrapContentWidth().fillMaxHeight(),
                alignment = Alignment.Center,
                contentScale = ContentScale.FillHeight,
            )
        }
    }

}

@Composable
private fun TopTitle(
    modifier: Modifier = Modifier,
) {
    val visibleState = remember { MutableTransitionState(false).apply { targetState = true } }
    val shape = MaterialTheme.shapes.small

    AnimatedVisibility(
        visibleState = visibleState,
        enter = fadeIn(tween(1500, 1000)),
        modifier = modifier,
    ) {

        Box(
            Modifier
                .border(2.dp, MaterialTheme.colorScheme.primary, shape = shape)
                .background(
                    MaterialTheme.colorScheme.primaryContainer,
                    shape = shape,
                )
                .padding(vertical = 8.dp, horizontal = 32.dp),
            contentAlignment = Alignment.Center,
        ) {
            Text("ガチャガチャ⭐️ザムライ", textAlign = TextAlign.Center)
        }

    }
}
