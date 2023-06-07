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
fun rememberLauncherForMultiSelectFile(
    onChange: (List<Uri>) -> Unit = {},
): SelectMultiFileState {
    val onChangeCallback by rememberUpdatedState(onChange)

    var uris by remember { mutableStateOf<List<Uri>?>(null) }
    val launcher =
        rememberLauncherForActivityResult(ActivityResultContracts.GetMultipleContents()) {
            uris = it
            onChangeCallback(it)
        }
    return object : SelectMultiFileState {
        override val uris: List<Uri>?
            get() = uris

        override fun select(mimeType: String) {
            launcher.launch(mimeType)
        }
    }
}

interface SelectMultiFileState {
    val uris: List<Uri>?
    fun select(mimeType: String)
}
