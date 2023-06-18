package me.tbsten.gachagachazamurai.component

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed

inline fun Modifier.applyIf(condition: Boolean, crossinline factory: Modifier.() -> Modifier) =
    composed { if (condition) factory() else this }
