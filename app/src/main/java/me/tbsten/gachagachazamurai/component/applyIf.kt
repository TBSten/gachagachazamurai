package me.tbsten.gachagachazamurai.component

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun Modifier.applyIf(condition: Boolean, factory: @Composable Modifier.() -> Modifier) =
    if (condition) this.factory() else this
