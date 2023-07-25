package me.tbsten.gachagachazamurai.feature.gacha.gacha

internal interface GachaStep {
    data class Spinning(val progress: Float) : GachaStep
    data class RunningAction(val progress: Float) : GachaStep
    companion object {
        val beforeStart = object : GachaStep {}
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

        val opened = object : GachaStep {}
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
    get() = when (val currentStep = this.current) {
        GachaStep.beforeStart ->
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

        is GachaStep.RunningAction ->
            GachaState(
                scale = 1.0f,
                enableRotate = false,
                handleRotate = 360f,
            )

        else -> throw IllegalStateException("$this can not convert to GachaState")
    }
