package me.tbsten.gachagachazamurai.component.animation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import kotlinx.coroutines.delay

@Composable
fun Modifier.delayed(
    delayMillis: Long,
    factory: Modifier.() -> Modifier,
): Modifier {
    var isWaited by remember { mutableStateOf(false) }
    LaunchedEffect(Unit) {
        delay(delayMillis)
        isWaited = true
    }
    return this.then(if (isWaited) this.factory() else this)
}