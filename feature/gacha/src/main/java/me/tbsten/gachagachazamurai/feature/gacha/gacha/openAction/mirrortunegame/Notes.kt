package me.tbsten.gachagachazamurai.feature.gacha.gacha.openAction.mirrortunegame

import androidx.annotation.DrawableRes
import me.tbsten.gachagachazamurai.feature.gacha.R

internal enum class Notes(
    @DrawableRes val image: Int,
    @DrawableRes val placeholderImage: Int,
) {
    LEFT(R.drawable.arrow_purple, R.drawable.arrow_purple_placeholder),
    DOWN(R.drawable.arrow_green, R.drawable.arrow_green_placeholder),
    UP(R.drawable.arrow_sky, R.drawable.arrow_sky_placeholder),
    RIGHT(R.drawable.arrow_pink, R.drawable.arrow_pink_placeholder),
    ;
}

internal val notesMap = mapOf(
    0.note to Notes.DOWN,
    2.note to Notes.UP,
    3.note to Notes.RIGHT,
    4.note to Notes.UP,
    5.note to Notes.RIGHT,
    5.5f.note to Notes.LEFT,
    6.note to Notes.LEFT,
)
    // reverse
    .toList().reversed().toMap()

private val Float.note: Float
    get() = this * 285f
private val Int.note: Float
    get() = this * 285f
