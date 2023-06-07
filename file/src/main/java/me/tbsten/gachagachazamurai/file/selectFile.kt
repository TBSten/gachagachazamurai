package me.tbsten.gachagachazamurai.file

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue

@Composable
fun rememberLauncherForSelectFile(
    onChange: (Uri?) -> Unit = {},
): SelectFileState {
    val onChangeCallback by rememberUpdatedState(onChange)

    var uri by remember { mutableStateOf<Uri?>(null) }
    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) {
        uri = it
        onChangeCallback(it)
    }
    return object : SelectFileState {
        override val uri: Uri?
            get() = uri

        override fun select(mimeType: String) {
            launcher.launch(mimeType)
        }
    }
}

interface SelectFileState {
    val uri: Uri?
    fun select(mimeType: String)
}
