package me.tbsten.gachagachazamurai.ui.modifier

import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.graphics.painter.Painter

fun Modifier.aspectRatio(painter: Painter) = composed {
    aspectRatio(painter.intrinsicSize.let { it.width / it.height })
}
