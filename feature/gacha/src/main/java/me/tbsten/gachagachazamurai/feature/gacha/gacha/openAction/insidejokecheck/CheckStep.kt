package me.tbsten.gachagachazamurai.feature.gacha.gacha.openAction.insidejokecheck

import androidx.compose.ui.geometry.Offset
import kotlin.math.abs
import kotlin.math.pow

internal sealed class CheckStep(val progress: Float) {
    val isComplete: Boolean
        get() = 1f <= progress

    object Start : CheckStep(0.00f)
    object Turned : CheckStep(0.25f)
    object End : CheckStep(1.00f)
    data class InTransition(val from: CheckStep, val to: CheckStep) :
        CheckStep((from.progress + to.progress) / 2)

    object Failure : CheckStep(0f)
}

internal fun Offset.inCircle(center: Offset, radius: Float) =
    abs(center.x - this.x).pow(2f) + abs(center.y - this.y).pow(2f) <= radius.pow(2f)

