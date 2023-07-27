package me.tbsten.gachagachazamurai.feature.gacha.gacha

import me.tbsten.gachagachazamurai.feature.gacha.gacha.openAction.OpenActionState
import me.tbsten.gachagachazamurai.feature.gacha.gacha.result.GachaResultState

internal interface GachaStep {
    object BeforeStart : GachaStep
    data class Spinning(val progress: Float) : GachaStep
    data class RunningAction(val progress: Float) : GachaStep
    object Opened : GachaStep
    companion object {
        val beforeStart = BeforeStart
        fun spinning(progress: Float): GachaStep {
            if (progress < 0) throw IllegalArgumentException()
            if (1 <= progress) return runningAction(0.0f)
            return Spinning(progress)
        }

        fun runningAction(progress: Float): GachaStep {
            if (progress < 0) throw IllegalArgumentException()
            if (1 <= progress) return opened
            return RunningAction(progress)
        }

        val opened = Opened
    }
}

/**
 * ガチャのステップは以下の通り
 *  BeforeStart
 *      ↓ ガチャを引くボタン
 *  Spinning(0.0)
 *      ↓ タップ
 *  Spinning(0.5)
 *      ↓ タップ
 *  RunningOpenAction
 *      ↓ 開封時のアクション（タップやチェックをなぞるなど）
 *  Opened
 */
internal val steps = listOf(
    GachaStep.beforeStart,
    GachaStep.spinning(0 / 2f),
    GachaStep.spinning(1 / 2f),
    GachaStep.runningAction(0f),
    GachaStep.opened,
)

internal val GachaStepState.gachaState: GachaState
    get() = when (val currentStep = this.currentStep) {
        is GachaStep.BeforeStart ->
            GachaState(
                scale = 0.8f,
                enableRotate = false,
                handleRotate = 0f,
            )

        is GachaStep.Spinning ->
            GachaState(
                scale = 1.0f,
                enableRotate = true,
                handleRotate = currentStep.progress * 360,
            )

        is GachaStep.RunningAction,
        is GachaStep.Opened ->
            GachaState(
                scale = 1.0f,
                enableRotate = false,
                handleRotate = 360f,
            )

        else -> throw IllegalStateException("undefined to convert $currentStep to GachaState")
    }

internal val GachaStepState.openActionState: OpenActionState
    get() = when (currentStep) {
        is GachaStep.BeforeStart,
        is GachaStep.Spinning -> OpenActionState(open = false)

        is GachaStep.RunningAction,
        is GachaStep.Opened -> OpenActionState(open = true)

        else -> throw IllegalStateException("undefined to convert $this to OpenActionState")
    }

internal val GachaStepState.gachaResultState: GachaResultState
    get() = when (currentStep) {
        is GachaStep.BeforeStart,
        is GachaStep.Spinning,
        is GachaStep.RunningAction -> GachaResultState(
            open = false,
        )

        is GachaStep.Opened -> GachaResultState(
            open = true,
        )

        else -> throw IllegalStateException("undefined to convert $this to GachaResultState")
    }
