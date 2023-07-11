package me.tbsten.gachagachazamurai.file

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable

@Composable
fun rememberLauncherForSelectFile(onChange: (uri: Uri?) -> Unit): SelectFileState {
    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) {
        onChange(it)
    }
    return object : SelectFileState {
        override fun select(mimeType: String) {
            launcher.launch(mimeType)
        }

    }
}

interface SelectFileState {
    fun select(mimeType: String)
}
