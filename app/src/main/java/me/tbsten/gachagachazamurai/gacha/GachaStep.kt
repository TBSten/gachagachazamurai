package me.tbsten.gachagachazamurai.gacha

enum class GachaStep {
    BEFORE_START,
    STARTED,
    TURNED_HALF,
    TURNED_FULL,
    UNOPENED_CAPSULE,
    OPENED_CAPSULE;

    val stepIndex = ordinal

    val next: GachaStep?
        get() {
            val steps = GachaStep.values()
            if (steps.size <= ordinal + 1) return null
            return steps[ordinal + 1]
        }
    val prev: GachaStep?
        get() {
            val steps = GachaStep.values()
            if (ordinal < 0) return null
            return steps[ordinal - 1]
        }
}