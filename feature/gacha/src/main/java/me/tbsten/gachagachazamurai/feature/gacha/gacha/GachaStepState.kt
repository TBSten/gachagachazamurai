package me.tbsten.gachagachazamurai.feature.gacha.gacha

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue

@Composable
internal fun rememberGachaStepState(): GachaStepState {
    return remember { GachaStepState() }
}

class GachaStepState {
    var currentIndex by mutableStateOf(0)
    val current: GachaStep
        get() = steps[currentIndex]

    fun next() {
        currentIndex++
    }
}
