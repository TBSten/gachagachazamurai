package me.tbsten.gachagachazamurai.ui.shape

import androidx.compose.ui.geometry.Rect

fun Rect.isIntersect(rect: Rect) = !this.intersect(rect).isEmpty
