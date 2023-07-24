package me.tbsten.gachagachazamurai.feature.gacha.gacha

interface GachaStep {
    private data class Spinning(val progress: Double) : GachaStep
    private data class RunningAction(val progress: Double) : GachaStep
    companion object {
        val beforeStart = object : GachaStep {}
        fun spinning(progress: Double): GachaStep {
            if (progress < 0) throw IllegalArgumentException()
            if (1 <= progress) return runningAction(0.0)
            return Spinning(progress)
        }

        fun runningAction(progress: Double): GachaStep {
            if (progress < 0) throw IllegalArgumentException()
            if (1 <= progress) return opened
            return RunningAction(progress)
        }

        val opened = object : GachaStep {}
    }
}
