package me.tbsten.gachagachazamurai.ui.shape

import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.layout.LayoutCoordinates
import androidx.compose.ui.layout.positionInParent
import androidx.compose.ui.layout.positionInRoot
import androidx.compose.ui.layout.positionInWindow
import androidx.compose.ui.unit.toSize

fun LayoutCoordinates.toRectInWindow() =
    Rect(
        this.positionInWindow(),
        this.size.toSize(),
    )

fun LayoutCoordinates.toRectInParent() =
    Rect(
        this.positionInParent(),
        this.size.toSize(),
    )

fun LayoutCoordinates.toRectInRoot() =
    Rect(
        this.positionInRoot(),
        this.size.toSize(),
    )
