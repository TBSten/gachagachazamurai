package me.tbsten.gachagachazamurai.ui.component

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import coil.compose.AsyncImage

@Composable
fun ImageSelect(
    file: Uri?,
    onFileChange: (Uri?) -> Unit,
    mimeType: String = "image/*",
    preview: @Composable (Uri?) -> Unit = ImageSelectDefaultPreview,
) {
    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) {
        onFileChange(it)
    }

    Column {
        preview(file)
        TextButton(onClick = { launcher.launch(mimeType) }) {
            Text("ファイルを選択")
        }
    }

}

val ImageSelectDefaultPreview = @Composable { file: Uri? ->
    if (file != null) {
        AsyncImage(
            model = file,
            contentDescription = null,
        )
    }
}
