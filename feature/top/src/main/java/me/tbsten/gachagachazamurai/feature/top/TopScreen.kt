package me.tbsten.gachagachazamurai.feature.top

import android.net.Uri
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.gestures.animateScrollBy
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage

@Composable
internal fun TopScreen(
    topViewModel: TopViewModel = hiltViewModel(),
) {
    val images = topViewModel.images.collectAsState().value

    if (images != null) {
        TopImages(images)
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
