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

internal class GachaStepState {
    var currentIndex by mutableStateOf(0)
    var currentStep: GachaStep
        get() = steps[currentIndex]
        set(value) {
            val index = steps.indexOfFirst { it == value }
            if (index in 0..steps.size) {
                currentIndex = index
            }
        }
    val prevStep: GachaStep?
        get() = steps.getOrNull(currentIndex - 1)
    val nextStep: GachaStep?
        get() = steps.getOrNull(currentIndex + 1)

    fun next() {
        currentIndex++
    }
}
