package me.tbsten.gachagachazamurai.feature.gacha.gacha

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue

@Composable
internal fun rememberGachaStepState(
    steps: List<GachaStep>,
): GachaStepState {
    var currentStepIndex by remember { mutableStateOf(0) }
    return object : GachaStepState {
        override val current
            get() = steps[currentStepIndex]
        override val currentIndex
            get() = currentStepIndex

        override fun next() {
            currentStepIndex++
        }
    }
}

internal interface GachaStepState {
    val current: GachaStep
    val currentIndex: Int
    fun next()
}
