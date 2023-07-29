package me.tbsten.gachagachazamurai.feature.gacha

import androidx.annotation.DrawableRes
import androidx.compose.animation.Crossfade
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import java.util.UUID

@OptIn(ExperimentalAnimationApi::class)
@Composable
internal fun NavigationText(
    state: NavigationTextState,
    modifier: Modifier = Modifier,
) {
    val entry = state.entry

    Crossfade(
        entry,
        modifier = modifier,
        animationSpec =
        if (entry != null) {
            // 出現時
            tween(durationMillis = 500)
        } else {
            // 削除時
            tween(durationMillis = 100)
        },
    ) { entry ->
        if (entry != null) {
            Image(
                painter = painterResource(entry.drawable),
                contentDescription = entry.text,
                modifier = Modifier
            )
        }
    }
}

class NavigationTextState() {

    var entry by mutableStateOf<NavigationTextEntry?>(null)
    fun show(@DrawableRes drawable: Int, text: String? = null) {
        this.entry = NavigationTextEntry(
            drawable, text,
        )
    }

    fun clear() {
        this.entry = null
    }
}

data class NavigationTextEntry(
    @DrawableRes val drawable: Int,
    val text: String?,
) {
    val key = UUID.randomUUID().toString()
}
