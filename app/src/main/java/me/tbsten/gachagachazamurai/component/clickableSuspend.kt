package me.tbsten.gachagachazamurai.component

import androidx.compose.foundation.clickable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

fun Modifier.clickableSuspend(block: suspend CoroutineScope.() -> Unit): Modifier = composed {
    val scope = rememberCoroutineScope()
    clickable {
        scope.launch {
            block()
        }
    }
}
