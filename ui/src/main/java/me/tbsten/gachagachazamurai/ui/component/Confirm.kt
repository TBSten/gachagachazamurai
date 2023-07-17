package me.tbsten.gachagachazamurai.ui.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog

@Composable
fun Confirm(
    state: ConfirmState,
    okButton: @Composable ConfirmState.() -> Unit = ConfirmDefaults.okButton,
    cancelButton: @Composable ConfirmState.() -> Unit = ConfirmDefaults.cancelButton,
    content: @Composable ConfirmState.() -> Unit,
) {
    if (state.open) {
        Dialog(onDismissRequest = { state.cancel() }) {

            Surface(shape = MaterialTheme.shapes.large) {
                Column(Modifier.padding(16.dp)) {
                    Box(Modifier.padding(vertical = 8.dp)) {
                        state.content()
                    }

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly,
                    ) {
                        state.okButton()
                        state.cancelButton()
                    }
                }
            }

        }
    }
}

@Composable
fun rememberConfirmState(
    onOk: () -> Unit,
    onCancel: () -> Unit = {},
): ConfirmState {
    var open by remember { mutableStateOf(false) }
    val state = object : ConfirmState {
        override val open = open

        override fun confirm() {
            open = true
        }

        override fun ok() {
            open = false
            onOk()
        }

        override fun cancel() {
            open = false
            onCancel()
        }
    }
    return state
}

interface ConfirmState {
    val open: Boolean
    fun confirm()
    fun ok()
    fun cancel()
}

object ConfirmDefaults {
    val okButton: @Composable ConfirmState.() -> Unit =
        {
            Button(onClick = { this.ok() }) {
                Text("OK")
            }
        }
    val cancelButton: @Composable ConfirmState.() -> Unit =
        {
            TextButton(onClick = { this.cancel() }) {
                Text(
                    "キャンセル"
                )
            }
        }

}